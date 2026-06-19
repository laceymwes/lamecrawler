package net.lamewizard.common;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Factor for common rendering related objects.
 */
public final class RenderingFactory {

    private static final SpriteBatch batch = new SpriteBatch();

    public static final SpriteBatch getBatch() {
        return batch;
    }
}
