package ru.inno.nalemian.lessons.assignment3;

public abstract class SmartDevice implements Controllable {
    private Status status;
    private int deviceId;
    private static int numberOfDevices = 0;

    public SmartDevice(Status status) {
        this.status = status;
        this.deviceId = numberOfDevices++;
    }

    abstract public String displayStatus();

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean turnOff() {
        if (status == Status.OFF) {
            System.out.println(getClass().getSimpleName() + " " + deviceId + " is already off");
            return false;
        }
        status = Status.OFF;
        System.out.println(getClass().getSimpleName() + " " + deviceId + " is off");
        return true;
    }

    @Override
    public boolean turnOn() {
        if (status == Status.ON) {
            System.out.println(getClass().getSimpleName() + " " + deviceId + " is already on");
            return false;
        }
        status = Status.ON;
        System.out.println(getClass().getSimpleName() + " " + deviceId + " is on");
        return true;
    }

    @Override
    public boolean isOn() {
        return status == Status.ON;
    }

    public boolean checkStatusAccess() {
        return false;
    }
}
