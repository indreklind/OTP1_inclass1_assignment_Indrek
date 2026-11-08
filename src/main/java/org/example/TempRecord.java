
package org.example;

public class TempRecord {

    private double distance;
    private double speed;
    private double time;

    public TempRecord(double distance, double speed, double time) {
        this.distance = distance;
        this.speed = speed;
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTime() {
        return time;
    }
}
