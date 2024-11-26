package ru.inno.nalemian.lessons.assignment3;


import java.util.*;

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
                system.handleCommand(command);
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
        return devices.get(name + id);
    }

    /**
     * Checks a command to ensure it matches the format and checks if the device exists
     *
     * @param commandElements         the command elements to validate
     * @param numberOfCommandElements the required number of command elements
     * @return true if the command is valid, false otherwise
     */
    private void checkFormatAndExistence(String[] commandElements, int numberOfCommandElements) {
        //TODO убрать ограничение по min и max device
        final int maxDeviceId = 9;
        final int minDeviceId = 0;
        if (commandElements.length != numberOfCommandElements) {
            throw new CommandRuntimeException("Invalid command");
        } else {
            if ((!commandElements[1].equals("Camera") && !commandElements[1].equals("Heater")
                    && !commandElements[1].equals("Light")) || (Integer.parseInt(commandElements[2]) > maxDeviceId
                    || Integer.parseInt(commandElements[2]) < minDeviceId)) {
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
    public void turnOnOff(String[] commandElements, int command) {
        String turnOnOffName = commandElements[1];
        int turnOnOffId = Integer.parseInt(commandElements[2]);

        SmartDevice turnOnOffDevice = findDevice(turnOnOffName, turnOnOffId);
        if (turnOnOffDevice != null) {
            if (command == 1) {
                if (turnOnOffDevice.isOn()) {
                    System.out.println(turnOnOffName + " " + turnOnOffId + " is already on");
                } else {
                    turnOnOffDevice.turnOn();
                }
            } else {
                if (!turnOnOffDevice.isOn()) {
                    System.out.println(turnOnOffName + " " + turnOnOffId + " is already off");
                } else {
                    turnOnOffDevice.turnOff();
                }
            }
        } else {
            System.out.println("The smart device was not found");
        }
    }

    /**
     * Starts or stops charging a smart device based on the provided command
     *
     * @param commandElements the command elements to validate
     * @param command         the command (1 - start charging, 2 - stop charging)
     */
    public void startStopCharging(String[] commandElements, int command) {
        String chargingName = commandElements[1];
        int chargingId = Integer.parseInt(commandElements[2]);
        SmartDevice chargingDevice = findDevice(chargingName, chargingId);
        if (chargingDevice != null) {
            if (chargingDevice instanceof Light || chargingDevice instanceof Camera) {
                if (command == 1) {
                    ((Chargeable) chargingDevice).startCharging();
                } else {
                    ((Chargeable) chargingDevice).stopCharging();
                }
            } else {
                System.out.println(chargingName + " " + chargingId + " is not chargeable");
            }
        } else {
            System.out.println("The smart device was not found");
        }
    }

    /**
     * Checks the status of a smart device to determine if it is possible to perform an operation on it
     *
     * @param brightnessDevice the smart device to check
     * @return true if the operation can proceed, false otherwise
     */
    public boolean status(SmartDevice brightnessDevice) {
        if (brightnessDevice != null) {
            if (brightnessDevice.getStatus() == Status.OFF) {
                System.out.println("You can't change the status of the " + brightnessDevice.getClass().getSimpleName()
                        + " " + brightnessDevice.getDeviceId() + " while it is off");
                return false;
            }
            return true;
        } else {
            System.out.println("The smart device was not found");
            return false;
        }
    }

    /**
     * Starts or stops recording for a camera device based on the provided command
     *
     * @param commandElements the command elements to validate
     * @param command         the command (1 - start recording, 2 - stop recording)
     */
    private void startStopRecording(String[] commandElements, int command) {
        String startStopRecName = commandElements[1];
        int startStopRecId = Integer.parseInt(commandElements[2]);
        SmartDevice startStopRecDevice = findDevice(startStopRecName, startStopRecId);
        if (status(startStopRecDevice)) {
            if (startStopRecDevice instanceof Camera) {
                if (command == 1) {
                    ((Camera) startStopRecDevice).startRecording();
                } else {
                    ((Camera) startStopRecDevice).stopRecording();
                }
            } else {
                System.out.println(startStopRecName + " " + startStopRecId + " is not a camera");
            }
        }
    }

    /**
     * Handles the issued command and performs the corresponding action
     *
     * @param commandLine a string containing the command parameters
     */
    public void handleCommand(String commandLine) {
        String[] commandElements = commandLine.split(" ");
        String action = commandElements[0];
        if (action.equals("DisplayAllStatus")) {
            if (commandElements.length != 1) {
                System.out.println("Invalid command");
            } else {
                for (SmartDevice value : devices.values()) {
                    System.out.println(value.displayStatus());
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
                            turnOnOff(commandElements, 1);
                            break;

                        case "TurnOff":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            turnOnOff(commandElements, 2);
                            break;

                        case "StartCharging":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            startStopCharging(commandElements, 1);
                            break;

                        case "StopCharging":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            startStopCharging(commandElements, 2);
                            break;

                        case "SetTemperature":
                            final int indexOfTemperature = 3;
                            checkFormatAndExistence(commandElements, secondLenOfCommandLine);
                            String tempName = commandElements[1];
                            int tempId = Integer.parseInt(commandElements[2]);
                            int temperature = Integer.parseInt(commandElements[indexOfTemperature]);
                            SmartDevice tempDevice = findDevice(tempName, tempId);
                            if (status(tempDevice)) {
                                if (tempDevice instanceof Heater) {
                                    ((Heater) tempDevice).setTemperature(temperature);
                                } else {
                                    System.out.println(tempName + " " + tempId + " is not a heater");
                                }
                            }
                            break;

                        case "SetBrightness":
                            final int indexOfBrightness = 3;
                            checkFormatAndExistence(commandElements, secondLenOfCommandLine);
                            String brightnessName = commandElements[1];
                            int brightnessId = Integer.parseInt(commandElements[2]);
                            String brightnessLevel = commandElements[indexOfBrightness];
                            SmartDevice brightnessDevice = findDevice(brightnessName, brightnessId);
                            if (status(brightnessDevice)) {
                                if (brightnessDevice instanceof Light) {
                                    if (brightnessLevel.equals("LOW") || brightnessLevel.equals("MEDIUM")
                                            || brightnessLevel.equals("HIGH")) {
                                        ((Light) brightnessDevice).setBrightnessLevel(
                                                BrightnessLevel.valueOf(brightnessLevel.toUpperCase()));
                                    } else {
                                        System.out.println("The brightness can only be one"
                                                + " of \"LOW\", \"MEDIUM\", or \"HIGH\"");
                                    }
                                } else {
                                    System.out.println(brightnessName + " " + brightnessId + " is not a light");
                                }
                            }
                            break;

                        case "SetColor":
                            final int indexOfColor = 3;
                            checkFormatAndExistence(commandElements, secondLenOfCommandLine);
                            String colorName = commandElements[1];
                            int colorId = Integer.parseInt(commandElements[2]);
                            String color = commandElements[indexOfColor];
                            SmartDevice colorDevice = findDevice(colorName, colorId);
                            if (status(colorDevice)) {
                                if (colorDevice instanceof Light) {
                                    if (color.equals("YELLOW") || color.equals("WHITE")) {
                                        ((Light) colorDevice).setLightColor(
                                                LightColor.valueOf(color.toUpperCase()));
                                    } else {
                                        System.out.println("The light color can"
                                                + " only be \"YELLOW\" or \"WHITE\"");
                                    }
                                } else {
                                    System.out.println(colorName + " " + colorId + " is not a light");
                                }
                            }
                            break;

                        case "SetAngle":
                            final int indexOfAngle = 3;
                            checkFormatAndExistence(commandElements, secondLenOfCommandLine);
                            String angleName = commandElements[1];
                            int angleId = Integer.parseInt(commandElements[2]);
                            int angle = Integer.parseInt(commandElements[indexOfAngle]);
                            SmartDevice angleDevice = findDevice(angleName, angleId);
                            if (status(angleDevice)) {
                                if (angleDevice instanceof Camera) {
                                    ((Camera) angleDevice).setCameraAngle(angle);
                                } else {
                                    System.out.println(angleName + " " + angleId + " is not a camera");
                                }

                            }
                            break;

                        case "StartRecording":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            startStopRecording(commandElements, 1);
                            break;


                        case "StopRecording":
                            checkFormatAndExistence(commandElements, firstLenOfCommandLine);
                            startStopRecording(commandElements, 2);
                            break;

                        default:
                            System.out.println("Invalid command");
                            break;
                    }
                } catch (
                        CommandRuntimeException e) {
                    System.out.println(e.getMessage());
                } catch (
                        Exception e) {
                    System.out.println("Invalid command");
                }
            } else {
                System.out.println("Invalid command");
            }
        }
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
     *
     * @return true if charging started successfully, false otherwise
     */
    boolean startCharging();

    /**
     * Stops charging the device
     *
     * @return true if charging stopped successfully, false otherwise
     */
    boolean stopCharging();
}

/**
 * Interface representing devices that can be controlled
 */
interface Controllable {
    /**
     * Turns off the device
     *
     * @return true if the device was successfully turned off, false otherwise
     */
    boolean turnOff();

    /**
     * Turns on the device
     *
     * @return true if the device was successfully turned on, false otherwise
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
            System.out.println(getClass().getSimpleName() + " " + deviceId + " is already off");
            return false;
        }
        status = Status.OFF;
        System.out.println(getClass().getSimpleName() + " " + deviceId + " is off");
        return true;
    }

    @Override
    public boolean turnOn() {
        if (Status.ON.equals(status)) {
            System.out.println(getClass().getSimpleName() + " " + deviceId + " is already on");
            return false;
        }
        status = Status.ON;
        System.out.println(getClass().getSimpleName() + " " + deviceId + " is on");
        return true;
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
    public void setLightColor(LightColor lightColor) {
        if (Status.OFF.equals(this.getStatus())) {
            System.out.println("You can't change the status of the Light " + this.getDeviceId() + " while it is off");
        } else {
            this.lightColor = lightColor;
            System.out.println("Light " + this.getDeviceId() + " color is set to " + lightColor);
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
    public void setBrightnessLevel(BrightnessLevel brightnessLevel) {
        if (Status.OFF.equals(this.getStatus())) {
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
     * @return true if the temperature was set successfully, false otherwise
     */
    public boolean setTemperature(int temperature) {
        if (Status.OFF.equals(this.getStatus())) {
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
     * @return true if the angle was set successfully, false otherwise
     */
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

    /**
     * Starts recording on the camera
     *
     * @return true if recording started successfully, false otherwise
     */
    public boolean startRecording() {
        if (Status.OFF.equals(this.getStatus())) {
            System.out.println("You can't change the status of the Camera " + this.getDeviceId() + " while it is off");
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

    /**
     * Stops recording on the camera
     *
     * @return true if recording stopped successfully, false otherwise
     */
    public boolean stopRecording() {
        if (Status.OFF.equals(this.getStatus())) {
            System.out.println("You can't change the status of the Camera " + this.getDeviceId() + " while it is off");
            return false;
        }
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
        System.out.println("Camera " + this.getDeviceId() + " is charging");
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
        return "Camera " + this.getDeviceId() + " is " + this.getStatus() + ", the angle is "
                + angle + ", the charging status is " + charging + ", and the recording status is " + recording + ".";
    }
}
