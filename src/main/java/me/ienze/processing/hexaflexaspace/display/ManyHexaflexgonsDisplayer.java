package me.ienze.processing.hexaflexaspace.display;

import me.ienze.processing.hexaflexaspace.Hexaflexagon;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.Iterator;

/**
 * @author Ienze
 */
public class ManyHexaflexgonsDisplayer extends PApplet {

    private final Hexaflexagon hexaflexagon;
    private final Iterator<PGraphics> graphicsIterator;
    private int hexhWidth;
    private int hexhHeight;

    public ManyHexaflexgonsDisplayer(Hexaflexagon hexaflexagon, Iterator<PGraphics> graphicsIterator) {
        this.hexaflexagon = hexaflexagon;
        this.graphicsIterator = graphicsIterator;
    }

    @Override
    public void settings() {
        fullScreen();
//        size(800, 800);
    }

    @Override
    public void setup() {
        hexhWidth = (int) Math.ceil(width / hexaflexagon.getWidth() * 1.5 / 2.0);
        hexhHeight = (int) Math.ceil(height / hexaflexagon.getHeight() / 2.0);

        background(255);

        frameRate(42);
    }

    @Override
    public void draw() {

        double triangleWidth = hexaflexagon.getTriangleSize();
        double triangleHeight = Math.round((triangleWidth / 2) * Math.sqrt(3));

        for(int rx=-hexhWidth; rx<=hexhWidth; rx++){
            for(int ry=-hexhHeight; ry<=hexhHeight; ry++){

                double guessMoveY = rx % 2 != 0 ? triangleHeight : 0;
                double collumnVal = triangleWidth * 1.5 * rx;
                double rowVal = triangleHeight * ry * 2 - guessMoveY;

                if(graphicsIterator.hasNext()) {

                    float px = (float) (collumnVal - triangleWidth + width / 2);
                    float py = (float) (rowVal - triangleHeight + height / 2);

                    image(graphicsIterator.next(), px, py);
                }
            }
        }

        save("m"+frameCount+".png");
    }
}
