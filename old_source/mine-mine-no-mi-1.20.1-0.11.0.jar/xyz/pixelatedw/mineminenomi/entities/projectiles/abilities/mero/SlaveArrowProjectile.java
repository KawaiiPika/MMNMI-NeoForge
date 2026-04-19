package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mero;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class SlaveArrowProjectile extends NuProjectileEntity {
   public SlaveArrowProjectile(EntityType<? extends SlaveArrowProjectile> type, Level world) {
      super(type, world);
   }

   public SlaveArrowProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.SLAVE_ARROW.get(), world, player, ability);
      this.setDamage(1.6F);
      this.setMaxLife(28);
      this.addEntityHitEvent(100, this::onEntityHitEvent);
   }

   private void onEntityHitEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity living) {
         living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.LOVESTRUCK.get(), 100, 0));
      }

   }
}
