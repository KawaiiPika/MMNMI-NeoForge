package xyz.pixelatedw.mineminenomi.handlers.ability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCharacterCreatorScreenPacket;

public class ValidationHandler {
   public static void validateAllItemsForComponentChecks(LivingEntity entity) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         for(ItemStack stack : ItemsHelper.getAllInventoryItems(entity)) {
            if (stack.m_41782_() && stack.m_41783_().m_128461_("spawnedByAbility") != null) {
               ResourceLocation rs = ResourceLocation.parse(stack.m_41783_().m_128461_("spawnedByAbility"));
               AbilityCore<?> core = AbilityCore.get(rs);
               if (core != null) {
                  IAbility abl = props.getEquippedAbility(core);
                  if (abl == null) {
                     return;
                  }

                  if (abl.hasComponent((AbilityComponentKey)ModAbilityComponents.ITEM_SPAWN.get()) && !((ItemSpawnComponent)abl.getComponent((AbilityComponentKey)ModAbilityComponents.ITEM_SPAWN.get()).get()).isActive()) {
                     stack.m_41774_(stack.m_41741_());
                  }
               }
            }
         }

      }
   }

   public static void forceSelectionScreen(Player player) {
      if (ServerConfig.isForcedSelectionEnabled()) {
         boolean hasValidState = (Boolean)EntityStatsCapability.get(player).map((props) -> props.hasRace() && props.hasFaction() && props.hasFightingStyle()).orElse(false);
         if (!hasValidState) {
            boolean hasRandomizedRace = ServerConfig.getRaceRandomizer();
            boolean allowSubRaceSelect = ServerConfig.getAllowSubRaceSelect();
            ModNetwork.sendTo(new SOpenCharacterCreatorScreenPacket(hasRandomizedRace, allowSubRaceSelect), player);
         }
      }

   }

   public static void validateFaction(Player player) {
      FactionsWorldData worldData = FactionsWorldData.get();
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      if (props != null) {
         if (!props.isPirate()) {
            Crew crew = worldData.getCrewWithMember(player.m_20148_());
            if (crew != null) {
               worldData.removeCrewMember(player.m_9236_(), crew, player.m_20148_());
            }
         }

      }
   }
}
