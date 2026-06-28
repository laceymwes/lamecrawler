package net.lamewizard.stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.lamewizard.actor.GroundTestActor;
import net.lamewizard.actor.TestAnimatedActor;

import java.util.Objects;
import java.util.function.Function;

public final class PhysicsStage extends Stage {

    private final Function<BodyDef, Body> bodyFactory;

    private PhysicsStage(Function<BodyDef, Body> bodyFactory, Viewport viewport) {
        super(viewport);
        Objects.requireNonNull(viewport, "Viewport cannot be null.");
        Objects.requireNonNull(bodyFactory, "Body factory cannot be null.");
        this.bodyFactory = bodyFactory;
    }

    public static PhysicsStage testStage(Function<BodyDef, Body> bodyFactory, Viewport viewport) {
        var testStage = new PhysicsStage(bodyFactory, viewport);
        var testActorOne = new TestAnimatedActor(bodyFactory, new Vector2(1f, 3f));
        var testGround = new GroundTestActor(bodyFactory, new Vector2(0, 0), viewport.getWorldWidth(), 0.5f);
        testStage.addActor(testActorOne);
        testStage.addActor(testGround);
        testStage.setKeyboardFocus(testActorOne);
        return testStage;
    }

}
