package xyz.pixelatedw.mineminenomi.client.render.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

import java.util.HashMap;
import java.util.Map;

public class ModAnimations {

    public static final Map<String, AnimationDefinition> REGISTRY = new HashMap<>();

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

    public static final AnimationDefinition SHARK_ON_TOOTH = AnimationDefinition.Builder.withLength(1.0f).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(90, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(90, 180, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(90, 360, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition SHUFUKU_SAGYO = AnimationDefinition.Builder.withLength(1.0f).looping()
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-45, -63, -45), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-45, 63, 45), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition SPIN_TO_WIN = AnimationDefinition.Builder.withLength(1.0f).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(0, 180, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 360, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, -20, 20), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, 20, -20), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-15, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-15, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition BLACK_HOLE = AnimationDefinition.Builder.withLength(1.0f).looping()
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition MIZU_TAIHO = AnimationDefinition.Builder.withLength(1.0f).looping()
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    static {
        REGISTRY.put("scream", SCREAM);
        REGISTRY.put("shark_on_tooth", SHARK_ON_TOOTH);
        REGISTRY.put("shufuku_sagyo", SHUFUKU_SAGYO);
        REGISTRY.put("spin_to_win", SPIN_TO_WIN);
        REGISTRY.put("black_hole", BLACK_HOLE);
        REGISTRY.put("mizu_taiho", MIZU_TAIHO);
    }
}
