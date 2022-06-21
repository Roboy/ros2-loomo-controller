package com.example.ros2_android_test_app;

import android.util.Log;

import com.segway.robot.sdk.perception.sensor.Sensor;
import com.segway.robot.sdk.perception.sensor.SensorData;

import java.util.Arrays;

public class SensorRosPublisherBinder {
  private static final String TAG = "SensorPublisherBinder";

  private final Sensor mSensor;
  private final SensorPublisherNode sensorPublisherNode;

  public SensorRosPublisherBinder(Sensor mSensor, SensorPublisherNode sensorPublisherNode) {
    this.mSensor = mSensor;
    this.sensorPublisherNode = sensorPublisherNode;

    this.sensorPublisherNode.setUltrasonicSampler(this::sampleUltrasonicDistanceInCentimeters);
  }

  public float sampleUltrasonicDistanceInCentimeters() {
    SensorData mInfraredData = mSensor.querySensorData(Arrays.asList(Sensor.INFRARED_BODY)).get(0);
    float mInfraredDistanceLeft = mInfraredData.getIntData()[0];
    float mInfraredDistanceRight = mInfraredData.getIntData()[1];

    SensorData mUltrasonicData =
        mSensor.querySensorData(Arrays.asList(Sensor.ULTRASONIC_BODY)).get(0);
    float mUltrasonicDistance = mUltrasonicData.getIntData()[0];
    float mUltrasonicDistanceInCentimeters = mUltrasonicDistance / 10;

    Log.d(
        TAG,
        "Ultrasonic Sample result: \n"
            + "left (cm): "
            + mInfraredDistanceLeft / 10
            + "\n"
            + "right (cm):"
            + mInfraredDistanceRight / 10
            + "\n"
            + "ultrasonicDistance (cm): "
            + mUltrasonicDistanceInCentimeters
            + "\n");

    return mUltrasonicDistanceInCentimeters;
  }
}
