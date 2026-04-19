package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TempuraudonProjectile extends NuProjectileEntity {
   public TempuraudonProjectile(EntityType<? extends TempuraudonProjectile> type, Level world) {
      super(type, world);
   }

   public TempuraudonProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.TEMPURAUDON.get(), world, player, ability);
      this.setDamage(50.0F);
      this.setPassThroughEntities();
      this.setMaxLife(32);
      this.setArmorPiercing(1.0F);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((NuProjectileEntity)this, (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 6.0F);
         explosion.setStaticDamage(15.0F);
         explosion.disableExplosionKnockback();
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setExplosionSound(false);
         explosion.setDamageEntities(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION6);
         explosion.m_46061_();
      });
   }
}
