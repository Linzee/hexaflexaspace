package me.ienze.processing.hexaflexaspace;

import processing.core.PVector;

import java.awt.*;

/**
 * @author Ienze
 */
public class PrecalculatedFoldingCalculator implements FoldingCalculator {

    private FoldingCalculator calculator;
    private Rectangle cachedRange;
    private PVector[] cache;
    private int supersampling;

    public PrecalculatedFoldingCalculator(FoldingCalculator calculator, Rectangle cachedRange, int supersampling) {
        this.calculator = calculator;
        this.cachedRange = cachedRange;
        this.supersampling = supersampling;

        this.cache = new PVector[cachedRange.width * cachedRange.height * supersampling * supersampling];

        for(int x = 0; x < cachedRange.width * supersampling; x++) {
            for(int y = 0; y < cachedRange.height * supersampling; y++) {
                float vx = x / (float)supersampling;
                float vy = y / (float)supersampling;
                cache[y * cachedRange.width * supersampling + x] = calculator.fold(new PVector(vx + cachedRange.x, vy + cachedRange.y));
            }
        }
    }

    @Override
    public PVector fold(PVector vector) {
        if(cachedRange.contains(vector.x, vector.y)) {
            return cache[(Math.round(vector.y * supersampling) - cachedRange.y) * cachedRange.width * supersampling + (Math.round(vector.x * supersampling) - cachedRange.x)];
        }
        return calculator.fold(vector);
    }
}
