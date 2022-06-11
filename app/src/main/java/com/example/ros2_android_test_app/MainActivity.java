package com.example.ros2_android_test_app;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.locomotion.sbv.Base;

import org.ros2.rcljava.RCLJava;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MainActivity extends ROSActivity implements CompoundButton.OnCheckedChangeListener {
    public static final String TAG = "MainRosActivity";

//    private Vision mVision;
//    private Sensor mSensor;
    private Base mBase;

//    private Button mKillAppButton;
//    private Button mTimeOffsetButton;

    //    public NtpTimeProvider mNtpTimeProvider;
//    private NtpTimeProvider ntpTimeProvider;

    private Switch mPubRsColorSwitch;
    private Switch mPubRsDepthSwitch;
    private Switch mPubFisheyeSwitch;
    private Switch mPubSensorSwitch;

    private Switch mPubTFSwitch;
//    private RealsensePublisher mRealsensePublisher;
//    private TFPublisher mTFPublisher;
    private LoomoBaseRosListenerBinder loomoBaseRosListenerBinder;
//
//    private SensorPublisher mSensorPublisher;

    private LoomoRosListenerNode loomoRosListenerNode;

    private Queue<Long> mDepthStamps;

    // loading params from yaml file
    private final NodeParams params = Utils.loadParams();

    private static final String IS_WORKING_TALKER = "isWorkingTalker";
    private static final String IS_WROKING_LISTENER = "isWorkingListener";

    private ListenerNode listenerNode;
    private TalkerNode talkerNode;

    private TextView listenerView;

    private static String logtag = MainActivity.class.getName();

    private boolean isWorkingListener;
    private boolean isWorkingTalker;

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

        // Add a button to show the NTP time offset when clicked
//        mTimeOffsetButton = (Button) findViewById(R.id.timeoffset);
//        mTimeOffsetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Toast message that displays NTP time offset in app
//                Toast toast = Toast.makeText(getApplicationContext(), "NTP time offset: " + Math.round(System.currentTimeMillis() - ntpTimeProvider.getCurrentTime().toSeconds()*1000) + " ms", Toast.LENGTH_LONG);
//                toast.show();
//            }
//        });

        // Add some switches to turn on/off sensor publishers
        mPubRsColorSwitch = (Switch) findViewById(R.id.rscolor);
        mPubRsDepthSwitch = (Switch) findViewById(R.id.rsdepth);
        mPubFisheyeSwitch = (Switch) findViewById(R.id.fisheye);
        mPubTFSwitch = (Switch) findViewById(R.id.tf);
        mPubSensorSwitch = (Switch) findViewById(R.id.sensor);

        // Add some listeners to the states of the switches
        mPubRsColorSwitch.setOnCheckedChangeListener(this);
        mPubRsDepthSwitch.setOnCheckedChangeListener(this);
        mPubFisheyeSwitch.setOnCheckedChangeListener(this);
        mPubTFSwitch.setOnCheckedChangeListener(this);
        mPubSensorSwitch.setOnCheckedChangeListener(this);

        // Keep track of timestamps when images published, so corresponding TFs can be published too
        mDepthStamps = new ConcurrentLinkedDeque<>();

        // Start an instance of the LoomoRosBridgeNode
        loomoRosListenerNode = new LoomoRosListenerNode();

//        // get Vision SDK instance
//        mVision = Vision.getInstance();
//        mVision.bindService(this, mBindVisionListener);
//
//        // get Sensor SDK instance
//        mSensor = Sensor.getInstance();
//        mSensor.bindService(this, mBindStateListener);
//

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
                loomoBaseRosListenerBinder.start_listening();
            }

            @Override
            public void onUnbind(String reason) {
                Log.d(TAG, "onUnbind() called with: reason = [" + reason + "]");
            }
        });

        RCLJava.rclJavaInit();

        Toast.makeText(getApplicationContext(), "Connected to ROS master at URI: " + params.getMasterURI(), Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putBoolean(IS_WROKING_LISTENER, isWorkingListener);
            outState.putBoolean(IS_WORKING_TALKER, isWorkingTalker);
        }
        if(lock.isHeld()) {
            Log.d(logtag, "release lock");
            lock.release();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        Log.d(TAG, "onCheckedChanged -- someone has clicked a button");
        // Someone has clicked a button - handle it here
        switch (compoundButton.getId()) {
            case R.id.rscolor:
//                mRealsensePublisher.mIsPubRsColor = isChecked;
//                if (isChecked) {
//                    mRealsensePublisher.start_color();
//                } else {
//                    mRealsensePublisher.stop_color();
//                }
                break;
            case R.id.rsdepth:
//                mRealsensePublisher.mIsPubRsDepth = isChecked;
//                if (isChecked) {
//                    mRealsensePublisher.start_depth();
//                } else {
//                    mRealsensePublisher.stop_depth();
//                }
                break;
            case R.id.fisheye:
//                mRealsensePublisher.mIsPubFisheye = isChecked;
//                if (isChecked) {
//                    mRealsensePublisher.start_fisheye();
//                } else {
//                    mRealsensePublisher.stop_fisheye();
//                }
                break;
            case R.id.tf:
                Log.d(TAG, "TF clicked.");
//                mTFPublisher.mIsPubTF = isChecked;
//                if (isChecked) {
//                    mTFPublisher.start_tf();
//                } else {
//                    mTFPublisher.stop_tf();
//                }
                break;
            case R.id.sensor:
                Log.d(TAG, "Sensor clicked.");
//                mSensorPublisher.mIsPubSensor = isChecked;
//                if (isChecked) {
//                    mSensorPublisher.start_sensor();
//                } else {
//                    mSensorPublisher.stop_sensor();
//                }
                break;
        }
    }
}