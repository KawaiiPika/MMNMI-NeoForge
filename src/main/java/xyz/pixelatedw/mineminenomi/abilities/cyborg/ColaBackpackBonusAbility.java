package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PassiveAbility;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class ColaBackpackBonusAbility extends PassiveAbility {
   public static final int EXTRA_COLA = 500;

   public ColaBackpackBonusAbility() {
      super();
   }

   private void removeEvent(LivingEntity entity) {
      ItemStack headStack = entity.getItemBySlot(EquipmentSlot.CHEST);
      if (entity instanceof ServerPlayer player) {
         PlayerStats stats = player.getData(ModDataAttachments.PLAYER_STATS);
         int cola = Mth.clamp(stats.getCola(), 0, stats.getMaxCola() - EXTRA_COLA);
         stats.setCola(cola);
         player.setData(ModDataAttachments.PLAYER_STATS, stats);

         if (!headStack.isEmpty() && headStack.getItem() == ModArmors.COLA_BACKPACK.get()) {
             headStack.set(ModDataComponents.COLA, Mth.clamp(stats.getCola(), 0, EXTRA_COLA));
         }
      }
   }

   public xyz.pixelatedw.mineminenomi.api.util.Result canUnlock(LivingEntity entity) {
      ItemStack headStack = entity.getItemBySlot(EquipmentSlot.CHEST);
      return (!headStack.isEmpty() && headStack.getItem() == ModArmors.COLA_BACKPACK.get()) ? xyz.pixelatedw.mineminenomi.api.util.Result.success() : xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
   }

   public net.minecraft.network.chat.Component getDisplayName() {
       return net.minecraft.network.chat.Component.literal("Cola Backpack Bonus");
   }

   public net.minecraft.resources.ResourceLocation getId() {
       return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "cola_backpack_bonus");
   }
}
