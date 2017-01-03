package me.ienze.hexaflexaspace;

import processing.core.PVector;

/**
 * @author Ienze
 */
public class NoneFoldingCalculator implements FoldingCalculator {

    private final Hexaflexagon hexaflexagon;

    public NoneFoldingCalculator(Hexaflexagon hexaflexagon) {
        this.hexaflexagon = hexaflexagon;
    }

    @Override
    public PVector fold(PVector pvector) {
        PVector vector = PVector.sub(pvector, hexaflexagon.getPosition());
//        return PVector.add(vector, new PVector((float) hexaflexagon.getWidth() / 2, (float) hexaflexagon.getHeight() / 2));
//
        double triangleWidth = hexaflexagon.getTriangleSize();
        double triangleHeight = (triangleWidth / 2) * Math.sqrt(3);

        long guessCollumn = Math.round(vector.x / (triangleWidth * 1.5));
        double guessMoveY = guessCollumn % 2 != 0 ? triangleHeight : 0;
        long guessRow = Math.round((vector.y + guessMoveY) / (triangleHeight * 2));

        double collumnVal = triangleWidth * 1.5 * guessCollumn;
        double rowVal = triangleHeight * guessRow * 2 - guessMoveY;

        PVector[] hexs = new PVector[3];
        hexs[0] = new PVector((float) collumnVal, (float) (rowVal));
        if(vector.x <= collumnVal) {
            hexs[1] = new PVector((float) (collumnVal - triangleWidth * 1.5), (float) (rowVal - triangleHeight));
            hexs[2] = new PVector((float) (collumnVal - triangleWidth * 1.5), (float) (rowVal + triangleHeight));
        } else {
            hexs[1] = new PVector((float) (collumnVal + triangleWidth * 1.5), (float) (rowVal - triangleHeight));
            hexs[2] = new PVector((float) (collumnVal + triangleWidth * 1.5), (float) (rowVal + triangleHeight));
        }

        int closest = 0;
        double dist = hexs[0].dist(vector);
        for(int i=1; i<hexs.length; i++) {
            double thisDist = hexs[i].dist(vector) + i/2;
            if(thisDist < dist) {
                dist = thisDist;
                closest = i;
            }
        }

        PVector closestHexaflexagon = hexs[closest];
        long collumn = Math.round(closestHexaflexagon.x / (triangleWidth * 1.5));
        double moveY = collumn % 2 != 0 ? triangleHeight : 0;
        long row = Math.round((vector.y + moveY) / (triangleHeight * 2));

        if(collumn == 0 && row == 0) {
            return PVector.add(vector, new PVector((float) hexaflexagon.getWidth() / 2, (float) hexaflexagon.getHeight() / 2));
        } else {
            return new PVector(-2, -2);
        }
    }
}
