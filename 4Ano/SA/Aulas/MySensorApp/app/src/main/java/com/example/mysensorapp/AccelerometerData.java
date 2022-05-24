package com.example.mysensorapp;

public class AccelerometerData {
    public float valueX = 0.0f;
    public float valueY = 0.0f;
    public float valueZ = 0.0f;
    public int accuracy = 0;

    public AccelerometerData() {

    }

    @Override
    public String toString() {
        return "AccelerometerData{" +
                "valueX=" + String.format("%.01f", valueX) +
                ", valueY=" + String.format("%.01f", valueY) +
                ", valueZ=" + String.format("%.01f", valueZ) +
                '}';
    }
}
