package net.lamewizard.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.function.Function;

public final class GroundTestActor extends AnimatedPhysicsActor {
    private final Vector2 startPosition;
    private final float height;
    private final float width;

    public GroundTestActor(Function<BodyDef, Body> bodyFactory, Vector2 startPosition, float width, float height) {
        super(bodyFactory);
        this.startPosition = startPosition;
        this.width = width;
        this.height = height;
        initBody();
    }

    private void initBody() {
        var bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(startPosition);
        setBody(bodyDef);

        var polyShape = new PolygonShape();
        polyShape.setAsBox(width / 2, height / 2);
        getBody().ifPresent(body -> body.createFixture(polyShape, 1f));
    }
}
