package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.carnivaltricks;

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

public class TopEntity extends NuProjectileEntity {
   public TopEntity(EntityType<? extends TopEntity> type, Level world) {
      super(type, world);
   }

   public TopEntity(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.TOP.get(), world, thrower, ability);
      this.setDamage(2.0F);
      this.setArmorPiercing(0.25F);
      this.addBlockHitEvent(100, this::blockImpactEvent);
   }

   private void blockImpactEvent(BlockHitResult result) {
      if (this.hashCode() % 3 == 0) {
         AbilityExplosion explosion = new AbilityExplosion(this.getOwner(), (IAbility)this.getParent().get(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 1.0F);
         explosion.setStaticDamage(2.0F);
         explosion.disableExplosionKnockback();
         explosion.setFireAfterExplosion(false);
         explosion.setDestroyBlocks(false);
         explosion.setExplosionSound(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         explosion.m_46061_();
      }

   }
}
