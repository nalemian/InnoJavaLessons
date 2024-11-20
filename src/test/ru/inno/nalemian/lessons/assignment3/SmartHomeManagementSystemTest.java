package ru.inno.nalemian.lessons.assignment3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Проверка error-ов
 */
class SmartHomeManagementSystemTestTestErrorOutput {

    @Test
    void testInvalidCommand() {

        var sha = new SmartHomeManagementSystem();

        assertEquals("Invalid command", sha.handleCommand("test", List.of()));
        assertEquals("Invalid command", sha.handleCommand("TurnOn Camera", List.of()));

    }

    @Test
    void smartDeviceWasNotFound() {
        var sha = new SmartHomeManagementSystem();

        assertEquals("The smart device was not found", sha.handleCommand("TurnOn Camera 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("TurnOff Camera 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("StartCharging Camera 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("StopCharging Camera 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("StopCharging Camera 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("SetTemperature Heater 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("SetBrightness Light 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("SetColor Camera 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("SetAngle Camera 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("StartRecording Camera 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("StopRecording Camera 1", List.of()));
    }

    @Test
    void testFindDevice() {

        var sha = new SmartHomeManagementSystem();
        var items = SmartHomeManagementSystem.generateListByTask();

        assertNotNull(sha.findDevice("Light", 0, items));
        assertNotNull(sha.findDevice("Light", 1, items));
        assertNotNull(sha.findDevice("Light", 2, items));
        assertNotNull(sha.findDevice("Light", 3, items));
        assertNotNull(sha.findDevice("Camera", 4, items));
        assertNotNull(sha.findDevice("Camera", 5, items));
        assertNotNull(sha.findDevice("Heater", 6, items));
        assertNotNull(sha.findDevice("Heater", 7, items));
        assertNotNull(sha.findDevice("Heater", 8, items));
        assertNotNull(sha.findDevice("Heater", 9, items));
    }

    @Test
    void chargingNonChargeable() {
        var sha = new SmartHomeManagementSystem();

        Heater heater = new Heater(Status.ON, 20);
        heater.setDeviceId(1);
        assertEquals("Charging a non-chargeable device", sha.handleCommand(
                "StartCharging Heater 1", List.of(heater))
        );
    }
}