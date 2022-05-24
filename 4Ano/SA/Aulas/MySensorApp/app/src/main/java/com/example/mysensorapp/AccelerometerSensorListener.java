package com.example.mysensorapp;



import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AccelerometerSensorListener implements SensorEventListener {

    private SensorManager sensorManager;
    private String TAG = "AccelerometerSensorListener";
    public AccelerometerData acc = new AccelerometerData();
    public TextView textView;

    public AccelerometerSensorListener(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    public AccelerometerSensorListener(SensorManager sensorManager, TextView textView) {
        this.sensorManager = sensorManager;
        this.textView = textView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        acc.valueX = sensorEvent.values[0];
        acc.valueY = sensorEvent.values[1];
        acc.valueZ = sensorEvent.values[2];
        //sensorManager.unregisterListener(this);

        /*Log.d(TAG,
                "[Sensor] - X = " + acc.valueX  +
                     ", Y = " + acc.valueY +
                     ", Z = " + acc.valueZ
        );*/

        textView.setText(acc.toString());
        Map<String, Object> user = new HashMap<>();
        Timestamp time = Timestamp.now();
        user.put("Date",time);
        user.put("X", acc.valueX);
        user.put("Y", acc.valueY);
        user.put("Z", acc.valueZ);


        db.collection("acelerometro")
                .add(user)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
