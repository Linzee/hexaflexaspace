package me.ienze.processing.hexaflexaspace.debugging;

import me.ienze.processing.hexaflexaspace.Hexaflexagon;
import me.ienze.processing.hexaflexaspace.Hexaflexaspace;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

/**
 * @author Ienze
 */
public class ViewFoldedHexaflexaspace extends PApplet {

    private Hexaflexaspace hexaflexaspace;

    @Override
    public void settings() {
//        size(800, 800);
        fullScreen();
    }

    @Override
    public void setup() {
        Hexaflexagon hexaflexagon = new Hexaflexagon(200);
        hexaflexaspace = new Hexaflexaspace(this, hexaflexagon);
    }

    @Override
    public void draw() {

        background(0);

        for(int x=0; x<width; x++) {
            for(int y=0; y<height; y++) {
                PVector vector = hexaflexaspace.getFoldedVector(new PVector(x, y));
                if(vector.x < 0 || vector.x >= width || vector.y < 0 || vector.y >= height) {
                    set(x, y, Color.red.getRGB());
                } else {
                    set(x, y, new Color(0, vector.x / width, vector.y / height).getRGB());
                }
            }
        }
    }

    public static void main(String... args){
        PApplet.main("me.ienze.hexaflexaspace.debugging.ViewFoldedHexaflexaspace");

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
