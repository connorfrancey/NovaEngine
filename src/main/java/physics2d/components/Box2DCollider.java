package physics2d.components;

import components.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;

public class Box2DCollider extends Component {
    // Logger
    private static final Logger LOGGER = LogManager.getLogger(Box2DCollider.class);

    private Vector2f halfSize = new Vector2f(1);

    public Vector2f getHalfSize() {
        return halfSize;
    }

    public void setHalfSize(Vector2f halfSize) {
        this.halfSize = halfSize;
    }
}
