package net.lamewizard.screen;

import com.badlogic.gdx.Screen;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Template for screens that manage objects in a physical world.
 * Screens that require physics processing should extend this class.
 * Class is aware of the required sequence for rendering and physics simulation.
 */
public abstract class PhysicsScreen implements Screen {
    private final Consumer<Float> physicsProcess;

    /**
     * Constructor.
     * @param physicsProcess to be called after each render.
     */
    public PhysicsScreen(Consumer<Float> physicsProcess) {
        Objects.requireNonNull(physicsProcess, "Physics process cannot be null.");
        this.physicsProcess = physicsProcess;
    }

    @Override
    public final void render(float delta) {
        input();
        logic();
        draw();
        physicsProcess.accept(delta);
    }

    /**
     * Process user input.
     */
    protected abstract void input();

    /**
     * Apply game logic.
     */
    protected abstract void logic();

    /**
     * Draw objects to the screen.
     */
    protected abstract void draw();

}
