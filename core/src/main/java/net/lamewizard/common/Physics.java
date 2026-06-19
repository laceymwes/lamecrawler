package net.lamewizard.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Factory for common physical related objects.
 */
public final class Physics {

    public enum FloatingValues {
        TIME_STEP {
            @Override
            public float getValue() {
                return 1f / 60f;
            }
        },
        PHYSICS_DELTA_UPPER_BOUND {
            @Override
            public float getValue() {
                return 0.25f;
            }
        };

        public abstract float getValue();
    }

    public enum IntValues {
        VELOCITY_ITERATIONS {
            @Override
            public int getValue() {
                return 6;
            }
        },
        POSITION_ITERATIONS {
            @Override
            public int getValue() {
                return 2;
            }
        };

        public abstract int getValue();
    }

    private static final World world;

    static {
        world = initWorld();
    }

    public static final World getWorld() {
        return world;
    }

    private static final World initWorld() {
        // zero x-plane (horizontal) gravity, -10 to simulate downward force on the y-plane (vertical) gravity
        return new World(new Vector2(0, -10), true);
    }
}
