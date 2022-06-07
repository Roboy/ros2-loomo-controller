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

    public String tf_prefix = params.getTfPrefix();  // LO01
    public boolean use_tf_prefix = params.getUseTfPrefix();  // true

    public LoomoRosListenerNode() {
        super("loomo_ros2_listener");
        Log.d(TAG, "Created instance of LoomoRosBridgeNode().");

        if (!use_tf_prefix){
            tf_prefix = "";
        }
    }

    public void addCmdVelSubscriber(Consumer<Twist> consumer){
        // Subscribe to commanded twist msgs (e.g. from joystick or autonomous driving software)
        cmdVelSubscriber = this.node.createSubscription(Twist.class, tf_prefix+params.getmCmdVelSubrTopic(), consumer);
    }
}
