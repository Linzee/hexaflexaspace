package me.ienze.processing.hexaflexaspace.debugging;

import me.ienze.processing.hexaflexaspace.Hexaflexagon;
import me.ienze.processing.hexaflexaspace.Hexaflexaspace;
import me.ienze.processing.hexaflexaspace.display.PGraphicsDisplayer;
import processing.core.PApplet;

/**
 * @author Ienze
 */
public class SketchOfHexaflexaspace extends PApplet {

    private Hexaflexaspace hexaflexaspace;

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        Hexaflexagon hexaflexagon = new Hexaflexagon(200);
        hexaflexaspace = new Hexaflexaspace(this, hexaflexagon);

        PGraphicsDisplayer hexaflexagonDisplayer = new PGraphicsDisplayer(hexaflexaspace.getFoldedHexaflexagonGraphics());
        runSketch(new String[]{"me.ienze.hexaflexaspace.display.PGraphicsDisplayer"}, hexaflexagonDisplayer);
    }

    @Override
    public void draw() {

        background(0);

        fill(255);
        ellipse(mouseX, mouseY, 20, 20);

        hexaflexaspace.update();

        try {
            image(hexaflexaspace.getFoldedHexaflexagonGraphics(), 0, 0);
        } catch (Exception e) {}
    }

    public static void main(String... args){
        PApplet.main("me.ienze.hexaflexaspace.debugging.SketchOfHexaflexaspace");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(30 * 1000);
                    System.exit(-42);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
