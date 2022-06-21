package com.example.ros2_android_test_app;

/* Copyright 2017 Esteve Fernandez <esteve@apache.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.executors.Executor;
import org.ros2.rcljava.executors.SingleThreadedExecutor;

import java.util.Timer;
import java.util.TimerTask;

public class ROSActivity extends AppCompatActivity {
  private static final String TAG = "RosActivity";

  private static final long SPINNER_DELAY = 500;
  private static final long SPINNER_PERIOD_MS = 200;

  private Executor rosExecutor;
  private Timer timer;
  private Handler handler;


  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.handler = new RosHandler(getMainLooper());
    RCLJava.rclJavaInit();
    this.rosExecutor = this.createExecutor();
  }

  @Override
  protected void onResume() {
    super.onResume();
    timer = new Timer();
    timer.scheduleAtFixedRate(
        new TimerTask() {
          public void run() {
            Log.d(TAG, "send handler message 0");
            handler.sendEmptyMessage(0); // spin some
          }
        },
        SPINNER_DELAY,
        SPINNER_PERIOD_MS);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (timer != null) {
      timer.cancel();
    }
  }

  public Handler getHandler() {
    return handler;
  }

  public Executor getExecutor() {
    return this.rosExecutor;
  }

  protected Executor createExecutor() {
    return new SingleThreadedExecutor();
  }

  private class RosHandler extends Handler {

    public RosHandler(@NonNull Looper looper) {
      super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
      Log.d(TAG, "handle message called");
      if (msg.what == 0) { // spin some
        Log.d(TAG, "spinning some");
        getExecutor().spinSome();
      } else {
        Log.w(TAG, "Unknown handler message: " + msg);
      }
    }
  }
}
