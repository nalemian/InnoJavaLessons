package ru.inno.nalemian.lessons.assignment3;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Проверка error-ов
 */
@Disabled
class SmartHomeManagementSystemTest {

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
        assertEquals("The smart device was not found", sha.handleCommand("SetTemperature Heater 1 45", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("SetBrightness Light 1 LOW", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("SetColor Camera 1 WHITE", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("SetAngle Camera 1 45", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("StartRecording Camera 1", List.of()));
        assertEquals("The smart device was not found", sha.handleCommand("StopRecording Camera 1", List.of()));
    }

    @Test
    void testFindDeviceAndCheckDefault() {

        var sha = new SmartHomeManagementSystem();
        var items = SmartHomeManagementSystem.generateListByTask();

        validateLight(sha.findDevice("Light", 0, items));
        validateLight(sha.findDevice("Light", 1, items));
        validateLight(sha.findDevice("Light", 2, items));
        validateLight(sha.findDevice("Light", 3, items));
        validateCamera(sha.findDevice("Camera", 4, items));
        validateCamera(sha.findDevice("Camera", 5, items));
        validateHeater(sha.findDevice("Heater", 6, items));
        validateHeater(sha.findDevice("Heater", 7, items));
        validateHeater(sha.findDevice("Heater", 8, items));
        validateHeater(sha.findDevice("Heater", 9, items));
    }

    @Test
    void chargingNonChargeable() {
        var sha = new SmartHomeManagementSystem();

        Heater heater = new Heater(Status.ON, 20);
        heater.setDeviceId(1);
        assertEquals("Heater 1 is not chargeable", sha.handleCommand(
                "StartCharging Heater 1", List.of(heater))
        );
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