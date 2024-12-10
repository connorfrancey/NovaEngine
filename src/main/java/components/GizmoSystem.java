package components;

import nova.KeyListener;
import nova.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static nova.KeyCodes.NOVA_KEY_E;
import static nova.KeyCodes.NOVA_KEY_R;

public class GizmoSystem extends Component {
    // Logger
    private Logger LOGGER = LogManager.getLogger(GizmoSystem.class);
    private Spritesheet gizmos;
    private int usingGizmo = 0;

    public GizmoSystem(Spritesheet gizmoSprites) {
        gizmos = gizmoSprites;
    }

    @Override
    public void start() {
        gameObject.addComponent(new TranslateGizmo(gizmos.getSprite(1),
                Window.getImguiLayer().getPropertiesWindow()));
        gameObject.addComponent(new ScaleGizmo(gizmos.getSprite(2),
                Window.getImguiLayer().getPropertiesWindow()));
    }

    @Override
    public void update(float dt) {
        if (usingGizmo == 0) {
            gameObject.getComponent(TranslateGizmo.class).setUsing();
            gameObject.getComponent(ScaleGizmo.class).setNotUsing();
        } else if (usingGizmo == 1) {
            gameObject.getComponent(TranslateGizmo.class).setNotUsing();
            gameObject.getComponent(ScaleGizmo.class).setUsing();
        }

        if (KeyListener.isKeyPressed(NOVA_KEY_E)) {
            usingGizmo = 0;
        } else if (KeyListener.isKeyPressed(NOVA_KEY_R)) {
            usingGizmo = 1;
        }
    }
}
