package components;

import editor.NImGui;
import imgui.ImGui;
import nova.GameObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Component {
    // Logger
    private static final Logger LOGGER = LogManager.getLogger(Component.class);

    private static int ID_COUNTER = 0;
    private int uid = -1;

    public transient GameObject gameObject = null;

    public void start() {
        LOGGER.debug("Component started: UID {}", uid);
    }

    public void update(float dt) {
        LOGGER.trace("Component updated: UID {}, Delta Time {}", uid, dt);
    }

    public void imgui() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean isTransient = Modifier.isTransient(field.getModifiers());
                if (isTransient) {
                    LOGGER.debug("Skipping transient field: {}", field.getName());
                    continue;
                }

                boolean isPrivate = Modifier.isPrivate(field.getModifiers());
                if (isPrivate) {
                    field.setAccessible(true);
                }

                Class<?> type = field.getType();
                Object value = field.get(this);
                String name = field.getName();

                LOGGER.debug("Processing field: Name = {}, Type = {}, Value = {}", name, type.getSimpleName(), value);

                if (type == int.class) {
                    int val = (int)value;
                    field.set(this, NImGui.dragInt(name, val));
                } else if (type == float.class) {
                    float val = (float)value;
                    field.set(this, NImGui.dragFloat(name, val));
                } else if (type == boolean.class) {
                    boolean val = (boolean)value;
                    if (ImGui.checkbox(name + ": ", val)) {
                        field.set(this, !val);
                        LOGGER.debug("Updated boolean field '{}': Old = {}, New = {}", name, val, !val);
                    }
                } else if (type == Vector2f.class) {
                    Vector2f val = (Vector2f)value;
                    NImGui.drawVec2Control(name, val);
                } else if (type == Vector3f.class) {
                    Vector3f val = (Vector3f)value;
                    float[] imVec = {val.x, val.y, val.z};
                    if (ImGui.dragFloat3(name + ": ", imVec)) {
                        val.set(imVec[0], imVec[1], imVec[2]);
                        LOGGER.debug("Updated Vector3f field '{}': New = ({}, {}, {})", name, imVec[0], imVec[1], imVec[2]);                    }
                } else if (type == Vector4f.class) {
                    Vector4f val = (Vector4f)value;
                    NImGui.colorPicker4(name, val);
                }

                if (isPrivate) {
                    field.setAccessible(false);
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("Error accessing field during ImGui processing: {}", e.getMessage(), e);
        }
    }

    public void generateId() {
        if (this.uid == -1) {
            this.uid = ID_COUNTER++;
            LOGGER.info("Generated new UID: {}", uid);
        }
    }

    public int getUid() {
        return this.uid;
    }

    public static void init(int maxId) {
        ID_COUNTER = maxId;
        LOGGER.info("Initialized ID_COUNTER with value: {}", maxId);
    }
}
