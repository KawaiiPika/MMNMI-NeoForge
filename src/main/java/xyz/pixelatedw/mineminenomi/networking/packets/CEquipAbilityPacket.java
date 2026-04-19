package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public record CEquipAbilityPacket(int slot, String abilityId) implements CustomPacketPayload {
    public static final Type<CEquipAbilityPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "equip_ability"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CEquipAbilityPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CEquipAbilityPacket::slot,
            ByteBufCodecs.STRING_UTF8, CEquipAbilityPacket::abilityId,
            CEquipAbilityPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CEquipAbilityPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                ResourceLocation abilityId = ResourceLocation.parse(payload.abilityId());
                xyz.pixelatedw.mineminenomi.api.abilities.Ability ability = xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.get(abilityId);
                if (ability != null) {
                    xyz.pixelatedw.mineminenomi.api.util.Result unlockResult = ability.canUnlock(player);
                    if (unlockResult.isFail()) {
                        if (unlockResult.getMessage() != null) {
                            player.displayClientMessage(unlockResult.getMessage(), true);
                        }
                        return;
                    }

                    if (ability.getRequiredFruit() != null) {
                        if (!stats.getDevilFruit().isPresent() || !stats.getDevilFruit().get().equals(ability.getRequiredFruit())) {
                            return;
                        }
                    }
                    stats.setEquippedAbility(payload.slot(), abilityId);
                    stats.sync(player);
                }
            }
        });
    }
}
