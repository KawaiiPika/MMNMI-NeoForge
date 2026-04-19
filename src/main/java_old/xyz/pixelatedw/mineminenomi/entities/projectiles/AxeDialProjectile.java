package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AxeDialProjectile extends NuProjectileEntity {
   public AxeDialProjectile(EntityType<AxeDialProjectile> type, Level world) {
      super(type, world);
   }

   public AxeDialProjectile(Level world, LivingEntity thrower) {
      super((EntityType)ModProjectiles.AXE_DIAL_PROJECTILE.get(), world, thrower);
      this.setDamage(20.0F);
      this.setMaxLife(20);
      this.setPhysical();
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult hit) {
      AbilityExplosion explosion = new AbilityExplosion(this, (IAbility)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), 4.0F);
      explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
      explosion.m_46061_();
   }
}
