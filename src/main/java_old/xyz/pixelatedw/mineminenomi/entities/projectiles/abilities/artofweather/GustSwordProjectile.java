package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GustSwordProjectile extends NuProjectileEntity {
   public GustSwordProjectile(EntityType<? extends GustSwordProjectile> type, Level world) {
      super(type, world);
   }

   public GustSwordProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.GUST_SWORD.get(), world, player, ability);
      this.setDamage(2.0F);
      this.addEntityHitEvent(100, this::entityImpactEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         Vec3 speed = this.getOwner().m_20154_().m_82541_().m_82542_((double)4.0F, (double)1.0F, (double)4.0F);
         if (target.m_6469_(this.getDamageSource(), 15.0F)) {
            AbilityHelper.setDeltaMovement(target, speed.f_82479_, 0.2, speed.f_82481_);
         }
      }

   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GUST_SWORD.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }
}
