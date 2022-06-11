# Ros2LoomoController

This is an Android project to control Loomo according to commands recieved from a ROS2 topic.

The project is based on these two projects:
- [ros1 android loomo controller](https://github.com/mit-acl/android_loomo_ros_core)
- [ros2-android-test-app](https://github.com/YasuChiba/ros2-android-test-app)

# How to use the project

## RCL Java dependencies
This project uses rcl java client build from rclcpp. The .`*.jar` files coming from there are already included under `/app/libs` however the native `*.so` are too large. So you should either 
- Build them from scratch following https://github.com/Roboy/ros2-android-build
- Or ask from a team member (e.g. @meverg)

## Installing
You can install, run and debug like a usual Android project via Android Stuido or adb cli tool.

As with [ros1 android loomo controller](https://github.com/mit-acl/android_loomo_ros_core) this project is configured with `params.yaml` file too. So make sure the `params.yaml` file located at the root directory of the repo is also on the Loomo at `/sdcard/params.yaml`. This can be done by running: 
> `adb push params.yaml /sdcard/`

## Controlling Locomotion
From a computer connected to the same wifi as Loomo, after setting up ros2, publish geometry Twist messages to the topic Loomo node listening on like:
> `ros2 topic pub -r 10 /LO01/cmd_vel geometry_msgs/msg/Twist "{linear: {x: 0.1, y: 0.0, z: 0.0}, angular: {x: 0.0, y: 0.0, z: 0.0}}"`

The x component of the linear is the linear velocity of the Loomo. The z component of the angular is the angular velocity of the Loomo.

