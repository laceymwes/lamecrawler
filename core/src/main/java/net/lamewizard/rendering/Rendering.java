package net.lamewizard.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Utility class for rendering-related functions and objects.
 */
public final class Rendering {

    private static final SpriteBatch batch = new SpriteBatch();

    public static final SpriteBatch getBatch() {
        return batch;
    }
}
