package com.example.ros2_android_test_app;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.Toast;

import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.locomotion.sbv.Base;
import com.segway.robot.sdk.perception.sensor.Sensor;
import com.segway.robot.sdk.perception.sensor.SensorData;

import org.ros2.rcljava.RCLJava;

import java.util.Arrays;
import java.util.Queue;

public class MainActivity extends ROSActivity {
    public static final String TAG = "MainRosActivity";

//    private Vision mVision;
    private Sensor mSensor;
    private Base mBase;

    private LoomoBaseRosListenerBinder loomoBaseRosListenerBinder;
    private LoomoSensorRosListenerBinder loomoSensorRosListenerBinder;
    private LoomoRosListenerNode loomoRosListenerNode;
    private UltrasonicNode ultrasonicNode;

    private Queue<Long> mDepthStamps;

    // loading params from yaml file
    private final NodeParams params = Utils.loadParams();

    private static String logtag = MainActivity.class.getName();

    WifiManager.MulticastLock lock;

    /** Called when the activity is first created. */
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called");

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loomo_ros2_controller);

        final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        lock = wifi.createMulticastLock("ssdp");
        lock.acquire();

        // Start an instance of the LoomoRosBridgeNode
        loomoRosListenerNode = new LoomoRosListenerNode();
        getExecutor().addNode(loomoRosListenerNode);

        // get Locomotion SDK instance
        mBase = Base.getInstance();
        mBase.bindService(this, new ServiceBinder.BindStateListener() {
            @Override
            public void onBind() {
                Log.d(TAG, "mBindLocomotionListener onBind() called");
                if (loomoBaseRosListenerBinder == null) {
                    Log.d(TAG, "mBindLocomotionListener creating LocomotionSubscriber instance.");
                    loomoBaseRosListenerBinder = new LoomoBaseRosListenerBinder(mBase, loomoRosListenerNode);
                    Log.d(TAG, "mBindLocomotionListener created LocomotionSubscriber instance.");
                }
            }

            @Override
            public void onUnbind(String reason) {
                Log.d(TAG, "onUnbind() called with: reason = [" + reason + "]");
            }
        });

        mSensor = Sensor.getInstance();
        mSensor.bindService(this, new ServiceBinder.BindStateListener() {
            @Override
            public void onBind() {
                if (loomoSensorRosListenerBinder == null) {
                    Handler h = new Handler();
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            ultrasonicNode = new UltrasonicNode();
                            getExecutor().addNode(ultrasonicNode);
                            loomoSensorRosListenerBinder = new LoomoSensorRosListenerBinder(mSensor, ultrasonicNode);
                        }
                    };
                    h.postDelayed(r, 5000);

                    Log.d(TAG, "mBindLocomotionListener creating LocomotionSubscriber instance.");
                    Log.d(TAG, "mBindLocomotionListener created LocomotionSubscriber instance.");
                }
            }

            @Override
            public void onUnbind(String reason) {

            }
        });


        RCLJava.rclJavaInit();

        Toast.makeText(getApplicationContext(), "Connected to ROS master at URI: " + params.getMasterURI(), Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(lock.isHeld()) {
            Log.d(logtag, "release lock");
            lock.release();
        }
        super.onSaveInstanceState(outState);
    }
}