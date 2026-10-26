package org.example;

public class TravelCalculator {

    public double calculateTime(double distance, double speed) {

        if (!Double.isFinite(distance)
                || !Double.isFinite(speed)
                || distance < 0
                || speed <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        double time = distance / speed;

        if (!Double.isFinite(time)) {
            throw new IllegalArgumentException("Invalid result");
        }

        return time;
    }
}