package ru.inno.nalemian.lessons.assignment3;


import java.util.*;

import static java.lang.Integer.parseInt;

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
    private SmartDevice findDeviceOrThrow(String name, int id) {
        SmartDevice device = devices.get(name + id);
        if (device == null) {
            throw new DeviceNotFoundException("The smart device was not found");
        }
        return device;
    }

    /**
     * Checks a command to ensure it matches the format and checks if the device exists
     *
     * @param commandElements         the command elements to validate
     * @param numberOfCommandElements the required number of command elements
     */
    private void checkFormatAndExistence(String[] commandElements, int numberOfCommandElements) {
        if (commandElements.length != numberOfCommandElements) {
            throw new CommandRuntimeException("Invalid command");
        } else {
            if ((!commandElements[1].equals("Camera") && !commandElements[1].equals("Heater")
                    && !commandElements[1].equals("Light"))) {
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
        SmartDevice turnOnOffDevice = findDeviceOrThrow(turnOnOffName, turnOnOffId);
        if (command == 1) {
            return turnOnOffDevice.executeTurnOnCommand();
        } else {
            return turnOnOffDevice.executeTurnOffCommand();
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
        SmartDevice device = findDeviceOrThrow(chargingName, chargingId);
        if (device instanceof Chargeable chargeable) {
            if (command == 1) {
                return chargeable.executeStartChargingCommand();
            } else {
                return chargeable.executeStopChargingCommand();
            }
        } else {
            throw new DeviceNotChargeableException(chargingName + " " + chargingId + " is not chargeable");
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
        SmartDevice startStopRecDevice = findDeviceOrThrow(startStopRecName, startStopRecId);
        String res = status(startStopRecDevice);
        if ("true".equals(res)) {
            if (startStopRecDevice instanceof Camera camera) {
                if (command == 1) {
                    return camera.executeStartRecordingCommand();
                } else {
                    return camera.executeStopRecordingCommand();
                }
            } else {
                return startStopRecName + " " + startStopRecId + " is not a camera";
            }
        } else {
            return res;
        }
    }

    private <T extends SmartDevice> String executeDeviceCommand(String[] commandElements, int length, Class<T> deviceType, DeviceCommand<T> command) {
        checkFormatAndExistence(commandElements, length);
        String deviceName = commandElements[1];
        int deviceId = parseInt(commandElements[2]);
        SmartDevice device = findDeviceOrThrow(deviceName, deviceId);
        if (!deviceType.isInstance(device)) {
            return deviceName + " " + deviceId + " is not a " + deviceType.getSimpleName().toLowerCase();
        }
        String statusCheck = status(device);
        if (!statusCheck.equals("true")) {
            return statusCheck;
        }
        return command.execute(deviceType.cast(device), commandElements);
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
                for (SmartDevice device : devices.values()) {
                    return device.displayStatus();
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
                            return executeDeviceCommand(commandElements, secondLenOfCommandLine, Heater.class, (heater, elements) -> {
                                int temperature = parseInt(elements[3]);
                                return heater.executeSetTemperatureCommand(temperature);
                            });

                        case "SetBrightness":
                            return executeDeviceCommand(commandElements, secondLenOfCommandLine, Light.class, (light, elements) -> {
                                String brightnessLevel = elements[3].toUpperCase();
                                if (!brightnessLevel.equals("LOW") && !brightnessLevel.equals("MEDIUM") && !brightnessLevel.equals("HIGH")) {
                                    return "The brightness can only be one of \"LOW\", \"MEDIUM\", or \"HIGH\"";
                                }
                                return light.executeSetBrightnessLevelCommand(BrightnessLevel.valueOf(brightnessLevel));
                            });


                        case "SetColor":
                            return executeDeviceCommand(commandElements, secondLenOfCommandLine, Light.class, (light, elements) -> {
                                String color = elements[3].toUpperCase();
                                if (!color.equals("YELLOW") && !color.equals("WHITE")) {
                                    return "The light color can only be \"YELLOW\" or \"WHITE\"";
                                }
                                return light.executeSetLightColorCommand(LightColor.valueOf(color));
                            });

                        case "SetAngle":
                            return executeDeviceCommand(commandElements, secondLenOfCommandLine, Camera.class, (camera, elements) -> {
                                int angle = parseInt(elements[3]);
                                return camera.executeSetCameraAngleCommand(angle);
                            });

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
                        CommandRuntimeException | DeviceNotFoundException | DeviceNotChargeableException e) {
                    return e.getMessage();
                } catch (
                        Exception e) {
                    return "Invalid command";
                }
            } else {
                return "Invalid command";
            }
        }
        throw new RuntimeException("Unexpected behavior");
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

@FunctionalInterface
interface DeviceCommand<T> {
    String execute(T device, String[] commandElements);
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
    boolean startCharging();

    /**
     * Stops charging the device
     */
    boolean stopCharging();

    String executeStartChargingCommand();

    String executeStopChargingCommand();
}

/**
 * Interface representing devices that can be controlled
 */
interface Controllable {
    /**
     * Turns off the device
     */
    boolean turnOff();

    /**
     * Turns on the device
     */
    boolean turnOn();

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
    public boolean turnOff() {
        if (Status.OFF.equals(status)) {
            throw new RuntimeException(getClass().getSimpleName() + " " + deviceId + " is already off");
        }
        return true;
    }

    public String executeTurnOffCommand() {
        try {
            if (turnOff()) {
                status = Status.OFF;
                return getClass().getSimpleName() + " " + deviceId + " is off";
            } else {
                return "Unknown error";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public boolean turnOn() {
        if (Status.ON.equals(status)) {
            throw new RuntimeException(getClass().getSimpleName() + " " + deviceId + " is already on");
        }
        return true;
    }

    public String executeTurnOnCommand() {
        try {
            if (turnOn()) {
                status = Status.ON;
                return getClass().getSimpleName() + " " + deviceId + " is on";
            } else {
                return "Unknown error";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
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
     */
    public void setLightColor() {
        if (Status.OFF.equals(this.getStatus())) {
            throw new RuntimeException("You can't change the status of the Light "
                    + this.getDeviceId() + " while it is off");
        }
    }

    public String executeSetLightColorCommand(LightColor lightColor) {
        try {
            setLightColor();
            this.lightColor = lightColor;
            return "Light " + this.getDeviceId() + " color is set to " + lightColor;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public BrightnessLevel getBrightnessLevel() {
        return brightnessLevel;
    }

    /**
     * Sets the brightness level of the light
     */
    public void setBrightnessLevel() {
        if (Status.OFF.equals(this.getStatus())) {
            throw new RuntimeException("You can't change the status of the Light "
                    + this.getDeviceId() + " while it is off");
        }
    }

    public String executeSetBrightnessLevelCommand(BrightnessLevel brightnessLevel) {
        try {
            setBrightnessLevel();
            this.brightnessLevel = brightnessLevel;
            return "Light " + this.getDeviceId() + " brightness level is set to " + brightnessLevel;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public boolean startCharging() {
        if (charging) {
            throw new RuntimeException("Light " + this.getDeviceId() + " is already charging");
        }
        return true;
    }

    @Override
    public String executeStartChargingCommand() {
        try {
            if (startCharging()) {
                charging = true;
                return "Light " + this.getDeviceId() + " is charging";
            } else {
                return "Unknown error";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public boolean stopCharging() {
        if (!charging) {
            throw new RuntimeException("Light " + this.getDeviceId() + " is not charging");
        }
        return true;
    }

    @Override
    public String executeStopChargingCommand() {
        try {
            if (stopCharging()) {
                charging = false;
                return "Light " + this.getDeviceId() + " stopped charging";
            } else {
                return "Unknown error";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
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
    public boolean setTemperature(int temperature) {
        if (Status.OFF.equals(this.getStatus())) {
            throw new RuntimeException("You can't change the status of the Heater "
                    + this.getDeviceId() + " while it is off");
        }
        if (temperature < MIN_HEATER_TEMP || temperature > MAX_HEATER_TEMP) {
            throw new RuntimeException("Heater " + this.getDeviceId() + " temperature should be in the range [15, 30]");
        }
        return true;
    }

    public String executeSetTemperatureCommand(int temperature) {
        try {
            if (setTemperature(temperature)) {
                this.temperature = temperature;
                return "Heater " + this.getDeviceId() + " temperature is set to " + temperature;
            } else {
                return "Unknown error";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
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
    public boolean setCameraAngle(int angle) {
        if (this.getStatus() == Status.OFF) {
            throw new RuntimeException("You can't change the status of the Camera "
                    + this.getDeviceId() + " while it is off");
        }
        if (angle < MIN_CAMERA_ANGLE || angle > MAX_CAMERA_ANGLE) {
            throw new RuntimeException("Camera " + this.getDeviceId() + " angle should be in the range [-60, 60]");
        }
        return true;
    }

    public String executeSetCameraAngleCommand(int angle) {
        try {
            if (setCameraAngle(angle)) {
                this.angle = angle;
                return "Camera " + this.getDeviceId() + " angle is set to " + angle;
            } else {
                return "Unknown error";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    /**
     * Starts recording on the camera
     */
    public boolean startRecording() {
        if (Status.OFF.equals(this.getStatus())) {
            throw new RuntimeException("You can't change the status of the Camera "
                    + this.getDeviceId() + " while it is off");
        }
        if (recording) {
            throw new RuntimeException("Camera " + this.getDeviceId() + " is already recording");
        }
        return true;
    }

    public String executeStartRecordingCommand() {
        try {
            if (startRecording()) {
                recording = true;
                return "Camera " + this.getDeviceId() + " started recording";
            } else {
                return "Unknown error";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    /**
     * Stops recording on the camera
     */
    public boolean stopRecording() {
        if (Status.OFF.equals(this.getStatus())) {
            throw new RuntimeException("You can't change the status of the Camera "
                    + this.getDeviceId() + " while it is off");
        }
        if (!recording) {
            throw new RuntimeException("Camera " + this.getDeviceId() + " is not recording");
        }
        return true;
    }

    public String executeStopRecordingCommand() {
        try {
            if (stopRecording()) {
                recording = false;
                return "Camera " + this.getDeviceId() + " stopped recording";
            } else {
                return "Unknown error";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public boolean isRecording() {
        return recording;
    }


    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public boolean startCharging() {
        if (charging) {
            throw new RuntimeException("Camera " + this.getDeviceId() + " is already charging");
        }
        return true;
    }

    @Override
    public String executeStartChargingCommand() {
        try {
            if (startCharging()) {
                charging = true;
                return "Camera " + this.getDeviceId() + " is charging";
            } else {
                return "Unknown error";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public boolean stopCharging() {
        if (!charging) {
            throw new RuntimeException("Camera " + this.getDeviceId() + " is not charging");
        }
        return true;
    }

    @Override
    public String executeStopChargingCommand() {
        try {
            if (stopCharging()) {
                charging = false;
                return "Camera " + this.getDeviceId() + " stopped charging";
            } else {
                return "Unknown error";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String displayStatus() {
        return "Camera " + this.getDeviceId() + " is " + this.getStatus() + ", the angle is "
                + angle + ", the charging status is " + charging + ", and the recording status is " + recording + ".";
    }
}
