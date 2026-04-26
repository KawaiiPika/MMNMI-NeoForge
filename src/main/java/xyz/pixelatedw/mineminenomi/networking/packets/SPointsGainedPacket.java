package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SPointsGainedPacket(int amount, String pointType) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SPointsGainedPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "points_gained"));

    public static final StreamCodec<FriendlyByteBuf, SPointsGainedPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, SPointsGainedPacket::amount,
        ByteBufCodecs.STRING_UTF8, SPointsGainedPacket::pointType,
        SPointsGainedPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
