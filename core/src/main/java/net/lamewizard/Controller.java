package net.lamewizard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.lamewizard.physics.Engine;
import net.lamewizard.physics.Physics;
import net.lamewizard.screen.MainMenuScreen;
import net.lamewizard.screen.MainPlayScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public final class Controller extends Game {

    private World world;
    private Engine engine;
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    private final String debug = System.getProperty("debug");

    @Override
    public void create() {
        viewport = new FitViewport(10, 8);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        setScreen(new MainMenuScreen(
                this::startGame,
                viewport,
                batch,
                font
            )
        );
    }

    @Override
    public void render() {
        super.render();
    }

    private void startGame() {
        world = Physics.world();
        engine = Physics.engine();
        var menuScreen = this.getScreen();
        this.setScreen(new MainPlayScreen((frameDelta) -> engine.apply(world, frameDelta), viewport));
        menuScreen.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        if (this.getScreen() != null) {
            this.getScreen().dispose();
        }
    }

}
