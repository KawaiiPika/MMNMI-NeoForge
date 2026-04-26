package xyz.pixelatedw.mineminenomi.client.render.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

import java.util.HashMap;
import java.util.Map;

public class ModAnimations {

    public static final Map<String, AnimationDefinition> REGISTRY = new HashMap<>();


    public static final AnimationDefinition BACKFLIP = AnimationDefinition.Builder.withLength(1.0f)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4f, KeyframeAnimations.degreeVec(360, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, new org.joml.Vector3f(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4f, new org.joml.Vector3f(0, 8f, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, new org.joml.Vector3f(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(-80, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(-80, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(50, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(50, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition BATTO_STRIKE = AnimationDefinition.Builder.withLength(1.0f)
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(70, 20, 40), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(50, 20, 40), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(85, 20, 50), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(65, 20, 50), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition DASH = AnimationDefinition.Builder.withLength(1.0f)
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(80, -180, -90), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(80, 180, 90), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition LEAP = AnimationDefinition.Builder.withLength(1.0f)
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(180, 0, -10), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(70, -5, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(70, 5, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition BLOCK = AnimationDefinition.Builder.withLength(1.0f)
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-70, -70, -45), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-70, 70, 45), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition DODGE = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0, 0, 50), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, new org.joml.Vector3f(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, new org.joml.Vector3f(8f, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, new org.joml.Vector3f(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition SCREAM = AnimationDefinition.Builder.withLength(1.0f)
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-120, 0, 45), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(-120, 0, -45), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    static {
        REGISTRY.put("scream", SCREAM);
        REGISTRY.put("backflip", BACKFLIP);
        REGISTRY.put("batto_strike", BATTO_STRIKE);
        REGISTRY.put("dash", DASH);
        REGISTRY.put("leap", LEAP);
        REGISTRY.put("block", BLOCK);
        REGISTRY.put("dodge", DODGE);

    }
}
