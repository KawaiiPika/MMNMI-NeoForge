package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public record SUpdatePlayerStatsPacket(PlayerStats stats) implements CustomPacketPayload {

    public static final Type<SUpdatePlayerStatsPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "update_player_stats"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SUpdatePlayerStatsPacket> STREAM_CODEC = StreamCodec.composite(
            PlayerStats.STREAM_CODEC,
            SUpdatePlayerStatsPacket::stats,
            SUpdatePlayerStatsPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SUpdatePlayerStatsPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            PlayerStats localStats = PlayerStats.get(context.player());
            localStats.setBasic(payload.stats().getBasic());
            localStats.setCombat(payload.stats().getCombat());
        });
    }
}
