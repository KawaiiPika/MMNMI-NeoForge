package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.entities.projectiles.PopGreenProjectile;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class PopGreenBowItem extends BowItem {
   public PopGreenBowItem(int maxDamage) {
      super((new Item.Properties()).m_41487_(1).m_41503_(maxDamage));
   }

   public Predicate<ItemStack> m_6437_() {
      return (stack) -> stack.m_41720_() == ModItems.POP_GREEN.get();
   }

   public AbstractArrow customArrow(AbstractArrow arrow) {
      return new PopGreenProjectile((LivingEntity)arrow.m_19749_());
   }

   public int m_6615_() {
      return 32;
   }
}
