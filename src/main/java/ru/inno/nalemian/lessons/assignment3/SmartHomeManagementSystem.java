package ru.inno.nalemian.lessons.assignment3;


import java.util.*;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;
import static java.lang.Math.min;

/**
 * A system for managing smart home elements
 */
public class SmartHomeManagementSystem {
    private final Map<String, SmartDevice> devices;

    public SmartHomeManagementSystem(List<SmartDevice> devices) {
        this.devices = new HashMap<>();
        for (SmartDevice device : devices) {
            this.devices.put(device.getClass().getSimpleName() + device.getDeviceId(), device);
        }
    }

    /**
     * The main entry point of the program, it initializes the smart devices and processes user commands
     * in a loop until the user ends the program
     */
    public static void main(String[] args) {
        SmartHomeManagementSystem system = new SmartHomeManagementSystem(generateListByTask());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("end")) {
                break;
            } else {
                System.out.println(system.handleCommand(command));
            }
        }
    }

    /**
     * Generates a list of smart devices,
     * includes lights, cameras, and heaters with specific properties
     *
     * @return a list of initialized SmartDevice objects
     */
    public static List<SmartDevice> generateListByTask() {
        List<SmartDevice> devices = new ArrayList<>();
        int id = 0;
        final int numberOfLights = 4;
        final int angleOfCamera = 45;
        final int numberOfHeaters = 4;
        final int temperatureOfHeater = 20;
        // Create and add lights to the list
        for (int i = 0; i < numberOfLights; i++) {
            Light light = new Light(Status.ON, false, BrightnessLevel.LOW, LightColor.YELLOW);
            light.setDeviceId(id);
            id++;
            devices.add(light);
        }
        for (int i = 0; i < 2; i++) {
            Camera camera = new Camera(Status.ON, false, false, angleOfCamera);
            camera.setDeviceId(id);
            id++;
            devices.add(camera);
        }
        for (int i = 0; i < numberOfHeaters; i++) {
            Heater heater = new Heater(Status.ON, temperatureOfHeater);
            heater.setDeviceId(id);
            id++;
            devices.add(heater);
        }
        return devices;
    }

    /**
     * Finds a smart device by its name and ID from a list of devices.
     *
     * @param name the name of the smart device
     * @param id   the ID of the smart device
     * @return the matching SmartDevice if found, null otherwise
     */
    private SmartDevice findDevice(String name, int id) {
        if (devices.get(name + id) == null) {
            throw new DeviceNotFoundException("The smart device was not found");
        }
        return devices.get(name + id);
    }

    /**
     * Checks a command to ensure it matches the format and checks if the device exists
     *
     * @param commandElements the command elements to validate
     * @param numberOfCommandElements the required number of command elements
     */
    private void checkFormatAndExistence(String[] commandElements, int numberOfCommandElements) {
        int maxDeviceId = -1000000000;
        int minDeviceId = 1000000000;
        if (commandElements.length != numberOfCommandElements) {
            throw new CommandRuntimeException("Invalid command");
        } else {
            for (Map.Entry<String, SmartDevice> entry : this.devices.entrySet()) {
                String key = entry.getKey();
                int index = parseInt(key.substring(key.length() - 1));
                maxDeviceId = max(maxDeviceId, index);
                minDeviceId = min(minDeviceId, index);
            }
            if ((!commandElements[1].equals("Camera") && !commandElements[1].equals("Heater")
                    && !commandElements[1].equals("Light")) || (parseInt(commandElements[2]) > maxDeviceId
                    || parseInt(commandElements[2]) < minDeviceId)) {
                throw new CommandRuntimeException("The smart device was not found");
            }
        }
    }

    /**
     * Turns a smart device on or off based on the provided command
     *
     * @param commandElements the command elements to validate
     * @param command         the command (1 - turn on, 2 - turn off)
     */
    public String turnOnOff(String[] commandElements, int command) {
        String turnOnOffName = commandElements[1];
        int turnOnOffId = parseInt(commandElements[2]);
        SmartDevice turnOnOffDevice = findDevice(turnOnOffName, turnOnOffId);
        if (command == 1) {
            if (turnOnOffDevice.isOn()) {
                return turnOnOffName + " " + turnOnOffId + " is already on";
            } else {
                return turnOnOffDevice.turnOn();
            }
        } else {
            if (!turnOnOffDevice.isOn()) {
                return turnOnOffName + " " + turnOnOffId + " is already off";
            } else {
                return turnOnOffDevice.turnOff();
            }
        }
    }

    /**
     * Starts or stops charging a smart device based on the provided command
     *
     * @param commandElements the command elements to validate
     * @param command         the command (1 - start charging, 2 - stop charging)
     */
    public String startStopCharging(String[] commandElements, int command) {
        String chargingName = commandElements[1];
        int chargingId = parseInt(commandElements[2]);
        SmartDevice chargingDevice = findDevice(chargingName, chargingId);
        if (chargingDevice != null) {
            if (chargingDevice instanceof Light || chargingDevice instanceof Camera) {
                if (command == 1) {
                    return ((Chargeable) chargingDevice).startCharging();
                } else {
                    return ((Chargeable) chargingDevice).stopCharging();
                }
            } else {
                return chargingName + " " + chargingId + " is not chargeable";
            }
        } else {
            return "The smart device was not found";
        }
    }

    /**
     * Checks the status of a smart device to determine if it is possible to perform an operation on it
     *
     * @param brightnessDevice the smart device to check
     */
    public String status(SmartDevice brightnessDevice) {
        if (brightnessDevice != null) {
            if (brightnessDevice.getStatus() == Status.OFF) {
                return "You can't change the status of the " + brightnessDevice.getClass().getSimpleName()
                        + " " + brightnessDevice.getDeviceId() + " while it is off";
            }
            return "true";
        } else {
            return "The smart device was not found";
        }
    }

    /**
     * Starts or stops recording for a camera device based on the provided command
     *
     * @param commandElements the command elements to validate
     * @param command         the command (1 - start recording, 2 - stop recording)
     */
    private String startStopRecording(String[] commandElements, int command) {
        String startStopRecName = commandElements[1];
        int startStopRecId = parseInt(commandElements[2]);
        SmartDevice startStopRecDevice = findDevice(startStopRecName, startStopRecId);
        String res = status(startStopRecDevice);
        if (res.equals("true")) {
            if (startStopRecDevice instanceof Camera camera) {
                if (command == 1) {
                    return camera.startRecording();
                } else {
                    return camera.stopRecording();
                }
            } else {
                return startStopRecName + " " + startStopRecId + " is not a camera";
            }
        } else {
            return res;
        }
    }

    /**
     * Handles the issued command and performs the corresponding action
     *
     * @param commandLine a string containing the command parameters
     */
    public String handleCommand(String commandLine) {
        String[] commandElements = commandLine.split(" ");
        String action = commandElements[0];
        if (action.equals("DisplayAllStatus")) {
            if (commandElements.length != 1) {
                return "Invalid command";
            } else {
                for (SmartDevice value : devices.values()) {
                    return value.displayStatus();
                }
            }
        } else {
            if (commandElements.length > 1) {
                final int firstLenOfCommandLine = 3;
                final int secondLenOfCommandLine = 4;
                try {
                    switch (action) {
                        case "TurnOn":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            return turnOnOff(commandElements, 1);

                        case "TurnOff":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            return turnOnOff(commandElements, 2);

                        case "StartCharging":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            return startStopCharging(commandElements, 1);

                        case "StopCharging":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            return startStopCharging(commandElements, 2);

                        case "SetTemperature":
                            final int indexOfTemperature = 3;
                            checkFormatAndExistence(commandElements, secondLenOfCommandLine);
                            String tempName = commandElements[1];
                            int tempId = parseInt(commandElements[2]);
                            int temperature = parseInt(commandElements[indexOfTemperature]);
                            SmartDevice tempDevice = findDevice(tempName, tempId);
                            String res = status(tempDevice);
                            if (res.equals("true")) {
                                if (tempDevice instanceof Heater) {
                                    return ((Heater) tempDevice).setTemperature(temperature);
                                } else {
                                    return tempName + " " + tempId + " is not a heater";
                                }
                            } else {
                                return res;
                            }

                        case "SetBrightness":
                            final int indexOfBrightness = 3;
                            checkFormatAndExistence(commandElements, secondLenOfCommandLine);
                            String brightnessName = commandElements[1];
                            int brightnessId = parseInt(commandElements[2]);
                            String brightnessLevel = commandElements[indexOfBrightness];
                            SmartDevice brightnessDevice = findDevice(brightnessName, brightnessId);
                            res = status(brightnessDevice);
                            if (res.equals("true")) {
                                if (brightnessDevice instanceof Light) {
                                    if (brightnessLevel.equals("LOW") || brightnessLevel.equals("MEDIUM")
                                            || brightnessLevel.equals("HIGH")) {
                                        return ((Light) brightnessDevice).setBrightnessLevel(
                                                BrightnessLevel.valueOf(brightnessLevel.toUpperCase()));
                                    } else {
                                        return "The brightness can only be one"
                                                + " of \"LOW\", \"MEDIUM\", or \"HIGH\"";
                                    }
                                } else {
                                    return brightnessName + " " + brightnessId + " is not a light";
                                }
                            } else {
                                return res;
                            }

                        case "SetColor":
                            final int indexOfColor = 3;
                            checkFormatAndExistence(commandElements, secondLenOfCommandLine);
                            String colorName = commandElements[1];
                            int colorId = parseInt(commandElements[2]);
                            String color = commandElements[indexOfColor];
                            SmartDevice colorDevice = findDevice(colorName, colorId);
                            res = status(colorDevice);
                            if (res.equals("true")) {
                                if (colorDevice instanceof Light) {
                                    if (color.equals("YELLOW") || color.equals("WHITE")) {
                                        return ((Light) colorDevice).setLightColor(
                                                LightColor.valueOf(color.toUpperCase()));
                                    } else {
                                        return "The light color can"
                                                + " only be \"YELLOW\" or \"WHITE\"";
                                    }
                                } else {
                                    return colorName + " " + colorId + " is not a light";
                                }
                            } else {
                                return res;
                            }

                        case "SetAngle":
                            final int indexOfAngle = 3;
                            checkFormatAndExistence(commandElements, secondLenOfCommandLine);
                            String angleName = commandElements[1];
                            int angleId = parseInt(commandElements[2]);
                            int angle = parseInt(commandElements[indexOfAngle]);
                            SmartDevice angleDevice = findDevice(angleName, angleId);
                            res = status(angleDevice);
                            if (res.equals("true")) {
                                if (angleDevice instanceof Camera) {
                                    return ((Camera) angleDevice).setCameraAngle(angle);
                                } else {
                                    return angleName + " " + angleId + " is not a camera";
                                }

                            } else {
                                return res;
                            }

                        case "StartRecording":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            return startStopRecording(commandElements, 1);

                        case "StopRecording":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            return startStopRecording(commandElements, 2);

                        default:
                            return "Invalid command";
                    }
                } catch (
                        CommandRuntimeException | DeviceNotFoundException e) {
                    return e.getMessage();
                } catch (
                        Exception e) {
                    return "Invalid command";
                }
            } else {
                return "Invalid command";
            }
        }
        return null;
    }
}

/**
 * Enum representing the brightness levels for smart devices
 */
enum BrightnessLevel {
    HIGH, MEDIUM, LOW
}

/**
 * Enum representing the light colors for smart devices
 */
enum LightColor {
    WHITE, YELLOW
}

/**
 * Enum representing the statuses of smart devices
 */
enum Status {
    OFF, ON
}

/**
 * Interface representing chargeable devices
 */
interface Chargeable {
    /**
     * Checks if the device is charging
     *
     * @return true if the device is charging, false otherwise
     */
    boolean isCharging();

    /**
     * Starts charging the device
     */
    String startCharging();

    /**
     * Stops charging the device
     */
    String stopCharging();
}

/**
 * Interface representing devices that can be controlled
 */
interface Controllable {
    /**
     * Turns off the device
     */
    String turnOff();

    /**
     * Turns on the device
     */
    String turnOn();

    /**
     * Checks if the device is on
     *
     * @return true if the device is on, false otherwise
     */
    boolean isOn();
}

/**
 * Abstract class representing a smart device
 */
abstract class SmartDevice implements Controllable {
    private Status status;
    private int deviceId;
    private static final int numberOfDevices = 0;

    public SmartDevice(Status status) {
        this.status = status;
    }

    /**
     * Displays the current status of the smart device, including its attributes
     *
     * @return A string representing the current status of the smart device
     */
    public abstract String displayStatus();

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
    public String turnOff() {
        if (Status.OFF.equals(status)) {
            return getClass().getSimpleName() + " " + deviceId + " is already off";
        }
        status = Status.OFF;
        return getClass().getSimpleName() + " " + deviceId + " is off";
    }

    @Override
    public String turnOn() {
        if (Status.ON.equals(status)) {
            return getClass().getSimpleName() + " " + deviceId + " is already on";
        }
        status = Status.ON;
        return getClass().getSimpleName() + " " + deviceId + " is on";
    }

    @Override
    public boolean isOn() {
        return Status.ON.equals(status);
    }

    /**
     * Checks if the current status of the device allows for modification of attributes
     *
     * @return true if the device's attributes can be modified, false otherwise
     */
    public boolean checkStatusAccess() {
        return Status.ON.equals(status);
    }
}

/**
 * Class representing a light
 */
class Light extends SmartDevice implements Chargeable {
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

    /**
     * Sets the color of the light
     *
     * @param lightColor the light color to set
     */
    public String setLightColor(LightColor lightColor) {
        if (Status.OFF.equals(this.getStatus())) {
            return "You can't change the status of the Light " + this.getDeviceId() + " while it is off";
        } else {
            this.lightColor = lightColor;
            return "Light " + this.getDeviceId() + " color is set to " + lightColor;
        }
    }

    public BrightnessLevel getBrightnessLevel() {
        return brightnessLevel;
    }

    /**
     * Sets the brightness level of the light
     *
     * @param brightnessLevel the brightness level to set
     */
    public String setBrightnessLevel(BrightnessLevel brightnessLevel) {
        if (Status.OFF.equals(this.getStatus())) {
            return "You can't change the status of the Light " + this.getDeviceId() + " while it is off";
        } else {
            this.brightnessLevel = brightnessLevel;
            return "Light " + this.getDeviceId() + " brightness level is set to " + brightnessLevel;
        }
    }

    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public String startCharging() {
        if (charging) {
            return "Light " + this.getDeviceId() + " is already charging";
        }
        charging = true;
        return "Light " + this.getDeviceId() + " is charging";
    }

    @Override
    public String stopCharging() {
        if (!charging) {
            return "Light " + this.getDeviceId() + " is not charging";
        }
        charging = false;
        return "Light " + this.getDeviceId() + " stopped charging";
    }

    @Override
    public String displayStatus() {
        return "Light " + this.getDeviceId() + " is " + this.getStatus()
                + ", the color is " + lightColor + ", the charging status is " + charging
                + ", and the brightness level is " + brightnessLevel + ".";
    }
}

/**
 * Class representing a heater
 */
class Heater extends SmartDevice {

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

    /**
     * Sets the temperature of the heater
     *
     * @param temperature the temperature to set
     */
    public String setTemperature(int temperature) {
        if (Status.OFF.equals(this.getStatus())) {
            return "You can't change the status of the Heater " + this.getDeviceId() + " while it is off";
        }
        if (temperature < MIN_HEATER_TEMP || temperature > MAX_HEATER_TEMP) {
            return "Heater " + this.getDeviceId() + " temperature should be in the range [15, 30]";
        }
        this.temperature = temperature;
        return "Heater " + this.getDeviceId() + " temperature is set to " + temperature;
    }

    @Override
    public String displayStatus() {
        return "Heater " + this.getDeviceId() + " is " + this.getStatus()
                + " and the temperature is " + temperature + ".";
    }
}

/**
 * Class representing a camera
 */
class Camera extends SmartDevice implements Chargeable {
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

    /**
     * Sets the angle of the camera
     *
     * @param angle the angle to set
     */
    public String setCameraAngle(int angle) {
        if (this.getStatus() == Status.OFF) {
            return "You can't change the status of the Camera " + this.getDeviceId() + " while it is off";
        }
        if (angle < MIN_CAMERA_ANGLE || angle > MAX_CAMERA_ANGLE) {
            return "Camera " + this.getDeviceId() + " angle should be in the range [-60, 60]";
        }
        this.angle = angle;
        return "Camera " + this.getDeviceId() + " angle is set to " + angle;
    }

    /**
     * Starts recording on the camera
     */
    public String startRecording() {
        if (Status.OFF.equals(this.getStatus())) {
            return "You can't change the status of the Camera " + this.getDeviceId() + " while it is off";
        }
        if (recording) {
            return "Camera " + this.getDeviceId() + " is already recording";
        }
        recording = true;
        return "Camera " + this.getDeviceId() + " started recording";
    }

    /**
     * Stops recording on the camera
     */
    public String stopRecording() {
        if (Status.OFF.equals(this.getStatus())) {
            return "You can't change the status of the Camera " + this.getDeviceId() + " while it is off";
        }
        if (!recording) {
            return "Camera " + this.getDeviceId() + " is not recording";
        }
        recording = false;
        return "Camera " + this.getDeviceId() + " stopped recording";
    }

    public boolean isRecording() {
        return recording;
    }


    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public String startCharging() {
        if (charging) {
            return "Camera " + this.getDeviceId() + " is already charging";
        }
        charging = true;
        return "Camera " + this.getDeviceId() + " is charging";
    }

    @Override
    public String stopCharging() {
        if (!charging) {
            return "Camera " + this.getDeviceId() + " is not charging";
        }
        charging = false;
        return "Camera " + this.getDeviceId() + " stopped charging";
    }

    @Override
    public String displayStatus() {
        return "Camera " + this.getDeviceId() + " is " + this.getStatus() + ", the angle is "
                + angle + ", the charging status is " + charging + ", and the recording status is " + recording + ".";
    }
}
