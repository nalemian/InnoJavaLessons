package ru.inno.nalemian.lessons.assignment3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SmartHomeManagementSystem {
    private List<SmartDevice> devices;

    public SmartHomeManagementSystem() {
        devices = new ArrayList<>();

        // Initialize devices
        for (int i = 0; i < 4; i++) {
            devices.add(new Light(Status.ON, false, BrightnessLevel.LOW, LightColor.YELLOW));
        }

        devices.add(new Camera(Status.ON, false, false, 45));
        devices.add(new Camera(Status.ON, false, false, 45));

        for (int i = 0; i < 4; i++) {
            devices.add(new Heater(Status.ON, 20));
        }
    }

    public SmartDevice findDevice(String name, int id) {
        for (SmartDevice device : devices) {
            if (device.getClass().getSimpleName().equalsIgnoreCase(name) && device.getDeviceId() == id) {
                return device;
            }
        }
        return null;
    }

    public void handleCommand(String command) {
        String[] tokens = command.split(" ");
        String action = tokens[0];

        try {
            switch (action) {
                case "TurnOn":
                    String turnOnName = tokens[1];
                    int turnOnId = Integer.parseInt(tokens[2]);
                    SmartDevice deviceToTurnOn = findDevice(turnOnName, turnOnId);
                    if (deviceToTurnOn != null) {
                        if (deviceToTurnOn.isOn()) {
                            System.out.println(turnOnName + " " + turnOnId + " is already on");
                        } else {
                            deviceToTurnOn.turnOn();
                            System.out.println(turnOnName + " " + turnOnId + " is on");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;

                // Implement other cases similar to TurnOn based on requirements.

                case "DisplayAllStatus":
                    for (SmartDevice device : devices) {
                        System.out.println(device.displayStatus());
                    }
                    break;

                case "end":
                    System.out.println("End of commands.");
                    break;

                default:
                    System.out.println("Invalid command");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Invalid command");
        }
    }

    public static void main(String[] args) {
        SmartHomeManagementSystem system = new SmartHomeManagementSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter commands:");
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("end")) {
                system.handleCommand("end");
                break;
            } else {
                system.handleCommand(command);
            }
        }
        scanner.close();
    }
}
