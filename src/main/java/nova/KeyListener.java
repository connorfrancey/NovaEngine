package nova;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    // Logger
    private static final Logger LOGGER = LogManager.getLogger(KeyListener.class);

    private static KeyListener instance;
    private boolean keyPressed[] = new boolean[350];

    private KeyListener() {

    }

    public static KeyListener get() {
        if (KeyListener.instance == null) {
            LOGGER.info("Creating a new KeyListener instance.");
            KeyListener.instance = new KeyListener();
        }

        return KeyListener.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            LOGGER.debug("Key Pressed - Key: {}", key);
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            LOGGER.debug("Key Released - Key: {}", key);
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }
}
