package net.lamewizard.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public abstract class AnimatedPhysicsActor extends Actor {
    private final Function<BodyDef, Body> bodyFactory;
    private final Map<String, Animation<TextureRegion>> animations = new HashMap<>();

    private String activeAnimationName;
    protected Body body;
    protected Vector2 bodyCenter;

    public AnimatedPhysicsActor(Function<BodyDef, Body> bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    /**
     * Set the physical body for this actor.
     * The body can only be set ONCE.
     * @param bodyDef
     */
    protected final void setBody(BodyDef bodyDef) {
        if (this.body != null) {
            throw new IllegalStateException("Body already set.");
        }
        Objects.requireNonNull(bodyDef, "Body definition cannot be null.");
        this.body = bodyFactory.apply(bodyDef);
    }

    protected Optional<Body> getBody() {
        return ofNullable(body);
    }

    /**
     * Build and store an animation for a multi-frame sprite sheet.
     * @param animationName
     * @param sheet
     * @param columns Number of columns in the sprite sheet.
     * @param rows Number of rows in the sprite sheet.
     */
    protected final void putAnimation(String animationName, Texture sheet, int columns, int rows) {
        TextureRegion[][] tempRegion = TextureRegion.split(sheet,
            sheet.getWidth() / columns,
            sheet.getHeight() / rows);

        TextureRegion[] frames = new TextureRegion[columns * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                frames[index++] = tempRegion[i][j];
            }
        }

        var animation = new Animation<TextureRegion>(0.025f, frames);
        animations.put(animationName, animation);
    }

    protected final Optional<Animation<TextureRegion>> getAnimation(String animationName) {
        return ofNullable(animations.get(animationName));
    }

    protected final void setActiveAnimation(String animationName) {
        if (animations.containsKey(animationName)) {
            this.activeAnimationName = animationName;
        }
    }

    protected final Optional<Animation<TextureRegion>> getActiveAnimation() {
        return ofNullable(animations.get(activeAnimationName));
    }

    /**
     * Get the last calculated center of the body.
     * See {@link AnimatedPhysicsActor#calculateBodyCenter(float, float)} to get an up-to-date center.
     * @return The last calculated center of the body, or null if the body center has not been calculated.
     */
    protected final Optional<Vector2> getBodyCenter() {
        return Optional.ofNullable(bodyCenter);
    }

    /**
     * Synthesize and set the center of this actor's body.
     * Center is determined as follows:
     *  x = body.getPosition().x + (width / 2)
     *  y = body.getPosition().y + (height / 2)
     * @param width
     * @param height
     * @return The updated center of the body, or null if the body is not set.
     */
    protected final Optional<Vector2> calculateBodyCenter(float width, float height) {
        return ofNullable(body)
            .map(body -> {
                if (bodyCenter == null) {
                    bodyCenter = new Vector2(body.getPosition());
                }
                bodyCenter.x = body.getPosition().x + (width / 2);
                bodyCenter.y = body.getPosition().y + (height / 2);
                return bodyCenter;
            });
    }
}
