package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.bomu.BreezeBreathBombAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BreezeBreathBombProjectile extends NuProjectileEntity {
   public BreezeBreathBombProjectile(EntityType<BreezeBreathBombProjectile> type, Level world) {
      super(type, world);
   }

   public BreezeBreathBombProjectile(Level world, LivingEntity player, BreezeBreathBombAbility ability) {
      super((EntityType)ModProjectiles.BREEZE_BREATH_BOMB.get(), world, player, ability);
      this.setPhysical();
      this.setDamage(15.0F);
      this.setMaxLife(26);
      this.setPassThroughEntities();
      this.addTickEvent(100, this::tickEvent);
   }

   private void tickEvent() {
      if (this.f_19797_ >= 3) {
         BlockPos pos = this.m_20183_();
         ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
            AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 2.0F);
            explosion.setStaticDamage(12.0F);
            explosion.setExplosionSound(true);
            explosion.setDamageOwner(false);
            explosion.setDestroyBlocks(true);
            explosion.setFireAfterExplosion(false);
            explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
            explosion.setDamageEntities(true);
            explosion.m_46061_();
         });
      }
   }
}
