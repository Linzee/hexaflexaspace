package me.ienze.processing.hexaflexaspace;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Ienze
 */
public class Hexaflexagon {

    private double triangleSize;
    private PVector position;

    public Hexaflexagon(double triangleSize) {
        this.triangleSize = triangleSize;
    }

    public Hexaflexagon(double triangleSize, PVector position) {
        this(triangleSize);
        this.position = position;
    }

    public void putInto(PApplet sketch) {
        if (sketch.width < getWidth() || sketch.height < getHeight()) {
            throw new IllegalArgumentException("Sketch is too small for this hexaflexagon. Width has to be at least " + getWidth() + " and height " + getHeight() + ".");
        }
        this.position = new PVector(sketch.width / 2, sketch.height / 2);
    }

    public double getTriangleSize() {
        return triangleSize;
    }

    public PVector getPosition() {
        return position;
    }

    public double getWidth() {
        return 2 * triangleSize;
    }

    public double getHeight() {
        return 2 * triangleSize * Math.sqrt(3) * 50 / 100;
    }
}
