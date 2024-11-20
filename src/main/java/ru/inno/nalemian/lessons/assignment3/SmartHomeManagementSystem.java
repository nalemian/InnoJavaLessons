package ru.inno.nalemian.lessons.assignment3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SmartHomeManagementSystem {

    public static void main(String[] args) {

        List<SmartDevice> devices = generateListByTask();

        SmartHomeManagementSystem system = new SmartHomeManagementSystem();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("end")) {
                break;
            } else {
                System.out.println(system.handleCommand(command, devices));
            }
        }
    }

    public static List<SmartDevice> generateListByTask(){
        List<SmartDevice> devices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            devices.add(new Light(Status.ON, false, BrightnessLevel.LOW, LightColor.YELLOW));
        }
        for (int i = 0; i < 2; i++) {
            devices.add(new Camera(Status.ON, false, false, 45));
        }
        for (int i = 0; i < 4; i++) {
            devices.add(new Heater(Status.ON, 20));
        }
        return devices;
    }

    //TODO replace List with Map
    public SmartDevice findDevice(String name, int id, List<SmartDevice> devices) {
        for (SmartDevice device : devices) {
            if (device.getClass().getSimpleName().equalsIgnoreCase(name) && device.getDeviceId() == id) {
                return device;
            }
        }
        return null;
    }

    public String handleCommand(String command, List<SmartDevice> devices) {
        String[] tokens = command.split(" ");
        String action = tokens[0];

        if (action.equals("DisplayAllStatus")) {
            if (tokens.length != 1) {
                System.out.println("Invalid command");
            } else {
                for (SmartDevice device : devices) {
                    System.out.println(device.displayStatus());
                }
            }
        } else {
            if (tokens.length > 1) {
                try {
                    switch (action) {
                        case "TurnOn":
                            if (tokens.length != 3) {
                                System.out.println("Invalid command");
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    System.out.println("The smart device was not found");
                                } else {
                                    String turnOnName = tokens[1];
                                    int turnOnId = Integer.parseInt(tokens[2]);
                                    SmartDevice turnOnDevice = findDevice(turnOnName, turnOnId, devices);
                                    if (turnOnDevice != null) {
                                        if (turnOnDevice.isOn()) {
                                            return turnOnName + " " + turnOnId + " is already on";
                                        } else {
                                            turnOnDevice.turnOn();
                                        }
                                    } else {
                                        return "The smart device was not found";
                                    }
                                }
                            }
                            break;

                        case "TurnOff":
                            if (tokens.length != 3) {
                                System.out.println("Invalid command");
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    System.out.println("The smart device was not found");
                                } else {
                                    String turnOffName = tokens[1];
                                    int turnOffId = Integer.parseInt(tokens[2]);
                                    SmartDevice turnOffDevice = findDevice(turnOffName, turnOffId, devices);
                                    if (turnOffDevice != null) {
                                        if (!turnOffDevice.isOn()) {
                                            return turnOffName + " " + turnOffId + " is already off";
                                        } else {
                                            turnOffDevice.turnOff();
                                        }
                                    } else {
                                        return "The smart device was not found";
                                    }
                                }
                            }
                            break;

                        case "StartCharging":

                            if (tokens.length != 3) {
                                System.out.println("Invalid command");
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    System.out.println("The smart device was not found");
                                } else {
                                    String chargingName = tokens[1];
                                    int chargingId = Integer.parseInt(tokens[2]);
                                    SmartDevice chargingDevice = findDevice(chargingName, chargingId, devices);
                                    if (chargingDevice != null) {
                                        if (chargingDevice instanceof Light || chargingDevice instanceof Camera) {
                                            ((Chargeable) chargingDevice).startCharging();
                                        } else {
                                            return chargingName + " " + chargingId + " is not chargeable";
                                        }
                                    } else {
                                        return "The smart device was not found";
                                    }
                                }
                            }
                            break;

                        case "StopCharging":
                            if (tokens.length != 3) {
                                System.out.println("Invalid command");
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    System.out.println("The smart device was not found");
                                } else {
                                    String stopChargingName = tokens[1];
                                    int stopChargingId = Integer.parseInt(tokens[2]);
                                    SmartDevice stopChargingDevice = findDevice(stopChargingName, stopChargingId, devices);
                                    if (stopChargingDevice != null) {
                                        if (stopChargingDevice instanceof Light || stopChargingDevice instanceof Camera) {
                                            ((Chargeable) stopChargingDevice).stopCharging();
                                        } else {
                                            return stopChargingName + " " + stopChargingId + " is not chargeable";
                                        }
                                    } else {
                                        return "The smart device was not found";
                                    }
                                }
                            }
                            break;

                        case "SetTemperature":
                            if (tokens.length != 4) {
                                System.out.println("Invalid command");
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    System.out.println("The smart device was not found");
                                } else {
                                    String tempName = tokens[1];
                                    int tempId = Integer.parseInt(tokens[2]);
                                    int temperature = Integer.parseInt(tokens[3]);
                                    SmartDevice tempDevice = findDevice(tempName, tempId, devices);
                                    if (tempDevice.getStatus() == Status.OFF) {
                                        System.out.println("You can't change the status of the " + tempDevice.getClass().getSimpleName() + " " + tempDevice.getDeviceId() + " while it is off");
                                    } else {
                                        if (tempDevice instanceof Heater) {
                                            ((Heater) tempDevice).setTemperature(temperature);
                                        } else {
                                            return tempName + " " + tempId + " is not a heater";
                                        }
                                    }
                                }
                            }
                            break;

                        case "SetBrightness":
                            if (tokens.length != 4) {
                                System.out.println("Invalid command");
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    System.out.println("The smart device was not found");
                                } else {
                                    String brightnessName = tokens[1];
                                    int brightnessId = Integer.parseInt(tokens[2]);
                                    String brightnessLevel = tokens[3];
                                    SmartDevice brightnessDevice = findDevice(brightnessName, brightnessId, devices);
                                    if (brightnessDevice.getStatus() == Status.OFF) {
                                        System.out.println("You can't change the status of the " + brightnessDevice.getClass().getSimpleName() + " " + brightnessDevice.getDeviceId() + " while it is off");
                                    } else {
                                        if (brightnessDevice instanceof Light) {
                                            if (brightnessLevel.equals("LOW") || brightnessLevel.equals("MEDIUM") || brightnessLevel.equals("HIGH")) {
                                                ((Light) brightnessDevice).setBrightnessLevel(BrightnessLevel.valueOf(brightnessLevel.toUpperCase()));
                                            } else {
                                                return "The brightness can only be one of \"LOW\", \"MEDIUM\", or \"HIGH\"";
                                            }
                                        } else {
                                            return brightnessName + " " + brightnessId + " is not a light";
                                        }
                                    }
                                }
                            }
                            break;

                        case "SetColor":
                            if (tokens.length != 4) {
                                System.out.println("Invalid command");
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    System.out.println("The smart device was not found");
                                } else {
                                    String colorName = tokens[1];
                                    int colorId = Integer.parseInt(tokens[2]);
                                    String color = tokens[3];
                                    SmartDevice colorDevice = findDevice(colorName, colorId, devices);
                                    if (colorDevice.getStatus() == Status.OFF) {
                                        System.out.println("You can't change the status of the " + colorDevice.getClass().getSimpleName() + " " + colorDevice.getDeviceId() + " while it is off");
                                    } else {

                                        if (colorDevice instanceof Light) {
                                            if (color.equals("YELLOW") || color.equals("WHITE")) {
                                                ((Light) colorDevice).setLightColor(LightColor.valueOf(color.toUpperCase()));
                                            } else {
                                                return "The light color can only be \"YELLOW\" or \"WHITE\"";
                                            }
                                        } else {
                                            return colorName + " " + colorId + " is not a light";
                                        }
                                    }
                                }
                            }
                            break;

                        case "SetAngle":
                            if (tokens.length != 4) {
                                System.out.println("Invalid command");
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    System.out.println("The smart device was not found");
                                } else {
                                    String angleName = tokens[1];
                                    int angleId = Integer.parseInt(tokens[2]);
                                    int angle = Integer.parseInt(tokens[3]);
                                    SmartDevice angleDevice = findDevice(angleName, angleId, devices);
                                    if (angleDevice.getStatus() == Status.OFF) {
                                        System.out.println("You can't change the status of the " + angleDevice.getClass().getSimpleName() + " " + angleDevice.getDeviceId() + " while it is off");
                                    } else {
                                        if (angleDevice instanceof Camera) {
                                            ((Camera) angleDevice).setCameraAngle(angle);
                                        } else {
                                            return angleName + " " + angleId + " is not a camera";
                                        }
                                    }
                                }
                            }
                            break;

                        case "StartRecording":
                            if (tokens.length != 3) {
                                System.out.println("Invalid command");
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    System.out.println("The smart device was not found");
                                } else {
                                    String startRecName = tokens[1];
                                    int startRecId = Integer.parseInt(tokens[2]);
                                    SmartDevice startRecDevice = findDevice(startRecName, startRecId, devices);
                                    if (startRecDevice.getStatus() == Status.OFF) {
                                        System.out.println("You can't change the status of the " + startRecDevice.getClass().getSimpleName() + " " + startRecDevice.getDeviceId() + " while it is off");
                                    } else {
                                        if (startRecDevice instanceof Camera) {
                                            ((Camera) startRecDevice).startRecording();
                                        } else {
                                            return startRecName + " " + startRecId + " is not a camera";
                                        }
                                    }
                                }
                            }
                            break;


                        case "StopRecording":
                            if (tokens.length != 3) {
                                return "Invalid command";
                            } else {
                                if ((!(tokens[1].equals("Camera")) & !(tokens[1].equals("Heater")) & !(tokens[1].equals("Light"))) || (Integer.parseInt(tokens[2]) > 9 || Integer.parseInt(tokens[2]) < 0)) {
                                    return "The smart device was not found";
                                } else {
                                    String stopRecName = tokens[1];
                                    int stopRecId = Integer.parseInt(tokens[2]);
                                    SmartDevice stopRecDevice = findDevice(stopRecName, stopRecId, devices);
                                    if (stopRecDevice.getStatus() == Status.OFF) {
                                        return "You can't change the status of the " + stopRecDevice.getClass().getSimpleName() + " " + stopRecDevice.getDeviceId() + " while it is off";
                                    } else {
                                        if (stopRecDevice instanceof Camera) {
                                            ((Camera) stopRecDevice).stopRecording();
                                        } else {
                                            return stopRecName + " " + stopRecId + " is not a camera";
                                        }
                                    }
                                }
                            }
                            break;

                        default:
                            return "Invalid command";

                    }
                } catch (Exception e) {
                    return "Invalid command";
                }
            }
        }

        return "";
    }
}

enum BrightnessLevel {HIGH, MEDIUM, LOW}

enum LightColor {WHITE, YELLOW}

enum Status {OFF, ON}

interface Chargeable {
    boolean isCharging();

    boolean startCharging();

    boolean stopCharging();
}

interface Controllable {
    boolean turnOff();

    boolean turnOn();

    boolean isOn();
}

abstract class SmartDevice implements Controllable {
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

    public boolean checkStatusAccess() {
        return false;
    }
}

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
        return "Light " + this.getDeviceId() + " is " + this.getStatus() + ", the color is " + lightColor + ", the charging status is " + charging + ", and the brightness level is " + brightnessLevel + ".";
    }
}

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
        return "Heater " + this.getDeviceId() + " is " + this.getStatus() + " and the temperature is " + temperature + ".";
    }
}


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
        return "Camera " + this.getDeviceId() + " is " + this.getStatus() + ", the angle is " + angle + ", the charging status is " + charging + ", and the recording status is " + recording + ".";
    }
}
