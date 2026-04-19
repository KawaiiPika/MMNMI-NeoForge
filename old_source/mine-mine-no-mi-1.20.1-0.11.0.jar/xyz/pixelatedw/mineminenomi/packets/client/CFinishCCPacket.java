package xyz.pixelatedw.mineminenomi.packets.client;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.ICharacterCreatorEntry;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetPlayerDetailsEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.items.CharacterCreatorItem;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class CFinishCCPacket {
   private int factionId;
   private int raceId;
   private int styleId;
   private int subRaceId;

   public CFinishCCPacket() {
   }

   public CFinishCCPacket(int factionId, int raceId, int styleId, int subRaceId) {
      this.factionId = factionId;
      this.raceId = raceId;
      this.styleId = styleId;
      this.subRaceId = subRaceId;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.factionId);
      buffer.writeInt(this.raceId);
      buffer.writeInt(this.styleId);
      buffer.writeInt(this.subRaceId);
   }

   public static CFinishCCPacket decode(FriendlyByteBuf buffer) {
      CFinishCCPacket msg = new CFinishCCPacket();
      msg.factionId = buffer.readInt();
      msg.raceId = buffer.readInt();
      msg.styleId = buffer.readInt();
      msg.subRaceId = buffer.readInt();
      return msg;
   }

   public static void handle(CFinishCCPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IEntityStats entityProps = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (entityProps != null) {
               boolean hasCharBook = !player.m_21205_().m_41619_() && player.m_21205_().m_41720_().equals(ModItems.CHARACTER_CREATOR.get());
               boolean hasEmptyStats = entityProps.getFaction().isEmpty() || entityProps.getRace().isEmpty() || entityProps.getFightingStyle().isEmpty();
               if (hasCharBook || hasEmptyStats) {
                  List<Faction> factions = ((IForgeRegistry)WyRegistry.FACTIONS.get()).getValues().stream().filter(ICharacterCreatorEntry::isInBook).sorted(ICharacterCreatorEntry::compareTo).toList();
                  List<Race> races = ((IForgeRegistry)WyRegistry.RACES.get()).getValues().stream().filter(Race::isMainRace).filter(ICharacterCreatorEntry::isInBook).sorted(ICharacterCreatorEntry::compareTo).toList();
                  List<FightingStyle> styles = ((IForgeRegistry)WyRegistry.FIGHTING_STYLES.get()).getValues().stream().filter(ICharacterCreatorEntry::isInBook).sorted(ICharacterCreatorEntry::compareTo).toList();
                  message.factionId %= factions.size() + 1;
                  message.raceId %= races.size() + 1;
                  message.styleId %= styles.size() + 1;
                  Faction faction = null;
                  if (message.factionId == 0) {
                     faction = (Faction)factions.get(player.m_217043_().m_188503_(factions.size()));
                  } else {
                     faction = (Faction)factions.get(message.factionId - 1);
                  }

                  entityProps.setFaction(faction);
                  ModAdvancements.SELECT_FACTION.trigger(player, faction.getRegistryName());
                  Race race = null;
                  if (message.raceId != 0 && !ServerConfig.getRaceRandomizer()) {
                     race = (Race)races.get(message.raceId - 1);
                  } else {
                     race = (Race)races.get(player.m_217043_().m_188503_(races.size()));
                  }

                  entityProps.setRace(race);
                  ModAdvancements.SELECT_RACE.trigger(player, race.getRegistryName());
                  List<Race> subRaces = race.getSubRaces().stream().map(RegistryObject::get).filter(ICharacterCreatorEntry::isInBook).sorted(ICharacterCreatorEntry::compareTo).toList();
                  if (race.hasSubRaces() && subRaces.size() > 0) {
                     message.subRaceId %= race.getSubRaces().size();
                     Race subRace = null;
                     if (ServerConfig.getAllowSubRaceSelect()) {
                        subRace = (Race)subRaces.get(message.subRaceId);
                     } else {
                        subRace = (Race)subRaces.get(player.m_217043_().m_188503_(subRaces.size()));
                     }

                     entityProps.setSubRace(subRace);
                  }

                  FightingStyle style = null;
                  if (message.styleId == 0) {
                     style = (FightingStyle)styles.get(player.m_217043_().m_188503_(styles.size()));
                  } else {
                     style = (FightingStyle)styles.get(message.styleId - 1);
                  }

                  entityProps.setFightingStyle(style);
                  ModAdvancements.SELECT_STYLE.trigger(player, style.getRegistryName());
                  if (entityProps.isCyborg()) {
                     entityProps.setCola(entityProps.getMaxCola());
                  }

                  for(ItemStack is : player.m_150109_().f_35974_) {
                     if (is != null && is.m_41720_() instanceof CharacterCreatorItem) {
                        player.m_150109_().m_36057_(is);
                     }
                  }

                  IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
                  if (abilityData != null) {
                     for(AbilityNode node : abilityData.getNodes()) {
                        node.lockNode(player);
                     }
                  }

                  SetPlayerDetailsEvent event = new SetPlayerDetailsEvent(player);
                  MinecraftForge.EVENT_BUS.post(event);
                  ModNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(player), player);
                  ProgressionHandler.checkAllForNewUnlocks(player, false);
                  ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
