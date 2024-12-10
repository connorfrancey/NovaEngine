package util;

import org.joml.Vector2i;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.awt.*;

public class HardwareInfo {

    private static final SystemInfo si = new SystemInfo();
    private static final HardwareAbstractionLayer hal = si.getHardware();
    private static final OperatingSystem os = si.getOperatingSystem();

    public static Vector2i getScreenResolution() {
        // Use Java AWT Toolkit to fetch screen resolution
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        return new Vector2i(screenSize.width, screenSize.height);
    }

    public static int getScreenWidth() {
        return getScreenResolution().x;
    }

    public static int getScreenHeight() {
        return getScreenResolution().y;
    }

    // Get CPU usage as a percentage (using OSHI)
    public static double getCPUUsage() {
        CentralProcessor processor = hal.getProcessor();
        return processor.getSystemCpuLoad(0) * 100; // Returns CPU load in percentage
    }

    // Get total CPU memory in bytes (using OSHI)
    public static long getTotalCPUMemory() {
        return hal.getMemory().getTotal(); // Returns total memory in bytes.
    }

    // Get available CPU memory in bytes (using OSHI)
    public static long getAvailableCPUMemory() {
        return hal.getMemory().getAvailable();  // Returns available memory in bytes
    }

    // TODO: This can't be safe
    public static long getTotalAvailableVRAM() {
        return hal.getGraphicsCards().getFirst().getVRam();
    }
}
