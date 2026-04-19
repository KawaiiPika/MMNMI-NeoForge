package xyz.pixelatedw.mineminenomi.api.math;

import java.util.function.Function;

public enum EasingFunction {
    SINE_IN(EasingFunctionHelper::easeInSine),
    QUAD_IN(EasingFunctionHelper::easeInQuad),
    CUBIC_IN(EasingFunctionHelper::easeInCubic),
    ELASTIC_IN(EasingFunctionHelper::easeInElastic),
    BACK_IN(EasingFunctionHelper::easeInBack),
    BOUNCE_IN(EasingFunctionHelper::easeInBounce),
    SINE_OUT(EasingFunctionHelper::easeOutSine),
    QUAD_OUT(EasingFunctionHelper::easeOutQuad),
    CUBIC_OUT(EasingFunctionHelper::easeOutCubic),
    ELASTIC_OUT(EasingFunctionHelper::easeOutElastic),
    BACK_OUT(EasingFunctionHelper::easeOutBack),
    BOUNCE_OUT(EasingFunctionHelper::easeOutBounce),
    SINE_IN_OUT(EasingFunctionHelper::easeInOutSine),
    QUAD_IN_OUT(EasingFunctionHelper::easeInOutQuad),
    CUBIC_IN_OUT(EasingFunctionHelper::easeInOutCubic),
    ELASTIC_IN_OUT(EasingFunctionHelper::easeInOutElastic),
    BACK_IN_OUT(EasingFunctionHelper::easeInOutBack),
    BOUNCE_IN_OUT(EasingFunctionHelper::easeInOutBounce);

    private final Function<Float, Float> func;

    EasingFunction(Function<Float, Float> func) {
        this.func = func;
    }

    public static final com.mojang.serialization.Codec<EasingFunction> CODEC = com.mojang.serialization.Codec.STRING.xmap(EasingFunction::valueOf, EasingFunction::name);
    public static final net.minecraft.network.codec.StreamCodec<net.minecraft.network.FriendlyByteBuf, EasingFunction> STREAM_CODEC = net.minecraft.network.codec.ByteBufCodecs.VAR_INT.map(i -> i == -1 ? null : EasingFunction.values()[i], e -> e == null ? -1 : e.ordinal()).cast();

    public float apply(float x) {
        return this.func.apply(x);
    }
}
