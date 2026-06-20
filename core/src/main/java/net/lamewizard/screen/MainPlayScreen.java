package net.lamewizard.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.function.Consumer;
import java.util.function.Function;

public final class MainPlayScreen extends PhysicsScreen {
    private final Viewport viewport;

    public MainPlayScreen(Consumer<Float> physicsProcess, Viewport viewport) {
        super(physicsProcess);
        this.viewport = viewport;
    }

    @Override
    protected void input() {

    }

    @Override
    protected void logic() {

    }

    @Override
    protected void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
    }

    @Override
    public void show() {

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
}
