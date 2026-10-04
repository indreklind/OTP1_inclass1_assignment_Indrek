package org.example;

public class TempRecord {

    private int id;
    private double distance;
    private double speed;
    private double time;
    private int unitId;

    public TempRecord(double distance, double speed, double time, int unitId) {
        this.distance = distance;
        this.speed = speed;
        this.time = time;
        this.unitId = unitId;
    }

    public int getId() {
        return id;
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

    public int getUnitId() {
        return unitId;
    }
}