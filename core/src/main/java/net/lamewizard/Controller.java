package net.lamewizard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.lamewizard.physics.Engine;
import net.lamewizard.physics.Physics;
import net.lamewizard.stage.PhysicsStage;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public final class Controller extends Game {

    private World world;
    private Engine engine;
    private Viewport viewport;
    private BitmapFont font;
    private Stage stage;

    private Box2DDebugRenderer debugRenderer;

    @Override
    public void create() {
        String debug = System.getenv("LAME_DEBUG");
        if (debug != null) {
            debugRenderer = new Box2DDebugRenderer();
        }
        initFont();

        viewport = new FitViewport(20, 20);
        world = Physics.world();
        engine = Physics.engine();
        stage = PhysicsStage.testStage(
            (bodyDef) -> world.createBody(bodyDef),
            viewport
        );
        Gdx.input.setInputProcessor(stage);
    }

    private void initFont() {
        var generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Orbitron-VariableFont_wght.ttf"));
        var parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setUseIntegerPositions(false);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        processPhysics(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    private void processPhysics(float delta) {
        if (debugRenderer != null) {
            debugRenderer.render(world, viewport.getCamera().combined);
        }
        engine.apply(world, delta);
    }

    @Override
    public void dispose() {
        font.dispose();
        if (stage != null) {
            stage.dispose();
        }
    }

}
