package com.example.ros2_android_test_app;

import android.os.Environment;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by kai on 17-11-2.
 */

public class Utils {
    private static final String TAG = "Utils";

    public static double platformStampToSecond(long stamp) {
        return (double)stamp/1.0E6D;
    }

    public static long platformStampInMillis(long stamp) {
        return (long)((double)stamp/1.0E3D);
    }

    public static NodeParams loadParams() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        NodeParams params;
        // *** Have to make sure most updated version of this file is located at /sdcard/params.yaml on the Loomo ***
        // If not or you have made changes to params.yaml in the android_loomo_ros package, run: adb push params.yaml /sdcard/params.yaml
        String filepath = "params.yaml";
        try {
            File paramsFile = new File(Environment.getExternalStorageDirectory(), filepath);
            params = mapper.readValue(paramsFile, NodeParams.class);
            Log.d(TAG, "Successfully using parameters in " + filepath);
        } catch (IOException e) {
            Log.d(TAG, "Could not read file \"" + filepath + "\": " + e.getMessage() + "\nGoing to use default parameter values");
            e.printStackTrace();
            params = new NodeParams();
        }
        return params;
    }
}
