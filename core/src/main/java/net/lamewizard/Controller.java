package net.lamewizard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.lamewizard.physics.Engine;
import net.lamewizard.physics.Physics;
import net.lamewizard.screen.MainMenuScreen;
import net.lamewizard.screen.TestScreen;

import java.util.function.Consumer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public final class Controller extends Game {

    private World world;
    private Engine engine;
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    private Box2DDebugRenderer debugRenderer;

    @Override
    public void create() {
        String debug = System.getenv("LAME_DEBUG");
        if (debug != null) {
            debugRenderer = new Box2DDebugRenderer();
        }
        viewport = new FitViewport(20, 20);
        batch = new SpriteBatch();
        initFont();
        font.setUseIntegerPositions(false);
        setScreen(new MainMenuScreen(
                this::startGame,
                viewport,
                batch,
                font
            )
        );
    }

    private void initFont() {
        var generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Orbitron-VariableFont_wght.ttf"));
        var parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    private void startGame() {
        world = Physics.world();
        engine = Physics.engine();
        var menuScreen = this.getScreen();
        Consumer<Float> processPhysics = (frameDelta) -> {
            if (debugRenderer != null) {
                debugRenderer.render(world, viewport.getCamera().combined);
            }
            engine.apply(world, frameDelta);
        };
        this.setScreen(new TestScreen(
                processPhysics,
                viewport,
                (bodyDef) -> world.createBody(bodyDef)
            )
        );
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
