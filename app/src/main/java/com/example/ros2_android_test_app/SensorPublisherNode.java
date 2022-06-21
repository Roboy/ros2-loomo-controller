package com.example.ros2_android_test_app;

import android.util.Log;

import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.publisher.Publisher;
import org.ros2.rcljava.timer.WallTimer;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class SensorPublisherNode extends BaseComposableNode {

  private static final String TAG = SensorPublisherNode.class.getName();
  private static final String TOPIC = "LO01/ultrasonic";

  public Publisher<std_msgs.msg.String> ultrasonicPublisher;
  private WallTimer timer;

  private Supplier<Float> ultrasonicSampler;

  public SensorPublisherNode() {
    super("loomo_ros2_publisher_sensor");

    // create publisher in initializer or else it fails on
    // .../build/std_msgs/rosidl_generator_java/std_msgs/msg/String.ep.rosidl_typesupport_fastrtps_c.cpp#std_msgs_msg_String__convert_from_java
    ultrasonicPublisher = this.node.createPublisher(std_msgs.msg.String.class, TOPIC);

    Log.d(TAG, "starting ultrasonic sensor publisher");
    if (this.timer != null) {
      this.timer.cancel();
    }
    this.timer =
        node.createWallTimer(
            500,
            TimeUnit.MILLISECONDS,
            () -> {
              if (ultrasonicSampler != null) {
                Log.d(TAG, "publishing ultrasonic sample");
                this.ultrasonicPublisher.publish(getRosMessage(ultrasonicSampler.get().toString()));
              } else {
                Log.d(TAG, "ultrasonicSampler is null skipping publish step");
              }
            });
  }

  private std_msgs.msg.String getRosMessage(String message) {
    return new std_msgs.msg.String().setData(message);
  }

  public void setUltrasonicSampler(Supplier<Float> ultrasonicSampler) {
    this.ultrasonicSampler = ultrasonicSampler;
  }

  public void stopUltrasonicPublisher() {
    Log.d(TAG, "stopping ultrasonic sensor publisher");
    if (this.timer != null) {
      this.timer.cancel();
    }
  }
}
