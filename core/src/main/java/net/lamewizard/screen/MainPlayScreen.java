package net.lamewizard.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.physics.box2d.*;

import java.util.function.Consumer;
import java.util.function.Function;

public final class MainPlayScreen extends PhysicsScreen {
    private final Viewport viewport;


    public MainPlayScreen(Consumer<Float> physicsProcess,Viewport viewport, Function<BodyDef, Body> bodyFactory) {
        super(physicsProcess);
        this.viewport = viewport;


        // experimental
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5, 10);
        Body body = bodyFactory.apply(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = body.createFixture(fixtureDef);

        circle.dispose();

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
