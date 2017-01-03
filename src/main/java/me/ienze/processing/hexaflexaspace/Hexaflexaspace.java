package me.ienze.hexaflexaspace;

import processing.core.*;

import java.awt.*;
import java.util.Arrays;

/**
 * @author Ienze
 */
public class Hexaflexaspace {

    private PApplet sketch;
    private Hexaflexagon hexaflexagon;

    private boolean cachedFoldingCalculator = true;
    private FoldingCalculator foldingCalculator;
    private boolean copyInitialHexaflexagon = false;
    private PGraphics foldedHexaflexagonGraphics;

    private int[] oldPixels;

    public Hexaflexaspace(PApplet sketch, Hexaflexagon hexaflexagon) {
        this.sketch = sketch;
        this.hexaflexagon = hexaflexagon;

        if(hexaflexagon.getPosition() == null) {
            hexaflexagon.putInto(sketch);
        }
    }

    public FoldingCalculator getFoldingCalculator() {
        if(foldingCalculator == null) {
            if (cachedFoldingCalculator) {
                foldingCalculator = new PrecalculatedFoldingCalculator(
                        new SimpleFoldingCalculator(hexaflexagon),
                        new Rectangle(0, 0, sketch.width, sketch.height), 1);
            } else {
                foldingCalculator = new SimpleFoldingCalculator(hexaflexagon);
            }
        }
        return foldingCalculator;
    }

    public PGraphics getFoldedHexaflexagonGraphics() {
        if(this.foldedHexaflexagonGraphics == null) {
            this.foldedHexaflexagonGraphics = sketch.createGraphics((int) hexaflexagon.getWidth(), (int) hexaflexagon.getHeight());

            this.foldedHexaflexagonGraphics.beginDraw();
//            this.foldedHexaflexagonGraphics.background(copyInitialHexaflexagon ? 255 : 0);
            this.foldedHexaflexagonGraphics.endDraw();
        }

        return this.foldedHexaflexagonGraphics;
    }

    public void update() {
        sketch.loadPixels();

        if(oldPixels != null || copyInitialHexaflexagon) {
            PGraphics graphics = getFoldedHexaflexagonGraphics();
            graphics.beginDraw();

            for (float x = 0; x < sketch.width; x += 1) {
                for (float y = 0; y < sketch.height; y += 1) {
                    int index = (int)y * sketch.width + (int)x;
                    if ((oldPixels == null && copyInitialHexaflexagon) || (oldPixels != null && oldPixels[index] != sketch.pixels[index])) {
                        updatePixel(graphics, new PVector(x, y));
                    }
                }
            }

            graphics.endDraw();
        }

        oldPixels = Arrays.copyOf(sketch.pixels, sketch.pixels.length);
        sketch.updatePixels();
    }

    private void updatePixel(PGraphics graphics, PVector vector) {
        PVector foldedHexPos = getFoldingCalculator().fold(vector);

        int index = Math.round(vector.y) * sketch.width + Math.round(vector.x);
        if(index < 0 || index >= sketch.pixels.length) {
            return;
        }

        graphics.set((int) foldedHexPos.x, (int) foldedHexPos.y, sketch.pixels[index]);
    }

    public PVector getFoldedVector(PVector vector) {
        return getFoldingCalculator().fold(vector);
    }

    public Hexaflexagon getHexaflexagon() {
        return hexaflexagon;
    }

    public boolean isCachedFoldingCalculator() {
        return cachedFoldingCalculator;
    }

    public void setCachedFoldingCalculator(boolean cachedFoldingCalculator) {
        this.cachedFoldingCalculator = cachedFoldingCalculator;
    }

    public boolean isCopyInitialHexaflexagon() {
        return copyInitialHexaflexagon;
    }

    public void setCopyInitialHexaflexagon(boolean copyInitialHexaflexagon) {
        this.copyInitialHexaflexagon = copyInitialHexaflexagon;
    }
}
