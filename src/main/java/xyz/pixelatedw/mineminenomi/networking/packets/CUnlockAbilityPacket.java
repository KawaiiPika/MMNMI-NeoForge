package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public record CUnlockAbilityPacket(ResourceLocation abilityId) implements CustomPacketPayload {
    public static final Type<CUnlockAbilityPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "unlock_ability"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CUnlockAbilityPacket> CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            CUnlockAbilityPacket::abilityId,
            CUnlockAbilityPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CUnlockAbilityPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            PlayerStats stats = PlayerStats.get(player);
            if (stats == null) return;

            // In a real scenario we might check costs or prerequisites here.
            // For now, we just grant the ability.
            stats.grantAbility(payload.abilityId());
            stats.sync(player);
        });
    }
}
