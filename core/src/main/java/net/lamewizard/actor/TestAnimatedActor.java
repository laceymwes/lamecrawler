package net.lamewizard.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.function.Function;

public final class TestAnimatedActor extends AnimatedPhysicsActor {
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> idleAnimation;
    private float stateTime = 0;
    private final Vector2 startPosition;

    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 4;
    private static final String ANIMATION_WALK = "walk";
    private static final String ANIMATION_IDLE = "idle";
    private static final float CHAR_HEIGHT = 1f;
    private static final float CHAR_WIDTH = 1f;
    private static final Vector2 WALK_LEFT = new Vector2(-1f, 0);
    private static final Vector2 WALK_RIGHT = new Vector2(1f, 0);


    public TestAnimatedActor(Function<BodyDef, Body> bodyFactory, Vector2 startPosition) {
        super(bodyFactory);
        this.startPosition = startPosition;
        initBody();
        initAnimation();
        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
               return keyDownDelegate(keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return keyUpDelegate(keycode);
            }
        });
    }

    private void initBody() {
        var bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(startPosition);
        setBody(bodyDef);

        var polyShape = new PolygonShape();
        polyShape.setAsBox(CHAR_WIDTH / 2, CHAR_HEIGHT / 2);
        var fixtureDef = new FixtureDef();
        fixtureDef.shape = polyShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.2f;
        getBody().ifPresent(body -> body.createFixture(fixtureDef));
        polyShape.dispose();
    }

    private void initAnimation() {
//        var walkSheet = new Texture("test_walk.png");
//        var idleSheet = new Texture("test_idle.png");
//        this.putAnimation(ANIMATION_WALK, walkSheet, FRAME_COLS, FRAME_ROWS);
//        this.putAnimation(ANIMATION_IDLE, idleSheet, FRAME_COLS, FRAME_ROWS);
    }

    private boolean keyDownDelegate(int keycode) {
        stateTime = 0;
        var center = calculateBodyCenter(CHAR_WIDTH, CHAR_HEIGHT).orElse(Vector2.Zero);
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                this.getBody().ifPresent(body -> body.applyLinearImpulse(WALK_RIGHT.x, WALK_RIGHT.y, center.x, center.y, true));
                this.setActiveAnimation(ANIMATION_WALK);
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                this.getBody().ifPresent(body -> body.applyLinearImpulse(WALK_LEFT.x, WALK_LEFT.y, center.x, center.y, true));
                this.setActiveAnimation(ANIMATION_WALK);
        }
        return true;
    }

    private boolean keyUpDelegate(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.LEFT:
            case Input.Keys.A:
            case Input.Keys.D:
                stateTime = 0;
                var center = calculateBodyCenter(CHAR_WIDTH, CHAR_HEIGHT).orElse(Vector2.Zero);
                this.getBody().ifPresent(body -> body.applyLinearImpulse(0f, 0f, center.x, center.y, true));
                this.setActiveAnimation(ANIMATION_IDLE);
        }
        return true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {// Clear screen
        stateTime += Gdx.graphics.getDeltaTime();
        var animation = getActiveAnimation();
        if (animation.isPresent()) {
            TextureRegion frame = animation.get().getKeyFrame(stateTime, true);
            batch.begin();
            batch.draw(frame, getX(), getY());
            batch.end();
        }
    }

}
