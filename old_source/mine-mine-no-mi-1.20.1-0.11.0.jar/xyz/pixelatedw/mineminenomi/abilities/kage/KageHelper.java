package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class KageHelper {
   public static Result hasEnoughShadows(LivingEntity entity, IAbility ability, int amount) {
      return ItemsHelper.countItemInInventory(entity, (Item)ModItems.SHADOW.get()) < amount ? Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_SHADOWS) : Result.success();
   }

   public static void removeShadows(LivingEntity entity, int amount) {
      for(ItemStack stack : ItemsHelper.getAllInventoryItems(entity)) {
         if (amount <= 0) {
            break;
         }

         if (stack.m_41720_().equals(ModItems.SHADOW.get())) {
            if (stack.m_41613_() >= amount) {
               stack.m_41774_(amount);
               break;
            }

            amount -= stack.m_41613_();
            stack.m_41774_(amount);
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public static int getAvailableShadows(LivingEntity entity) {
      return ItemsHelper.countItemInInventory(entity, (Item)ModItems.SHADOW.get());
   }
}
