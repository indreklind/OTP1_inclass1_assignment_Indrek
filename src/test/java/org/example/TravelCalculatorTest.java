package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TravelCalculatorTest {

    TravelCalculator calculator = new TravelCalculator();

    @Test
    void testNormalCalculation() {
        assertEquals(2.0, calculator.calculateTime(100, 50));
    }

    @Test
    void testZeroDistance() {
        assertEquals(0.0, calculator.calculateTime(0, 50));
    }

    @Test
    void testNegativeDistance() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTime(-100, 50));
    }

    @Test
    void testZeroSpeed() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTime(100, 0));
    }

    @Test
    void testNegativeSpeed() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTime(100, -50));
    }

    @Test
    void testInfiniteDistance() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTime(Double.POSITIVE_INFINITY, 50));
    }

    @Test
    void testNaNInput() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTime(Double.NaN, 50));
    }

    @Test
    void testInfiniteResult() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTime(Double.MAX_VALUE, 0.1));
    }
}