package com.example.ros2_android_test_app;

import android.util.Log;

import com.segway.robot.sdk.locomotion.sbv.Base;
import com.segway.robot.sdk.perception.sensor.Sensor;
import com.segway.robot.sdk.perception.sensor.SensorData;

import java.util.Arrays;

public class LoomoBaseRosListenerBinder {
    public static final String TAG = "LocomotionSubscriber";

    private final Base mBase;
    private final Sensor mSensor;

    private final LoomoRosListenerNode loomoRosListenerNode;

    public void setEmergencyStopThreshold(double emergencyStopThreshold) {
        this.emergencyStopThreshold = emergencyStopThreshold;
    }

    private double emergencyStopThreshold = 50.0;

    public void setEmergencyStop(boolean emergencyStop) {
        //this.emergencyStop = emergencyStop;
        Log.d(TAG, "emegencyStop is set to " + emergencyStop);
    }

    private boolean emergencyStop = false;


    public void setEmergencyStopEnabled(boolean emergencyStopEnabled) {
        this.emergencyStopEnabled = emergencyStopEnabled;
    }

    private boolean emergencyStopEnabled = true;



    public LoomoBaseRosListenerBinder(Base mBase, Sensor mSensor,LoomoRosListenerNode loomoRosListenerNode) {
        this.mBase = mBase;
        this.mSensor = mSensor;
        this.loomoRosListenerNode = loomoRosListenerNode;

        // Configure Base to accept raw linear/angular velocity commands
        this.mBase.setControlMode(Base.CONTROL_MODE_RAW);

        this.loomoRosListenerNode.setConsumer(
                msg -> {

                    SensorData mUltrasonicData =
                            mSensor.querySensorData(Arrays.asList(Sensor.ULTRASONIC_BODY)).get(0);
                    float mUltrasonicDistance = mUltrasonicData.getIntData()[0];
                    float mUltrasonicDistanceInCentimeters = mUltrasonicDistance / 10;

                    Log.d(
                            TAG,
                            "setting velocities acc. to locomotion msg -> Lin: "
                                    + msg.getLinear().getX()
                                    + " Ang: "
                                    + msg.getAngular().getZ());
                    float linear_velocity = (float) msg.getLinear().getX();
                    float angular_velocity = (float) msg.getAngular().getZ();
                    if(linear_velocity < 0f || mUltrasonicDistanceInCentimeters > this.emergencyStopThreshold || !this.emergencyStopEnabled){
                        mBase.setLinearVelocity(linear_velocity);
                        mBase.setAngularVelocity(angular_velocity);
                    }
                });
    }


}
