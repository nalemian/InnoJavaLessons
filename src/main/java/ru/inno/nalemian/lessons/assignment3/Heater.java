package ru.inno.nalemian.lessons.assignment3;

public class Heater extends SmartDevice {

    private int temperature;
    static final int MAX_HEATER_TEMP = 30;
    static final int MIN_HEATER_TEMP = 15;

    public Heater(Status status, int temperature) {
        super(status);
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean setTemperature(int temperature) {
        if (this.getStatus() == Status.OFF) {
            System.out.println("You can't change the status of the Heater " + this.getDeviceId() + " while it is off");
            return false;
        }
        if (temperature < MIN_HEATER_TEMP || temperature > MAX_HEATER_TEMP) {
            System.out.println("Heater " + this.getDeviceId() + " temperature should be in the range [15, 30]");
            return false;
        }
        this.temperature = temperature;
        System.out.println("Heater " + this.getDeviceId() + " temperature is set to " + temperature);
        return true;
    }

    @Override
    public String displayStatus() {
        return "Heater " + this.getDeviceId() + " is " + this.getStatus() + " and the temperature is " + temperature;
    }
}
