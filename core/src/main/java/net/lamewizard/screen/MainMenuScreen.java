package net.lamewizard.screen;

import com.badlogic.gdx.Screen;

import java.util.Objects;

/** First screen of the application. Displayed after the application is created. */
public final class MainMenuScreen implements Screen {

    private final Runnable startGame;

    public MainMenuScreen(Runnable startGame){
        Objects.requireNonNull(startGame, "Start game function cannot be null.");
        this.startGame = startGame;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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

    private final void onStart() {
        this.startGame.run();
    }
}
