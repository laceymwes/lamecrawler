package net.lamewizard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.World;
import net.lamewizard.common.Physics;
import net.lamewizard.screen.MainMenuScreen;
import net.lamewizard.screen.MainPlayScreen;

import static net.lamewizard.common.Physics.FloatingValues;
import static net.lamewizard.common.Physics.IntValues;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public final class Controller extends Game {

    private World world;

    private float physicsAccumulator = 0f;

    private final String debug = System.getProperty("debug");

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this::startGame));
    }

    private void startGame() {
        initPhysics();
        this.setScreen(new MainPlayScreen(this::processPhysics));
    }

    private void initPhysics() {
        this.world = Physics.getWorld();
    }

    private void processPhysics(final float renderDelta) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        final float frameTime = Math.min(renderDelta, FloatingValues.PHYSICS_DELTA_UPPER_BOUND.getValue())   ;
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
