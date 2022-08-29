package com.example.ros2_android_test_app;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.segway.robot.sdk.perception.sensor.Sensor;
import com.segway.robot.sdk.perception.sensor.SensorData;

import java.util.Arrays;

public class SensorRosPublisherBinder {
  private static final String TAG = "SensorPublisherBinder";

  private final Sensor mSensor;
  private final SensorPublisherNode sensorPublisherNode;
  private final MutableLiveData<Boolean> emergencyStopLiveData;

  private double emergencyStopThreshold = 50.0;

  public SensorRosPublisherBinder(Sensor mSensor, SensorPublisherNode sensorPublisherNode, MutableLiveData<Boolean> emergencyStopLiveData) {
    this.mSensor = mSensor;
    this.sensorPublisherNode = sensorPublisherNode;
    this.emergencyStopLiveData = emergencyStopLiveData;

    this.sensorPublisherNode.setUltrasonicSampler(this::sampleUltrasonicDistanceInCentimeters);
  }

  public double getEmergencyStopThreshold() {
    return emergencyStopThreshold;
  }


  public void setEmergencyStopThreshold(double emergencyStopThreshold) {
    this.emergencyStopThreshold = emergencyStopThreshold;
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
