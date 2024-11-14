package ru.inno.nalemian.lessons.assignment3;

public class Camera extends SmartDevice implements Chargeable {
    static final int MAX_CAMERA_ANGLE = 60;
    static final int MIN_CAMERA_ANGLE = -60;
    private boolean charging;
    private boolean recording;
    private int angle;

    public Camera(Status status, boolean charging, boolean recording, int angle) {
        super(status);
        this.charging = charging;
        this.recording = recording;
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public boolean setCameraAngle(int angle) {
        if (this.getStatus() == Status.OFF) {
            System.out.println("You can't change the status of the Camera " + this.getDeviceId() + " while it is off");
            return false;
        }
        if (angle < MIN_CAMERA_ANGLE || angle > MAX_CAMERA_ANGLE) {
            System.out.println("Camera " + this.getDeviceId() + " angle should be in the range [-60, 60]");
            return false;
        }
        this.angle = angle;
        System.out.println("Camera " + this.getDeviceId() + " angle is set to " + angle);
        return true;
    }

    public boolean startRecording() {
        if (this.getStatus() == Status.OFF) {
            System.out.println("Camera " + this.getDeviceId() + " is not recording");
            return false;
        }
        if (recording) {
            System.out.println("Camera " + this.getDeviceId() + " is already recording");
            return false;
        }
        recording = true;
        System.out.println("Camera " + this.getDeviceId() + " started recording");
        return true;
    }

    public boolean stopRecording() {
        if (!recording) {
            System.out.println("Camera " + this.getDeviceId() + " is not recording");
            return false;
        }
        recording = false;
        System.out.println("Camera " + this.getDeviceId() + " stopped recording");
        return true;
    }

    public boolean isRecording() {
        return recording;
    }

    ;

    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public boolean startCharging() {
        if (charging) {
            System.out.println("Camera " + this.getDeviceId() + " is already charging");
            return false;
        }
        charging = true;
        return true;
    }

    @Override
    public boolean stopCharging() {
        if (!charging) {
            System.out.println("Camera " + this.getDeviceId() + " is not charging");
            return false;
        }
        charging = false;
        System.out.println("Camera " + this.getDeviceId() + " stopped charging");
        return true;
    }

    @Override
    public String displayStatus() {
        return "Camera " + this.getDeviceId() + " is " + this.getStatus() + ", the angle is " + angle + ", the charging status is " + charging + ", and the recording status is " + recording;
    }
}
