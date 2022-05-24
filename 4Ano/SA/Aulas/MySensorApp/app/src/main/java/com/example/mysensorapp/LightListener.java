package com.example.mysensorapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.provider.Settings;

import com.google.firebase.firestore.FirebaseFirestore;

public class LightListener implements SensorEventListener {
    private SensorManager sensorManager;
    private String TAG = "LightListener";
    private float light = 0.0f;
    Context context;

    public LightListener(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        light = sensorEvent.values[0];
        System.out.println("Luz: " + light);
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
