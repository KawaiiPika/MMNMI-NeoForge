package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public record CExecuteAbilityPacket(int slot) implements CustomPacketPayload {
    public static final Type<CExecuteAbilityPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "execute_ability"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CExecuteAbilityPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            CExecuteAbilityPacket::slot,
            CExecuteAbilityPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CExecuteAbilityPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            PlayerStats stats = PlayerStats.get(player);
            if (stats == null || !stats.isInCombatMode()) return;

            int abilityIndex = payload.slot() + stats.getCombatBarSet() * 8;
            String abilityIdStr = stats.getEquippedAbility(abilityIndex);
            
            if (!abilityIdStr.isEmpty()) {
                ResourceLocation abilityId = ResourceLocation.parse(abilityIdStr);
                Ability ability = ModAbilities.REGISTRY.get(abilityId);
                if (ability != null) {
                    ability.use(player);
                }
            }
        });
    }
}
