package net.lamewizard.physics;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Apply physical processing to a world.
 */
public interface Engine {

    /**
     * Apply physical processing to a world.
     * Physics will be simulated at a constant rate relative to the frame rate.
     * @param world
     * @param frameDelta time since last frame
     */
    void apply(World world, float frameDelta);
}
