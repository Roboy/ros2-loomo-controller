package com.example.ros2_android_test_app;

import org.ros2.rcljava.interfaces.MessageDefinition;

public class UltrasonicData  implements MessageDefinition {
    private float data;
    @Override
    public long getFromJavaConverterInstance() {
        return 0;
    }

    @Override
    public long getToJavaConverterInstance() {
        return 0;
    }

    @Override
    public long getTypeSupportInstance() {
        return 0;
    }

    @Override
    public long getDestructorInstance() {
        return 0;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
