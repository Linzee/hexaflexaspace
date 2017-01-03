package me.ienze.processing.hexaflexaspace;

import processing.core.PVector;

/**
 * @author Ienze
 */
@SuppressWarnings("Since15")
public class SimpleFoldingCalculator implements FoldingCalculator {

    private Hexaflexagon hexaflexagon;

    public SimpleFoldingCalculator(Hexaflexagon hexaflexagon) {
        this.hexaflexagon = hexaflexagon;
    }

    @Override
    public PVector fold(PVector in) {

        PVector vector = new PVector(in.x - hexaflexagon.getPosition().x, in.y - hexaflexagon.getPosition().y);

        double triangleWidth = hexaflexagon.getTriangleSize();
        double triangleHeight = (triangleWidth / 2) * Math.sqrt(3);

        long guessCollumn = Math.round(vector.x / (triangleWidth * 1.5));
        double guessMoveY = guessCollumn % 2 != 0 ? triangleHeight : 0;
        long guessRow = Math.round((vector.y + guessMoveY) / (triangleHeight * 2));

        double collumnVal = triangleWidth * 1.5 * guessCollumn;
        double rowVal = triangleHeight * guessRow * 2 - guessMoveY;

        PVector[] hexs = new PVector[3];
        hexs[0] = new PVector((float) collumnVal, (float) (rowVal));
        if(vector.x < collumnVal) {
            hexs[1] = new PVector((float) (collumnVal - triangleWidth * 1.5), (float) (rowVal - triangleHeight));
            hexs[2] = new PVector((float) (collumnVal - triangleWidth * 1.5), (float) (rowVal + triangleHeight));
        } else {
            hexs[1] = new PVector((float) (collumnVal + triangleWidth * 1.5), (float) (rowVal - triangleHeight));
            hexs[2] = new PVector((float) (collumnVal + triangleWidth * 1.5), (float) (rowVal + triangleHeight));
        }

        int closest = 0;
        double dist = hexs[0].dist(vector);
        for(int i=1; i<hexs.length; i++) {
            double thisDist = hexs[i].dist(vector);
            if(thisDist < dist) {
                dist = thisDist;
                closest = i;
            }
        }

        PVector closestHexaflexagon = hexs[closest];
        long collumn = Math.round(closestHexaflexagon.x / (triangleWidth * 1.5));
        double moveY = collumn % 2 != 0 ? triangleHeight : 0;
        long row = Math.round((vector.y + moveY) / (triangleHeight * 2));

        double rotation = 0;
        if(collumn % 2 != 0) {
            rotation = Math.PI * 1/3 * (new Integer[] {2, 4, 0}[(int) Math.floorMod(row, 3)]);
        } else {
            rotation = Math.PI * 1/3 * (new Integer[] {0, 2, 4}[(int) Math.floorMod(row, 3)]);
        }

        float dx = vector.x - closestHexaflexagon.x;
        float dy = vector.y - closestHexaflexagon.y;

        float rc = (float) Math.cos(rotation);
        float rs = (float) Math.sin(rotation);
        float ox = rc * dx + rs * dy;
        float oy = -rs * dx +rc * dy;

        return new PVector( ox + (float) hexaflexagon.getWidth() / 2,  oy + (float) hexaflexagon.getHeight() / 2);
    }
}
