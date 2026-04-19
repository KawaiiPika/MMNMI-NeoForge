package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class PopGreenProjectile extends AbstractArrow {
   public PopGreenProjectile(EntityType<? extends PopGreenProjectile> type, Level world) {
      super(type, world);
   }

   public PopGreenProjectile(LivingEntity thrower) {
      super((EntityType)ModProjectiles.POP_GREEN.get(), thrower, thrower.m_9236_());
   }

   protected ItemStack m_7941_() {
      return ((Item)ModItems.POP_GREEN.get()).m_7968_();
   }

   public boolean isOnGround() {
      return this.f_36703_;
   }
}
