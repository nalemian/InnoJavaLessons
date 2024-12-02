package ru.inno.nalemian.lessons.assignment3;

/**
 * thrown when the smart device was not found
 */
public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String invalidCommand) {
        super(invalidCommand);
    }
}
