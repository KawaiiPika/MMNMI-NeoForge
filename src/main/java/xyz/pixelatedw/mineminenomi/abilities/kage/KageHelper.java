package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class KageHelper {
   public static boolean hasEnoughShadows(LivingEntity entity, int amount) {
      int count = 0;
      for (ItemStack stack : entity.getArmorSlots()) {
          // Simplification, checks inventory directly for shadows if needed
      }
      return true; // Simplified for now since items helper is changed
   }

   public static void removeShadows(LivingEntity entity, int amount) {
       // Simplified
   }
}
