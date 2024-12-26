package components;

import editor.PropertiesWindow;
import nova.MouseListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScaleGizmo extends Gizmo {
    public ScaleGizmo(Sprite scaleSprite, PropertiesWindow propertiesWindow) {
        super(scaleSprite, propertiesWindow);
    }

    @Override
    public void editorUpdate(float dt) {
        if (activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                activeGameObject.transform.scale.x -= MouseListener.getWorldX();
            } else if (yAxisActive) {
                activeGameObject.transform.scale.y -= MouseListener.getWorldY();
            }
        }

        super.editorUpdate(dt);
    }
}
