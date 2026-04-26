package xyz.pixelatedw.mineminenomi.client.render.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

import java.util.HashMap;
import java.util.Map;

public class ModAnimations {

    public static final Map<String, AnimationDefinition> REGISTRY = new HashMap<>();


    public static final AnimationDefinition AIM_SNIPER = AnimationDefinition.Builder.withLength(1.0f).looping()
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, -5.7f, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, 45.8f, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition AIM_FLINTLOCK_RIGHT = AnimationDefinition.Builder.withLength(1.0f).looping()
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, -28.6f, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, 28.6f, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition AIM_FLINTLOCK_LEFT = AnimationDefinition.Builder.withLength(1.0f).looping()
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, 28.6f, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-90, -28.6f, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();


    public static final AnimationDefinition DOWNWARD_SLASH = AnimationDefinition.Builder.withLength(1.0f)
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(-170, 0, 10), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(1, 1, -5), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(-170, 0, -10), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(-1, 1, -5), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(0, 3, -1), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(0, 3, -2), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(-5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(0, 0, -2), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(15, -10, -5), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(0, 0, -0.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();


    public static final AnimationDefinition OVER_SHOULDER_SLASH = AnimationDefinition.Builder.withLength(1.0f)
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.degreeVec(-170, 0, 10), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(-170, -30, 50), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.posVec(1, 1, -5), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(1, 1, -5), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.degreeVec(-170, 0, -10), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(-170, 0, 20), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.posVec(-1, 1, -5), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(-1, 1, -5), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.posVec(0, 3, -1), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(0, 3, -1), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.posVec(0, 3, -2), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(0, 3, -2), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.degreeVec(-5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(-5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.posVec(0, 0, -2), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(0, 0, -2), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.degreeVec(15, -10, -5), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.degreeVec(15, -10, -5), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3f, KeyframeAnimations.posVec(0, 0, -0.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6f, KeyframeAnimations.posVec(0, 0, -0.5f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();


    public static final AnimationDefinition ITTORYU_CHARGE = AnimationDefinition.Builder.withLength(1.0f)
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 3, -4.5f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 3, -5), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(-10, 0, -5), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 3, -4), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(-40, 40, 20), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(-2, 2, -5), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(-5, 0, 0), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, -2), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(15, -10, -5), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.posVec(0, 0, -0.5f), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();


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

    public static final AnimationDefinition BODY_ROTATION_WIDE_ARMS = AnimationDefinition.Builder.withLength(1.0f).looping()
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 90), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, 90), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, -90), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 0, -90), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(0, 90, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5f, KeyframeAnimations.degreeVec(0, 180, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(0, 270, 0), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0f, KeyframeAnimations.degreeVec(0, 360, 0), AnimationChannel.Interpolations.LINEAR)
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

        REGISTRY.put("body_rotation_wide_arms", BODY_ROTATION_WIDE_ARMS);
        REGISTRY.put("aim_sniper", AIM_SNIPER);
        REGISTRY.put("aim_flintlock_right", AIM_FLINTLOCK_RIGHT);
        REGISTRY.put("aim_flintlock_left", AIM_FLINTLOCK_LEFT);
        REGISTRY.put("downward_slash", DOWNWARD_SLASH);
        REGISTRY.put("over_shoulder_slash", OVER_SHOULDER_SLASH);
        REGISTRY.put("ittoryu_charge", ITTORYU_CHARGE);
    }
}
