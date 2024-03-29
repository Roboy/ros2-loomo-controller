# Ros2LoomoController

This is an Android project to control Loomo according to commands recieved from a ROS2 topic.

The project is based on these two projects:
- [ros1 android loomo controller](https://github.com/mit-acl/android_loomo_ros_core)
- [ros2-android-test-app](https://github.com/YasuChiba/ros2-android-test-app)

# How to use the project
You can download and install the latest apk release to Loomo and make changes to `params.yaml` for further configuration. See `Configuration` below for details on configuring.

To build the project locally continue with the below steps.

## RCL Java dependencies
This project uses rcl java client build from rclcpp. The .`*.jar` files coming from there are already included under `/app/libs` however the native `*.so` are too large. So you should either 
- Build them from scratch following https://github.com/Roboy/ros2-android-build
- Or download from a large dependency release named like `vX.Y.Z-so_files` and copy the folder to respective `jniLibs/<android-processor-architecture>` place. e.g. `jniLibs/x86_64` for Loomo

> WATCH OUT
>
> As ros2-java README points out: 
>
> [Make sure to use Fast-RTPS as your DDS vendor and at least this revision.](https://github.com/ros2-java/ros2_java#including-android)
> 
> So add the snippet below in your `~/.bashrc` file
> ```
> # This ros2 middleware implementation is a must-have for compliance with the ros2-java client
> export RMW_IMPLEMENTATION=rmw_fastrtps_cpp
> ```

## Installation
You can install, run and debug like a usual Android project via Android Stuido or adb cli tool.

## Configuration
As with [ros1 android loomo controller](https://github.com/mit-acl/android_loomo_ros_core) this project is configured with `params.yaml` file too. So make sure the `params.yaml` file located at the root directory of the repo is also on the Loomo at `/sdcard/params.yaml`. This can be done by running: 
> `adb push params.yaml /sdcard/`

## Controlling Locomotion
From a computer connected to the same wifi as Loomo, after setting up ros2, publish geometry Twist messages to the topic Loomo node listening on like:
> `ros2 topic pub -r 10 /LO01/cmd_vel geometry_msgs/msg/Twist "{linear: {x: 0.1, y: 0.0, z: 0.0}, angular: {x: 0.0, y: 0.0, z: 0.0}}"`

The x component of the linear is the linear velocity of the Loomo. The z component of the angular is the angular velocity of the Loomo.

