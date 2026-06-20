package net.lamewizard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Objects;

/** First screen of the application. Displayed after the application is created. */
public final class MainMenuScreen implements Screen {

    private final Runnable startGame;
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final BitmapFont font;

    public MainMenuScreen(Runnable startGame, Viewport viewport, SpriteBatch batch, BitmapFont font){
        Objects.requireNonNull(startGame, "Start game function cannot be null.");
        Objects.requireNonNull(viewport, "Viewport cannot be null.");
        Objects.requireNonNull(batch, "Sprite batch cannot be null.");
        Objects.requireNonNull(font, "Bitmap font cannot be null.");
        this.startGame = startGame;
        this.viewport = viewport;
        this.batch = batch;
        this.font = font;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        final float x = viewport.getWorldWidth() / 2;
        final float y = viewport.getWorldHeight() / 2;
        font.draw(batch, "Lame Crawler", x - 1, y);
        font.draw(batch, "Press any key to start", x - 1, y - 0.25f);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            this.startGame.run();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
