package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public record CToggleCombatModePacket() implements CustomPacketPayload {
    public static final Type<CToggleCombatModePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "toggle_combat_mode"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CToggleCombatModePacket> CODEC = StreamCodec.unit(new CToggleCombatModePacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CToggleCombatModePacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            PlayerStats stats = PlayerStats.get(player);
            stats.setInCombatMode(!stats.isInCombatMode());
            stats.sync(player);
        });
    }
}
