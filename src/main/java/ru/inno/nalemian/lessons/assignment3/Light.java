package ru.inno.nalemian.lessons.assignment3;

public class Light extends SmartDevice implements Chargeable {
    private boolean charging;
    private BrightnessLevel brightnessLevel;
    private LightColor lightColor;

    public Light(Status status, boolean charging, BrightnessLevel brightnessLevel, LightColor lightColor) {
        super(status);
        this.charging = charging;
        this.brightnessLevel = brightnessLevel;
        this.lightColor = lightColor;
    }

    public LightColor getLightColor() {
        return lightColor;
    }

    public void setLightColor(LightColor lightColor) {
        if (this.getStatus() == Status.OFF) {
            System.out.println("You can't change the status of the Light " + this.getDeviceId() + " while it is off");
        } else {
            this.lightColor = lightColor;
            System.out.println("Light " + this.getDeviceId() + " color is set to " + lightColor);
        }
    }

    public BrightnessLevel getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(BrightnessLevel brightnessLevel) {
        if (this.getStatus() == Status.OFF) {
            System.out.println("You can't change the status of the Light " + this.getDeviceId() + " while it is off");
        } else {
            this.brightnessLevel = brightnessLevel;
            System.out.println("Light " + this.getDeviceId() + " brightness level is set to " + brightnessLevel);
        }
    }

    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public boolean startCharging() {
        if (charging) {
            System.out.println("Light " + this.getDeviceId() + " is already charging");
            return false;
        }
        charging = true;
        System.out.println("Light " + this.getDeviceId() + " is charging");
        return true;
    }

    @Override
    public boolean stopCharging() {
        if (!charging) {
            System.out.println("Light " + this.getDeviceId() + " is not charging");
            return false;
        }
        charging = false;
        System.out.println("Light " + this.getDeviceId() + " stopped charging");
        return true;
    }

    @Override
    public String displayStatus() {
        return "Light " + this.getDeviceId() + " is " + this.getStatus() + ", the color is " + lightColor + ", the charging status is " + charging + ", and the brightness level is " + brightnessLevel;
    }
}
