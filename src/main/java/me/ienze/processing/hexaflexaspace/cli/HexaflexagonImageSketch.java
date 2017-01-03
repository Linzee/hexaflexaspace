package me.ienze.processing.hexaflexaspace.cli;

import me.ienze.processing.hexaflexaspace.Hexaflexagon;
import me.ienze.processing.hexaflexaspace.Hexaflexaspace;
import me.ienze.processing.hexaflexaspace.display.PGraphicsDisplayer;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.nio.file.*;

/**
 * @author Ienze
 */
public class HexaflexagonImageSketch extends PApplet {

    private WatchService watchService;
    private Path in;
    private Path out;
    private PImage image;
    private Hexaflexagon hexaflexagon;
    private Hexaflexaspace hexaflexaspace;
    private boolean redrawImage;

    public HexaflexagonImageSketch(Path in, Path out, double size, boolean watch) throws IOException {
        this.in = in;
        this.out = out;
        if(size > 0) {
            hexaflexagon = new Hexaflexagon(size);
        }
        if(watch) {
            watchService = FileSystems.getDefault().newWatchService();
        }
    }

    @Override
    public void settings() {
        reloadImage();

        if(hexaflexagon == null) {
            hexaflexagon = new Hexaflexagon(Math.min(image.width, image.height) / 2);
        }

        size(Math.max((int)hexaflexagon.getWidth(), image.width), Math.max((int)hexaflexagon.getHeight(), image.height));
    }

    @Override
    public void setup() {

        hexaflexaspace = new Hexaflexaspace(this, hexaflexagon);
        hexaflexaspace.setCopyInitialHexaflexagon(true);

        PGraphicsDisplayer displayer = new PGraphicsDisplayer(hexaflexaspace.getFoldedHexaflexagonGraphics());
        PApplet.runSketch(new String[]{""}, displayer);

        frameRate(4);

        redrawImageBackground();
        redrawImage();

        if(watchService != null) {
            try {
                watchImage(in.toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            exit();
        }
    }

    private void watchImage(final Path path) throws IOException {

        Path folderPath = path.getParent();
        final WatchKey key = folderPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }

                        WatchEvent<Path> ev = (WatchEvent<Path>)event;
                        Path filename = ev.context();
                        if(filename.toAbsolutePath().equals(path)) {
                            redrawImage = true;
                            System.out.println("Redrawing image..");
                        }
                    }
                    boolean valid = key.reset();
                    if (!valid) {
                        exit();
                        break;
                    }
                }
            }
        }).start();
    }

    @Override
    public void draw() {
        if(redrawImage) {
            redrawImage();
        }
    }

    private void redrawImageBackground() {
        reloadImage();

        image(image, 0, 0, width * image.width, height * image.height);

        hexaflexaspace.update();
        hexaflexaspace.getFoldedHexaflexagonGraphics().save(out.toAbsolutePath().toString());
    }

    private void reloadImage() {
        image = loadImage(in.toAbsolutePath().toString());
    }

    private void redrawImage() {
        PImage image = loadImage(in.toAbsolutePath().toString());

        if(image == null) {
            fill(0xff0000);
            rect(0, 0, width, height);
            return;
        }

        image(image,
                width / 2 - image.width / 2,
                height / 2 - image.height / 2,
                image.width,
                image.height);

        hexaflexaspace.update();

        redrawImage = false;

        hexaflexaspace.getFoldedHexaflexagonGraphics().save(out.toAbsolutePath().toString());
    }
}
