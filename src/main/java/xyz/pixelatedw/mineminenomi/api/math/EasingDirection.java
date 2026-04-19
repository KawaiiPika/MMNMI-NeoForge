package xyz.pixelatedw.mineminenomi.api.math;

public enum EasingDirection {
    POSITIVE,
    NEGATIVE,
    HALF_HALF;

    public static final com.mojang.serialization.Codec<EasingDirection> CODEC = com.mojang.serialization.Codec.STRING.xmap(EasingDirection::valueOf, EasingDirection::name);
    public static final net.minecraft.network.codec.StreamCodec<net.minecraft.network.FriendlyByteBuf, EasingDirection> STREAM_CODEC = net.minecraft.network.codec.ByteBufCodecs.VAR_INT.map(i -> EasingDirection.values()[i], EasingDirection::ordinal).cast();
}
