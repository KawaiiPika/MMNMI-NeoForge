package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.awa;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RelaxHourProjectile extends NuProjectileEntity {
   public RelaxHourProjectile(EntityType<? extends RelaxHourProjectile> type, Level world) {
      super(type, world);
   }

   public RelaxHourProjectile(Level world, LivingEntity player, IAbility parent) {
      super((EntityType)ModProjectiles.RELAX_HOUR.get(), world, player, parent);
      this.setDamage(3.0F);
      this.setMaxLife(40);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityHitEvent(EntityHitResult hit) {
      Entity hitEntity = hit.m_82443_();
      if (hitEntity instanceof LivingEntity living) {
         living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.WASHED.get(), 200, 0));
      }

   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.RELAX_HOUR.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }
}
