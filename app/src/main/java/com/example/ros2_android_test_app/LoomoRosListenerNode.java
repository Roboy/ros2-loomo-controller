package com.example.ros2_android_test_app;

import android.util.Log;

import org.ros2.rcljava.consumers.Consumer;
import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.subscription.Subscription;

import geometry_msgs.msg.Twist;

public class LoomoRosListenerNode extends BaseComposableNode {
  private static final String TAG = "LoomoRosBridgeNode";

  private final NodeParams params = Utils.loadParams();

  public Subscription<Twist> cmdVelSubscriber;
  private Consumer<Twist> consumer;

  public String tf_prefix = params.getTfPrefix(); // LO01
  public boolean use_tf_prefix = params.getUseTfPrefix(); // true

  public LoomoRosListenerNode() {
    super("loomo_ros2_listener");
    Log.d(TAG, "Created instance of LoomoRosBridgeNode().");

    if (!use_tf_prefix) {
      tf_prefix = "";
    }
  }

  public void createLocomotionCommandsSubscriber() {
    cmdVelSubscriber =
        this.node.createSubscription(
            Twist.class,
            tf_prefix + params.getmCmdVelSubrTopic(),
            (msg) -> {
              Log.d(TAG, "recieved locomotion msg: " + msg.toString());
              if (consumer != null) {
                Log.d(TAG, "consuming the loco msg");
                consumer.accept(msg);
              } else {
                Log.d(TAG, "consumer not available -> skipping the loco msg");
              }
            });
  }

  public void setConsumer(Consumer<Twist> consumer) {
    this.consumer = consumer;
  }
}
