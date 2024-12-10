package math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class GLColor {
    private float red;
    private float green;
    private float blue;
    private float alpha;

    /**
     * Creates a Color with the specified RGBA values.
     *
     * @param red   Red component (0.0 - 1.0)
     * @param green Green component (0.0 - 1.0)
     * @param blue  Blue component (0.0 - 1.0)
     * @param alpha Alpha component (0.0 - 1.0)
     */
    public GLColor(float red, float green, float blue, float alpha) {
        this.red = clamp(red);
        this.green = clamp(green);
        this.blue = clamp(blue);
        this.alpha = clamp(alpha);
    }

    /**
     * Creates a Color with the specified RGB values and an alpha of 1.0.
     *
     * @param red   Red component (0.0 - 1.0)
     * @param green Green component (0.0 - 1.0)
     * @param blue  Blue component (0.0 - 1.0)
     */
    public GLColor(float red, float green, float blue) {
        this(red, green, blue, 1.0f);
    }

    /**
     * Creates a Color from an integer RGB value (e.g., 0xRRGGBB).
     *
     * @param rgb RGB value as an integer
     */
    public GLColor(int rgb) {
        this(((rgb >> 16) & 0xFF) / 255.0f,
                ((rgb >> 8) & 0xFF) / 255.0f,
                (rgb & 0xFF) / 255.0f,
                1.0f);
    }

    /**
     * Creates a Color from an integer RGBA value (e.g., 0xRRGGBBAA).
     *
     * @param rgba RGBA value as an integer
     */
    public GLColor(int rgba, boolean hasAlpha) {
        this(((rgba >> 24) & 0xFF) / 255.0f,
                ((rgba >> 16) & 0xFF) / 255.0f,
                ((rgba >> 8) & 0xFF) / 255.0f,
                hasAlpha ? (rgba & 0xFF) / 255.0f : 1.0f);
    }

    /**
     * Returns the red component.
     *
     * @return Red component (0.0 - 1.0)
     */
    public float getRed() {
        return red;
    }

    /**
     * Returns the green component.
     *
     * @return Green component (0.0 - 1.0)
     */
    public float getGreen() {
        return green;
    }

    /**
     * Returns the blue component.
     *
     * @return Blue component (0.0 - 1.0)
     */
    public float getBlue() {
        return blue;
    }

    /**
     * Returns the alpha component.
     *
     * @return Alpha component (0.0 - 1.0)
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Sets the red component.
     *
     * @param red Red component (0.0 - 1.0)
     */
    public void setRed(float red) {
        this.red = clamp(red);
    }

    /**
     * Sets the green component.
     *
     * @param green Green component (0.0 - 1.0)
     */
    public void setGreen(float green) {
        this.green = clamp(green);
    }

    /**
     * Sets the blue component.
     *
     * @param blue Blue component (0.0 - 1.0)
     */
    public void setBlue(float blue) {
        this.blue = clamp(blue);
    }

    /**
     * Sets the alpha component.
     *
     * @param alpha Alpha component (0.0 - 1.0)
     */
    public void setAlpha(float alpha) {
        this.alpha = clamp(alpha);
    }

    /**
     * Converts this Color to a FloatBuffer for OpenGL.
     *
     * @return FloatBuffer containing the color in RGBA order
     */
    public FloatBuffer toFloatBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        buffer.put(red).put(green).put(blue).put(alpha);
        buffer.flip();
        return buffer;
    }

    /**
     * Blends this color with another color using linear interpolation.
     *
     * @param other The other color
     * @param t     Interpolation factor (0.0 - 1.0)
     * @return A new Color that is the result of the blend
     */
    public GLColor blend(GLColor other, float t) {
        t = clamp(t);
        return new GLColor(
                lerp(this.red, other.red, t),
                lerp(this.green, other.green, t),
                lerp(this.blue, other.blue, t),
                lerp(this.alpha, other.alpha, t)
        );
    }

    /**
     * Clamps a value between 0.0 and 1.0.
     *
     * @param value The value to clamp
     * @return Clamped value
     */
    private float clamp(float value) {
        return Math.max(0.0f, Math.min(1.0f, value));
    }

    /**
     * Performs linear interpolation between two values.
     *
     * @param a Start value
     * @param b End value
     * @param t Interpolation factor
     * @return Interpolated value
     */
    private float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    @Override
    public String toString() {
        return String.format("Color(R: %.2f, G: %.2f, B: %.2f, A: %.2f)", red, green, blue, alpha);
    }

    // Predefined colors for convenience
    public static final GLColor WHITE = new GLColor(1.0f, 1.0f, 1.0f);
    public static final GLColor BLACK = new GLColor(0.0f, 0.0f, 0.0f);
    public static final GLColor RED = new GLColor(1.0f, 0.0f, 0.0f);
    public static final GLColor GREEN = new GLColor(0.0f, 1.0f, 0.0f);
    public static final GLColor BLUE = new GLColor(0.0f, 0.0f, 1.0f);
    public static final GLColor YELLOW = new GLColor(1.0f, 1.0f, 0.0f);
    public static final GLColor CYAN = new GLColor(0.0f, 1.0f, 1.0f);
    public static final GLColor MAGENTA = new GLColor(1.0f, 0.0f, 1.0f);
    public static final GLColor TRANSPARENT = new GLColor(0.0f, 0.0f, 0.0f, 0.0f);
}