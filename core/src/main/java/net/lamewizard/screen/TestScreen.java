package net.lamewizard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.physics.box2d.*;

import java.util.function.Consumer;
import java.util.function.Function;

public final class TestScreen extends PhysicsScreen {
    private final Viewport viewport;
    private final Function<BodyDef, Body> bodyFactory;
    Body circleBody;
    Body kinematicBody;
    float kinematicAccumulator = 0f;


    public TestScreen(Consumer<Float> physicsProcess, Viewport viewport, Function<BodyDef, Body> bodyFactory) {
        super(physicsProcess);
        this.viewport = viewport;
        this.bodyFactory = bodyFactory;
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
        if (circleBody == null) {
            // experimental
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(3, 4);
            circleBody = bodyFactory.apply(bodyDef);
            CircleShape circle = new CircleShape();
            circle.setRadius(2f);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = circle;
            fixtureDef.density = 0.5f;
            fixtureDef.friction = 0.4f;
            fixtureDef.restitution = 1f;

            Fixture fixture = circleBody.createFixture(fixtureDef);

            circle.dispose();


            BodyDef kinematicBodyDef = new BodyDef();
            kinematicBodyDef.type = BodyDef.BodyType.KinematicBody;
            kinematicBodyDef.position.set(5, 4);
            kinematicBody = bodyFactory.apply(kinematicBodyDef);

            PolygonShape kinematicBox = new PolygonShape();
            kinematicBox.setAsBox(1f, 1f);
            kinematicBody.createFixture(kinematicBox, 0.0f);
            kinematicBox.dispose();

            kinematicBody.setLinearVelocity(new Vector2(2, -0.25f));

            // Create our body definition
            BodyDef groundBodyDef = new BodyDef();
            // Set its world position
            groundBodyDef.position.set(new Vector2(0, 0.25f));

            // Create a body from the definition and add it to the world
            Body groundBody = bodyFactory.apply(groundBodyDef);

            // Create a polygon shape
            PolygonShape groundBox = new PolygonShape();
            // Set the polygon shape as a box which is twice the size of our view port and 20 high
            // (setAsBox takes half-width and half-height as arguments)
            groundBox.setAsBox(viewport.getScreenWidth(), 1f);
            // Create a fixture from our polygon shape and add it to our ground body
            groundBody.createFixture(groundBox, 0.0f);
            // Clean up after ourselves
            groundBox.dispose();
        }

        if (kinematicBody != null) {
            var currentVelocity = kinematicBody.getLinearVelocity();
            if (kinematicAccumulator >= 1f) {
                kinematicAccumulator = 0f;
                if (currentVelocity.x > 0f) {
                    kinematicBody.setLinearVelocity(new Vector2(currentVelocity.x -.50f, currentVelocity.y));
                } else if (kinematicBody.getPosition().y < 1f) {
                    kinematicBody.setLinearVelocity(Vector2.Zero);
                }
            } else {
                kinematicAccumulator += Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void show() {

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
