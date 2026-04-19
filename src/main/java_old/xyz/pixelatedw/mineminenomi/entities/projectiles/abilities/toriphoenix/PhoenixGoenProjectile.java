package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.toriphoenix;

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

public class PhoenixGoenProjectile extends NuProjectileEntity {
   private Vec3 lookVec;

   public PhoenixGoenProjectile(EntityType<? extends PhoenixGoenProjectile> type, Level world) {
      super(type, world);
   }

   public PhoenixGoenProjectile(Level world, LivingEntity player, IAbility ability, Vec3 lookVec) {
      super((EntityType)ModProjectiles.PHOENIX_GOEN.get(), world, player, ability);
      this.setDamage(10.0F);
      this.setMaxLife(30);
      this.lookVec = lookVec;
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         Vec3 speed = this.lookVec.m_82542_((double)1.5F, (double)0.0F, (double)1.5F);
         AbilityHelper.setDeltaMovement(target, speed.f_82479_, 0.15, speed.f_82481_);
         target.f_19789_ = 0.0F;
      }

   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.PHOENIX_GOEN.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }
}
