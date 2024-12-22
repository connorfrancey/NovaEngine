package nova;

import observers.EventSystem;
import observers.Observer;
import observers.events.Event;
import observers.events.EventType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import renderer.*;
import scenes.LevelEditorSceneInitializer;
import scenes.Scene;
import scenes.SceneInitializer;
import util.AssetPool;
import util.HardwareInfo;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements Observer {
    // Logger
    private static final Logger LOGGER = LogManager.getLogger(Window.class);

    private int width, height;
    private String title;
    private long glfwWindow;
    private ImGuiLayer imguiLayer;
    private Framebuffer framebuffer;
    private PickingTexture pickingTexture;
    private boolean runtimePlaying = false;

    private static Window window = null;

    private static Scene currentScene;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "NovaEngine";
        EventSystem.addObserver(this);
    }

    public static void changeScene(SceneInitializer sceneInitializer) {
        if (currentScene != null) {
            currentScene.destroy();
        }

        getImguiLayer().getPropertiesWindow().setActiveGameObject(null);
        currentScene = new Scene(sceneInitializer);
        currentScene.load();
        currentScene.init();
        currentScene.start();
    }

    public static Window get() {
        if (Window.window == null) {
            LOGGER.info("Creating a new Window instance.");
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Scene getScene() {
        return get().currentScene;
    }

    public void run() {
        LOGGER.info("Starting the engine with LWJGL Version: {}", Version.getVersion());

        init();
        loop();

        // Free the memory
        LOGGER.info("Free the GLFW callbacks and destroy the GLFW window");
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and then free the error callback
        LOGGER.info("Terminating GLFW and freeing the error callback");
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {

        // Setup an error callback
        LOGGER.debug("Setting up the GLFW error callback");
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        LOGGER.info("Initializing GLFW");
        if (!glfwInit()) {
            String errorMessage = "Unable to initialize GLFW.";
            LOGGER.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }

        // Configure GLFW
        LOGGER.debug("Configuring GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        LOGGER.info("Creating the GLFW window");
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            String errorMessage = "Failed to create the GLFW window.";
            LOGGER.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }

        // Setup callbacks
        LOGGER.debug("Setting up GLFW callbacks");
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            LOGGER.info("Window resized to {}x{}", newWidth, newHeight);
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        // Make the OpenGL context current
        LOGGER.debug("Making the OpenGL context current");
        glfwMakeContextCurrent(glfwWindow);

        // Enable VSync
        LOGGER.debug("Setting VSync");
        glfwSwapInterval(1);

        // Make the window visible
        LOGGER.debug("Making the window visible");
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        LOGGER.info("Creating LWJGL OpenGL Capabilities");
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        LOGGER.info("Initializing Framebuffer");
        this.framebuffer = new Framebuffer(HardwareInfo.getScreenWidth(), HardwareInfo.getScreenHeight());
        this.pickingTexture = new PickingTexture(HardwareInfo.getScreenWidth(), HardwareInfo.getScreenHeight());
        glViewport(0, 0, HardwareInfo.getScreenWidth(), HardwareInfo.getScreenHeight());

        LOGGER.info("Initializing ImGuiLayer");
        this.imguiLayer = new ImGuiLayer(glfwWindow, pickingTexture);
        this.imguiLayer.initImGui();

        LOGGER.info("Window initialized successfully.");
        Window.changeScene(new LevelEditorSceneInitializer());
    }

    private void loop() {
        LOGGER.info("Entering the main engine loop.");

        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        Shader defaultShader = AssetPool.getShader("assets/shaders/default.glsl");
        Shader pickingShader = AssetPool.getShader("assets/shaders/pickingShader.glsl");

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll for events
            glfwPollEvents();

            // Render pass 1. Render to picking texture
            glDisable(GL_BLEND);
            pickingTexture.enableWriting();

            glViewport(0, 0, HardwareInfo.getScreenWidth(), HardwareInfo.getScreenHeight());
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Renderer.bindShader(pickingShader);
            currentScene.render();

            pickingTexture.disableWriting();
            glEnable(GL_BLEND);

            // Render pass 2. Render actual game
            DebugDraw.beginFrame();

            // Bind framebuffer
            this.framebuffer.bind();

            // Set the background color
            glClearColor(1, 1, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                DebugDraw.draw();
                Renderer.bindShader(defaultShader);
                if (runtimePlaying) {
                    currentScene.update(dt);
                } else {
                    currentScene.editorUpdate(dt);
                }

                currentScene.render();
            }
            this.framebuffer.unbind();

            this.imguiLayer.update(dt, currentScene);
            glfwSwapBuffers(glfwWindow);
            MouseListener.endFrame();

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }

        LOGGER.info("Exiting the main engine loop.");
    }

    public static int getWidth() {
        return get().width;
    }

    public static int getHeight() {
        return get().height;
    }

    public static void setWidth(int newWidth) {
        LOGGER.debug("Setting new window width to {}", newWidth);
        get().width = newWidth;
    }

    public static void setHeight(int newHeight) {
        LOGGER.debug("Setting new window height to {}", newHeight);
        get().height = newHeight;
    }

    public static Framebuffer getFramebuffer() {
        return get().framebuffer;
    }
    public static float getTargetAspectRatio() {
        return 16.0f / 9.0f;
    }

    public static ImGuiLayer getImguiLayer() {
        return get().imguiLayer;
    }

    @Override
    public void onNotify(GameObject object, Event event) {
        switch (event.type) {
            case GameEngineStartPlay:
                LOGGER.info("Starting runtime");
                this.runtimePlaying = true;
                currentScene.save();
                Window.changeScene(new LevelEditorSceneInitializer());
                break;
            case GameEngineStopPlay:
                LOGGER.info("Stopping runtime");
                this.runtimePlaying = false;
                Window.changeScene(new LevelEditorSceneInitializer());
                break;
            case LoadLevel:
                Window.changeScene(new LevelEditorSceneInitializer());
                break;
            case SaveLevel:
                currentScene.save();
                break;
        }
    }
}
