package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public record CUnlockAbilityPacket(String abilityId) implements CustomPacketPayload {

    public static final Type<CUnlockAbilityPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "unlock_ability"));

    public static final StreamCodec<FriendlyByteBuf, CUnlockAbilityPacket> STREAM_CODEC = StreamCodec.composite(
            net.minecraft.network.codec.ByteBufCodecs.STRING_UTF8,
            CUnlockAbilityPacket::abilityId,
            CUnlockAbilityPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                PlayerStats stats = PlayerStats.get(serverPlayer);
                if (stats != null) {
                    ResourceLocation resLoc = ResourceLocation.tryParse(this.abilityId);
                    if (resLoc != null) {
                        xyz.pixelatedw.mineminenomi.api.abilities.Ability ability = xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.get(resLoc);
                        if (ability != null) {
                            // Ensure client hasn't sent a bad unlock request based on thresholds.
                            if (stats.getDoriki() >= ability.getRequiredDoriki() && ability.canUnlock(serverPlayer).isSuccess()) {
                                stats.grantAbility(resLoc);
                                stats.sync(serverPlayer);
                            }
                        }
                    }
                }
            }
        });
    }
}
