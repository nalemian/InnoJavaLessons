package ru.inno.nalemian.lessons.assignment3;

public class DeviceNotChargeableException extends RuntimeException {
    public DeviceNotChargeableException(String message) {
        super(message);
    }
}
