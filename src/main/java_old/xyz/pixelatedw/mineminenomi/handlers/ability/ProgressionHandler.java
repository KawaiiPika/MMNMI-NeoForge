package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.stream.Collectors;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncHakiDataPacket;

public class ProgressionHandler {
   private static boolean checkCategoryForNewUnlocks(LivingEntity entity, AbilityCategory category) {
      long updates = (Long)ModValues.abilityCategoryMap.get(category).stream().map((core) -> AbilityHelper.checkAndUnlockAbility(entity, core)).filter((b) -> b).collect(Collectors.counting());
      return updates != 0L;
   }

   public static void checkForPossibleFruitAbilities(LivingEntity entity) {
      Item dfItem = (Item)DevilFruitCapability.get(entity).map((props) -> props.getDevilFruitItem()).orElse(Items.f_41852_);
      ItemStack df = new ItemStack(dfItem);
      if (!df.m_41619_()) {
         for(AbilityCore<?> core : ((AkumaNoMiItem)df.m_41720_()).getAbilities()) {
            AbilityHelper.checkAndUnlockAbility(entity, core);
         }
      }

   }

   public static void checkAllForNewUnlocks(Player player, boolean sendUpdate) {
      long updates = (Long)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValues().stream().map((core) -> AbilityHelper.checkAndUnlockAbility(player, core)).filter((b) -> b).collect(Collectors.counting());
      if (sendUpdate && updates != 0L) {
         ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
      }

   }

   public static void checkForDevilFruitUnlocks(Player player) {
      if (checkCategoryForNewUnlocks(player, AbilityCategory.DEVIL_FRUITS)) {
         ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
      }

   }

   public static void checkForEquipmentUnlocks(LivingEntity entity) {
      if (checkCategoryForNewUnlocks(entity, AbilityCategory.EQUIPMENT) && entity instanceof ServerPlayer player) {
         ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
      }

   }

   public static void checkForFactionUnlocks(Player player) {
      if (checkCategoryForNewUnlocks(player, AbilityCategory.FACTION)) {
         ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
      }

   }

   public static void checkForStyleUnlocks(Player player) {
      if (checkCategoryForNewUnlocks(player, AbilityCategory.STYLE)) {
         ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
      }

   }

   public static void checkForRacialUnlocks(Player player) {
      if (checkCategoryForNewUnlocks(player, AbilityCategory.RACIAL)) {
         ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
      }

   }

   public static void checkForHakiUnlocks(Player player) {
      boolean updates = checkCategoryForNewUnlocks(player, AbilityCategory.HAKI);
      if (updates) {
         ModNetwork.sendTo(new SSyncHakiDataPacket(player), player);
         ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
      }

   }

   public static void forceAbilityUnlockAdvancementChecks(ServerPlayer player) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      if (props != null) {
         for(AbilityCoreUnlockWrapper<?> unlockWrapper : props.getUnlockedAbilities()) {
            ModAdvancements.UNLOCK_ABILITY.trigger(player, unlockWrapper.getAbilityCore());
         }

      }
   }
}
