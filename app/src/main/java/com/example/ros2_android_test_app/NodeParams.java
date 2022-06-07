package com.example.ros2_android_test_app;

/**
 * Used for loading params from a yaml file.
 */
public class NodeParams {
    // MainActivity.java
    private String masterURI;
    private String ntpServerIP;

    // RealsensePublisher.java
    private String rsDepthOpticalFrame;
    private String rsColorOpticalFrame;
    private String fisheyeOpticalFrame;

    private int mRsColorWidth;
    private int mRsColorHeight;
    private int mRsDepthWidth;
    private int mRsDepthHeight;
    private int mFisheyeWidth;
    private int mFisheyeHeight;

    // LoomoRosBridgeNode.java
    private String nodeName;
    private String tfPrefix;

    private boolean shouldPubUltrasonic;
    private boolean shouldPubInfrared;
    private boolean shouldPubBasePitch;
    private boolean useTfPrefix;


    private String mFisheyeCamPubrTopic;
    private String mFisheyeCompressedPubrTopic;
    private String mFisheyeCamInfoPubrTopic;
    private String mRsColorPubrTopic;
    private String mRsColorCompressedPubrTopic;
    private String mRsColorInfoPubrTopic;
    private String mRsDepthPubrTopic;
    private String mRsDepthInfoPubrTopic;
    private String mTfPubrTopic;
    private String mInfraredPubrTopic;
    private String mUltrasonicPubrTopic;
    private String mBasePitchPubrTopic;

    private String mCmdVelSubrTopic;

    public NodeParams() {
        this.masterURI = "http://192.168.42.134:11311/";
        this.ntpServerIP = "192.168.42.134";
        this.rsDepthOpticalFrame = "rs_depth_optical_frame";
        this.rsColorOpticalFrame = "rs_color_optical_frame";
        this.fisheyeOpticalFrame = "fisheye_optical_frame";
        this.mRsColorWidth = 640;
        this.mRsColorHeight = 480;
        this.mRsDepthWidth = 320;
        this.mRsDepthHeight = 240;
        this.mFisheyeWidth = 640;
        this.mFisheyeHeight = 480;
        this.nodeName = "loomo_ros_bridge_node";
        this.tfPrefix = "LO01";
        this.shouldPubUltrasonic = false;
        this.shouldPubInfrared = false;
        this.shouldPubBasePitch = true;
        this.useTfPrefix = true;
        this.mFisheyeCamPubrTopic = "/fisheye/rgb";
        this.mFisheyeCompressedPubrTopic = "/fisheye/rgb/compressed";
        this.mFisheyeCamInfoPubrTopic = "/fisheye/camera_info";
        this.mRsColorPubrTopic = "/realsense_loomo/rgb";
        this.mRsColorCompressedPubrTopic = "/realsense_loomo/rgb/compressed";
        this.mRsColorInfoPubrTopic = "/realsense_loomo/rgb/camera_info";
        this.mRsDepthPubrTopic = "/realsense_loomo/depth";
        this.mRsDepthInfoPubrTopic = "/realsense_loomo/depth/camera_info";
        this.mTfPubrTopic = "/tf";
        this.mInfraredPubrTopic = "/infrared";
        this.mUltrasonicPubrTopic = "/ultrasonic";
        this.mBasePitchPubrTopic = "/base_pitch";
        this.mCmdVelSubrTopic = "/cmd_vel";
    }

    public String getMasterURI() {
        return masterURI;
    }

    public String getNtpServerIP() {
        return ntpServerIP;
    }

    public String getRsDepthOpticalFrame() {
        return rsDepthOpticalFrame;
    }

    public String getRsColorOpticalFrame() {
        return rsColorOpticalFrame;
    }

    public String getFisheyeOpticalFrame() {
        return fisheyeOpticalFrame;
    }

    public int getmRsColorWidth() {
        return mRsColorWidth;
    }

    public int getmRsColorHeight() {
        return mRsColorHeight;
    }

    public int getmRsDepthWidth() {
        return mRsDepthWidth;
    }

    public int getmRsDepthHeight() {
        return mRsDepthHeight;
    }

    public int getmFisheyeWidth() {
        return mFisheyeWidth;
    }

    public int getmFisheyeHeight() {
        return mFisheyeHeight;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getTfPrefix() {
        return tfPrefix;
    }

    public boolean getShouldPubUltrasonic() {
        return shouldPubUltrasonic;
    }

    public boolean getShouldPubInfrared() {
        return shouldPubInfrared;
    }

    public boolean getShouldPubBasePitch() {
        return shouldPubBasePitch;
    }

    public boolean getUseTfPrefix() {
        return useTfPrefix;
    }

    public String getmFisheyeCamPubrTopic() {
        return mFisheyeCamPubrTopic;
    }

    public String getmFisheyeCompressedPubrTopic() {
        return mFisheyeCompressedPubrTopic;
    }

    public String getmFisheyeCamInfoPubrTopic() {
        return mFisheyeCamInfoPubrTopic;
    }

    public String getmRsColorPubrTopic() {
        return mRsColorPubrTopic;
    }

    public String getmRsColorCompressedPubrTopic() {
        return mRsColorCompressedPubrTopic;
    }

    public String getmRsColorInfoPubrTopic() {
        return mRsColorInfoPubrTopic;
    }

    public String getmRsDepthPubrTopic() {
        return mRsDepthPubrTopic;
    }

    public String getmRsDepthInfoPubrTopic() {
        return mRsDepthInfoPubrTopic;
    }

    public String getmTfPubrTopic() {
        return mTfPubrTopic;
    }

    public String getmInfraredPubrTopic() {
        return mInfraredPubrTopic;
    }

    public String getmUltrasonicPubrTopic() {
        return mUltrasonicPubrTopic;
    }

    public String getmBasePitchPubrTopic() {
        return mBasePitchPubrTopic;
    }

    public String getmCmdVelSubrTopic() {
        return mCmdVelSubrTopic;
    }
}
