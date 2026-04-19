package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu;

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

public class PainEntity extends NuProjectileEntity {
   public PainEntity(EntityType<? extends PainEntity> type, Level world) {
      super(type, world);
   }

   public PainEntity(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.PAIN.get(), world, player, ability);
      super.setArmorPiercing(1.0F);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      float size = Math.min(super.getDamage(), 20.0F);
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), Math.max(1.0F, size / 2.0F));
         explosion.setExplosionSound(true);
         explosion.setDamageEntities(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         explosion.setDamageEntities(false);
         explosion.m_46061_();
      });
   }
}
