package net.lamewizard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.World;
import net.lamewizard.physics.Engine;
import net.lamewizard.physics.Physics;
import net.lamewizard.screen.MainMenuScreen;
import net.lamewizard.screen.MainPlayScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public final class Controller extends Game {

    private World world;
    private Engine engine;

    private final String debug = System.getProperty("debug");

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this::startGame));
    }

    private void startGame() {
        world = Physics.world();
        engine = Physics.engine();
        this.setScreen(new MainPlayScreen((frameDelta) -> engine.apply(world, frameDelta)));
    }

}
