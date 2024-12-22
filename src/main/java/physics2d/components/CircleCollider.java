package physics2d.components;

import components.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CircleCollider extends Collider {
    private float radius = 1f;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}