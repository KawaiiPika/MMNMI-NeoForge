package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public record CRemoveAbilityPacket(int slot) implements CustomPacketPayload {
    public static final Type<CRemoveAbilityPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "remove_ability"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CRemoveAbilityPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CRemoveAbilityPacket::slot,
            CRemoveAbilityPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CRemoveAbilityPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                stats.setEquippedAbility(payload.slot(), null);
                stats.sync(player);
            }
        });
    }
}
