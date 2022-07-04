package com.example.ros2_android_test_app;

import android.os.Handler;
import android.util.Log;

import com.segway.robot.sdk.locomotion.sbv.Base;
import com.segway.robot.sdk.perception.sensor.Sensor;
import com.segway.robot.sdk.perception.sensor.SensorData;

import java.util.Arrays;

import std_msgs.msg.Float32;
import std_msgs.msg.Float64;

/**
 * Created by mfe on 7/24/18.
 */

public class LoomoSensorRosListenerBinder {
    public static final String TAG = "LocomotionSubscriber";

    private final Sensor mSensor;
    private final UltrasonicNode ultrasonicNode;

    public LoomoSensorRosListenerBinder(Sensor mSensor, UltrasonicNode ultrasonicNode){
        this.mSensor = mSensor;
        this.ultrasonicNode = ultrasonicNode;
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {

                new Thread( new Runnable(){
                    @Override
                    public void run(){
                        while (true) {
                            SensorData mUltrasonicData = mSensor.querySensorData(Arrays.asList(Sensor.ULTRASONIC_BODY)).get(0);
                            float mUltrasonicDistance = mUltrasonicData.getIntData()[0];
                            std_msgs.msg.Float64 msg = new std_msgs.msg.Float64();
                            msg.setData(mUltrasonicDistance);
                            Log.d(TAG, "ultrasonic reads: " + mUltrasonicDistance);
                            //ultrasonicNode.publisher.publish(msg);
                        }
                    }
                }).start();
            }
        };
        h.postDelayed(r, 5500);
    }
}
