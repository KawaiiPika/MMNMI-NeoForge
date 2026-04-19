package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class DoruHelper {
   public static boolean hasColorPalette(LivingEntity entity) {
      if (ItemsHelper.countItemInInventory(entity, (Item)ModItems.COLOR_PALETTE.get()) > 0) {
         return true;
      } else {
         if (entity instanceof Mr3Entity) {
            Mr3Entity mr3Entity = (Mr3Entity)entity;
            if (mr3Entity.hasColorPaletteBonus()) {
               return true;
            }
         }

         return false;
      }
   }
}
