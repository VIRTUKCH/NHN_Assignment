package com.nhnacademy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;

public class TestVector {
 
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> {
            int dx = 100;
            int dy = -100;
            Vector vector = new Vector(dx, dy);

            assertEquals(dx, vector.getDX());
            assertEquals(dy, vector.getDY());
        });
    }

    @Test
    public void testSet() {
        assertDoesNotThrow(() -> {
            Vector targetVector = new Vector(100, -100);
            Vector otherVector = new Vector(0, 0);

            otherVector.set(targetVector);

            targetVector.equals(otherVector);
        });
    }
}