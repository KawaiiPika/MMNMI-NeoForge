package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import java.util.Optional;

public record CFinishCharacterCreatorPacket(
        Optional<ResourceLocation> faction,
        Optional<ResourceLocation> race,
        Optional<ResourceLocation> subRace,
        Optional<ResourceLocation> fightingStyle
) implements CustomPacketPayload {
    public static final Type<CFinishCharacterCreatorPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "finish_character_creator"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CFinishCharacterCreatorPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), CFinishCharacterCreatorPacket::faction,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), CFinishCharacterCreatorPacket::race,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), CFinishCharacterCreatorPacket::subRace,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), CFinishCharacterCreatorPacket::fightingStyle,
            CFinishCharacterCreatorPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CFinishCharacterCreatorPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                payload.faction().ifPresent(stats::setFaction);
                
                payload.race().ifPresent(raceLoc -> {
                    stats.setRace(raceLoc);
                    xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race race = xyz.pixelatedw.mineminenomi.init.ModRegistries.RACES.get(raceLoc);
                    if (race != null && race.getSelectionInfo() != null) {
                        race.getSelectionInfo().getTopAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                        race.getSelectionInfo().getBottomAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                    }
                });

                payload.subRace().ifPresent(subRaceLoc -> {
                    stats.setSubRace(subRaceLoc);
                    xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race subRace = xyz.pixelatedw.mineminenomi.init.ModRegistries.RACES.get(subRaceLoc);
                    if (subRace != null && subRace.getSelectionInfo() != null) {
                        subRace.getSelectionInfo().getTopAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                        subRace.getSelectionInfo().getBottomAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                    }
                });

                payload.fightingStyle().ifPresent(styleLoc -> {
                    stats.setFightingStyle(styleLoc);
                    xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle style = xyz.pixelatedw.mineminenomi.init.ModRegistries.FIGHTING_STYLES.get(styleLoc);
                    if (style != null && style.getSelectionInfo() != null) {
                        style.getSelectionInfo().getTopAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                        style.getSelectionInfo().getBottomAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                    }
                });
                
                stats.sync(player);
            }
        });
    }
}
