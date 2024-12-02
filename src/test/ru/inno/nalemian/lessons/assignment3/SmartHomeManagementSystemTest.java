package ru.inno.nalemian.lessons.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.inno.nalemian.lessons.assignment3.SmartHomeManagementSystem.generateListByTask;

/**
 * Проверка error-ов
 */

class SmartHomeManagementSystemTest {
    private SmartHomeManagementSystem sha;
    private List<SmartDevice> devices;

    @BeforeEach
    void generateListForTests() {
        devices = generateListByTask();
        sha = new SmartHomeManagementSystem(devices);
    }


    @Test
    void testInvalidCommand() {
        assertEquals("Invalid command", sha.handleCommand("test"));
        assertEquals("Invalid command", sha.handleCommand("TurnOn Camera"));

    }

    @Test
    void smartDeviceWasNotFound() {
        assertEquals("The smart device was not found", sha.handleCommand("TurnOn Camera 1"));
        assertEquals("The smart device was not found", sha.handleCommand("TurnOff Camera 1"));
        assertEquals("The smart device was not found", sha.handleCommand("StartCharging Camera 1"));
        assertEquals("The smart device was not found", sha.handleCommand("StopCharging Camera 1"));
        assertEquals("The smart device was not found", sha.handleCommand("StopCharging Camera 1"));
        assertEquals("The smart device was not found", sha.handleCommand("SetTemperature Heater 1 45"));
        assertEquals("The smart device was not found", sha.handleCommand("SetBrightness Light 5 LOW"));
        assertEquals("The smart device was not found", sha.handleCommand("SetColor Camera 1 WHITE"));
        assertEquals("The smart device was not found", sha.handleCommand("SetAngle Camera 1 45"));
        assertEquals("The smart device was not found", sha.handleCommand("StartRecording Camera 1"));
        assertEquals("The smart device was not found", sha.handleCommand("StopRecording Camera 1"));
    }

    @Test
    void chargingNonChargeable() {
        Heater heater = new Heater(Status.ON, 20);
        heater.setDeviceId(1);
        assertEquals("Heater 8 is not chargeable", sha.handleCommand(
                "StartCharging Heater 8")
        );
    }

    @Test
    void validation() {
        Light light = new Light(Status.ON, false, BrightnessLevel.LOW, LightColor.YELLOW);
        light.setDeviceId(1);
        Heater heater = new Heater(Status.ON, 20);
        heater.setDeviceId(8);
        Camera camera = new Camera(Status.ON, false, false, 45);
        camera.setDeviceId(5);
        validateLight(light);
        validateCamera(camera);
        validateHeater(heater);
    }

    private void validateLight(SmartDevice device) {
        assertNotNull(device);
        assertEquals(Status.ON, device.getStatus());

        if (device instanceof Light light) {
            assertEquals(BrightnessLevel.LOW, light.getBrightnessLevel());
            assertEquals(LightColor.YELLOW, light.getLightColor());
        } else {
            fail("device is not a light");
        }
    }

    private void validateCamera(SmartDevice device) {
        assertNotNull(device);
        assertEquals(Status.ON, device.getStatus());

        if (device instanceof Camera camera) {
            assertFalse(camera.isCharging());
            assertFalse(camera.isRecording());
            assertEquals(45, camera.getAngle());
        } else {
            fail("device is not a camera");
        }
    }

    private void validateHeater(SmartDevice device) {
        assertNotNull(device);
        assertEquals(Status.ON, device.getStatus());

        if (device instanceof Heater heater) {
            assertEquals(20, heater.getTemperature());
        } else {
            fail("device is not a camera");
        }
    }
}