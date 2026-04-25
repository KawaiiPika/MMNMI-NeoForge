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
                boolean hasCharBook = !player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() instanceof xyz.pixelatedw.mineminenomi.items.CharacterCreatorItem;
                boolean hasEmptyStats = stats.getBasic().identity().faction().isEmpty() || stats.getBasic().identity().race().isEmpty() || stats.getBasic().identity().fightingStyle().isEmpty();
                
                if (!hasCharBook && !hasEmptyStats) {
                    return;
                }

                ResourceLocation finalFaction = payload.faction().orElseGet(() -> {
                    java.util.List<xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction> factions = xyz.pixelatedw.mineminenomi.init.ModRegistries.FACTIONS.stream().filter(xyz.pixelatedw.mineminenomi.api.entities.charactercreator.ICharacterCreatorEntry::isInBook).toList();
                    if (!factions.isEmpty()) {
                        return factions.get(player.getRandom().nextInt(factions.size())).getRegistryName();
                    }
                    return null;
                });

                if (finalFaction != null) {
                    stats.setFaction(finalFaction);
                }

                ResourceLocation finalRace = payload.race().orElseGet(() -> {
                    java.util.List<xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race> races = xyz.pixelatedw.mineminenomi.init.ModRegistries.RACES.stream().filter(r -> r.isMainRace() && r.isInBook()).toList();
                    if (!races.isEmpty()) {
                        return races.get(player.getRandom().nextInt(races.size())).getRegistryName();
                    }
                    return null;
                });

                if (finalRace != null) {
                    stats.setRace(finalRace);
                    xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race raceObj = xyz.pixelatedw.mineminenomi.init.ModRegistries.RACES.get(finalRace);
                    if (raceObj != null && raceObj.getSelectionInfo() != null) {
                        raceObj.getSelectionInfo().getTopAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                        raceObj.getSelectionInfo().getBottomAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                    }

                    if (raceObj != null && raceObj.hasSubRaces()) {
                        ResourceLocation finalSubRace = payload.subRace().orElseGet(() -> {
                            java.util.List<xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race> subRaces = raceObj.getSubRaces().stream().map(java.util.function.Supplier::get).filter(xyz.pixelatedw.mineminenomi.api.entities.charactercreator.ICharacterCreatorEntry::isInBook).toList();
                            if (!subRaces.isEmpty()) {
                                return subRaces.get(player.getRandom().nextInt(subRaces.size())).getRegistryName();
                            }
                            return null;
                        });
                        if (finalSubRace != null) {
                            stats.setSubRace(finalSubRace);
                            xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race subRaceObj = xyz.pixelatedw.mineminenomi.init.ModRegistries.RACES.get(finalSubRace);
                            if (subRaceObj != null && subRaceObj.getSelectionInfo() != null) {
                                subRaceObj.getSelectionInfo().getTopAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                                subRaceObj.getSelectionInfo().getBottomAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                            }
                        }
                    }
                }

                ResourceLocation finalStyle = payload.fightingStyle().orElseGet(() -> {
                    java.util.List<xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle> styles = xyz.pixelatedw.mineminenomi.init.ModRegistries.FIGHTING_STYLES.stream().filter(xyz.pixelatedw.mineminenomi.api.entities.charactercreator.ICharacterCreatorEntry::isInBook).toList();
                    if (!styles.isEmpty()) {
                        return styles.get(player.getRandom().nextInt(styles.size())).getRegistryName();
                    }
                    return null;
                });

                if (finalStyle != null) {
                    stats.setFightingStyle(finalStyle);
                    xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle styleObj = xyz.pixelatedw.mineminenomi.init.ModRegistries.FIGHTING_STYLES.get(finalStyle);
                    if (styleObj != null && styleObj.getSelectionInfo() != null) {
                        styleObj.getSelectionInfo().getTopAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                        styleObj.getSelectionInfo().getBottomAbilities().forEach(s -> stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(s.get())));
                    }
                }

                if (stats.isCyborg()) {
                    stats.setCola(stats.getMaxCola());
                }

                for (net.minecraft.world.item.ItemStack is : player.getInventory().items) {
                    if (is != null && is.getItem() instanceof xyz.pixelatedw.mineminenomi.items.CharacterCreatorItem) {
                        player.getInventory().removeItem(is);
                    }
                }
                
                stats.sync(player);
            }
        });
    }
}
