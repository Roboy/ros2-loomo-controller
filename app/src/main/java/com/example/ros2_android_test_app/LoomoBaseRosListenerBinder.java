package com.example.ros2_android_test_app;

import android.util.Log;

import com.segway.robot.sdk.locomotion.sbv.Base;

public class LoomoBaseRosListenerBinder {
  public static final String TAG = "LocomotionSubscriber";

  private final Base mBase;
  private final LoomoRosListenerNode loomoRosListenerNode;

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
          mBase.setLinearVelocity((float) msg.getLinear().getX());
          mBase.setAngularVelocity((float) msg.getAngular().getZ());
        });
  }
}
