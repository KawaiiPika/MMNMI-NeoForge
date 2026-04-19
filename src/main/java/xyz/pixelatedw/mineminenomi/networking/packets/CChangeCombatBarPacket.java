package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public record CChangeCombatBarPacket(int change) implements CustomPacketPayload {
    public static final Type<CChangeCombatBarPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "change_combat_bar"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CChangeCombatBarPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CChangeCombatBarPacket::change,
            CChangeCombatBarPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CChangeCombatBarPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                int current = stats.getCombatBarSet();
                int next = (current + payload.change()) % 10; // Max 10 bars
                if (next < 0) next = 9;
                stats.setCombatBarSet(next);
                stats.sync(player);
            }
        });
    }
}
