package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class SpiralHollowProjectile extends NuProjectileEntity {
   public SpiralHollowProjectile(EntityType<? extends SpiralHollowProjectile> type, Level world) {
      super(type, world);
   }

   public SpiralHollowProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.SPIRAL_HOLLOW.get(), world, player, ability);
      this.setDamage(30.0F);
      this.setMaxLife(10);
      this.setPassThroughEntities();
      this.setEntityCollisionSize((double)2.5F, (double)1.0F, (double)2.5F);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.BLEEDING.get(), 100, 0));
      }

   }
}
