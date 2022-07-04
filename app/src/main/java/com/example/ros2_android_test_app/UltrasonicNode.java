package com.example.ros2_android_test_app;

import android.util.Log;

import org.ros2.rcljava.consumers.Consumer;
import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.publisher.Publisher;
import org.ros2.rcljava.subscription.Subscription;

import geometry_msgs.msg.Twist;

public class UltrasonicNode extends BaseComposableNode {
    private static final String TAG = "LoomoRosBridgeNode";

    private final NodeParams params = Utils.loadParams();

    public Publisher<std_msgs.msg.Float64> publisher;

    public String tf_prefix = params.getTfPrefix();  // LO01
    public boolean use_tf_prefix = params.getUseTfPrefix();  // true

    public UltrasonicNode() {
        super("loomo_ros2_ultasonic");
        Log.d(TAG, "Created instance of UltrasonicNode().");

        if (!use_tf_prefix){
            tf_prefix = "";
        }
        publisher = this.node.<std_msgs.msg.Float64>createPublisher(std_msgs.msg.Float64.class, tf_prefix+params.getmUltrasonicPubrTopic());
    }
}
