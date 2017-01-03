package me.ienze.processing.hexaflexaspace.display;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * @author Ienze
 */
public class PGraphicsDisplayer extends PApplet {

    private PGraphics pgraphics;

    public PGraphicsDisplayer(PGraphics pgraphics) {
        this.pgraphics = pgraphics;
    }

    @Override
    public void settings() {
        size(pgraphics.width, pgraphics.height);
    }

    @Override
    public void draw() {
        //TODO what is best way to copy PGraphic
        try {
            image(pgraphics, 0, 0);
        } catch (Exception e) { }
    }
}
