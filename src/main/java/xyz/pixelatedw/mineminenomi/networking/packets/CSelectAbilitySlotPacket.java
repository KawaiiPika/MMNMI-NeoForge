package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public record CSelectAbilitySlotPacket(int slot) implements CustomPacketPayload {
    public static final Type<CSelectAbilitySlotPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "select_ability_slot"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CSelectAbilitySlotPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            CSelectAbilitySlotPacket::slot,
            CSelectAbilitySlotPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CSelectAbilitySlotPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null && stats.isInCombatMode()) {
                stats.setSelectedAbilitySlot(payload.slot());
                stats.sync(player);
            }
        });
    }
}
