package net.lamewizard.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Utility class for physics-related objects.
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

    public static final World world() {
        return world;
    }

    private static final World initWorld() {
        // zero x-plane (horizontal) gravity, -10 to simulate downward force on the y-plane (vertical) gravity
        return new World(new Vector2(0, -10), true);
    }

    public static final Engine engine() {
        return new LameEngine();
    }

    private static final class LameEngine implements Engine {
        private float physicsAccumulator = 0f;
        /*
         This was taken from a few examples in the libgdx doco and Glen Fielder.
         https://gafferongames.com/post/fix_your_timestep/
         https://libgdx.com/wiki/extensions/physics/box2d#stepping-the-simulation
        */
        @Override
        public void apply(World world, float frameDelta) {
            // fixed time step
            // max frame time to avoid spiral of death (on slow devices)
            final float frameTime = Math.min(frameDelta, FloatingValues.PHYSICS_DELTA_UPPER_BOUND.getValue())   ;
            physicsAccumulator += frameTime;
            while (physicsAccumulator >= FloatingValues.TIME_STEP.getValue()) {
                world.step(
                    FloatingValues.TIME_STEP.getValue(),
                    IntValues.VELOCITY_ITERATIONS.getValue(),
                    IntValues.POSITION_ITERATIONS.getValue()
                );
                physicsAccumulator -= FloatingValues.TIME_STEP.getValue();
            }
        }
    }
}
