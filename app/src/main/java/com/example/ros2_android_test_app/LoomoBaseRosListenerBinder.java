package com.example.ros2_android_test_app;

import android.util.Log;

import com.segway.robot.sdk.locomotion.sbv.Base;

public class LoomoBaseRosListenerBinder {
    public static final String TAG = "LocomotionSubscriber";

    private final Base mBase;
    private final LoomoRosListenerNode loomoRosListenerNode;

    public void setEmergencyStop(boolean emergencyStop) {
        this.emergencyStop = emergencyStop;
        Log.d(TAG, "emegencyStop is set to " + emergencyStop);
    }

    private boolean emergencyStop = false;

    public LoomoBaseRosListenerBinder(Base mBase, LoomoRosListenerNode loomoRosListenerNode) {
        this.mBase = mBase;
        this.loomoRosListenerNode = loomoRosListenerNode;

        // Configure Base to accept raw linear/angular velocity commands
        this.mBase.setControlMode(Base.CONTROL_MODE_RAW);

        this.loomoRosListenerNode.setConsumer(
                msg -> {
                    Log.d(
                            TAG,
                            "setting velocities acc. to locomotion msg -> Lin: "
                                    + msg.getLinear().getX()
                                    + " Ang: "
                                    + msg.getAngular().getZ());
                    float linear_velocity = (float) msg.getLinear().getX();
                    float angular_velocity = (float) msg.getAngular().getZ();
                    if(linear_velocity < 0f || !this.emergencyStop){
                        mBase.setLinearVelocity(linear_velocity);
                        mBase.setAngularVelocity(angular_velocity);
                    }
                });
    }


}
