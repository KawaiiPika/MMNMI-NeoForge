package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class ColaBackpackBonusAbility extends PassiveAbility {
   public static final int EXTRA_COLA = 500;
   public static final RegistryObject<AbilityCore<ColaBackpackBonusAbility>> INSTANCE = ModRegistry.registerAbility("cola_backpack_bonus", "Cola Backpack Bonus", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.EQUIPMENT, AbilityType.PASSIVE, ColaBackpackBonusAbility::new)).setHidden().setUnlockCheck(ColaBackpackBonusAbility::canUnlock).build("mineminenomi"));

   public ColaBackpackBonusAbility(AbilityCore<ColaBackpackBonusAbility> ability) {
      super(ability);
      this.addRemoveEvent(this::removeEvent);
   }

   private void removeEvent(LivingEntity entity, IAbility ability) {
      if (entity instanceof ServerPlayer player) {
         EntityStatsCapability.get(player).ifPresent((props) -> {
            int cola = Mth.m_14045_(props.getCola(), 0, props.getMaxCola() - 500);
            props.setCola(cola);
            ModNetwork.sendTo(new SUpdateColaAmountPacket(entity), player);
         });
      }

   }

   private static boolean canUnlock(LivingEntity entity) {
      ItemStack headStack = entity.m_6844_(EquipmentSlot.CHEST);
      return !headStack.m_41619_() && headStack.m_41720_() == ModArmors.COLA_BACKPACK.get();
   }
}
