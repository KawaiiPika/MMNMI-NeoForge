package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

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

public class RenpatsuNamariBoshiProjectile extends NuProjectileEntity {
   public RenpatsuNamariBoshiProjectile(EntityType<? extends RenpatsuNamariBoshiProjectile> type, Level world) {
      super(type, world);
   }

   public RenpatsuNamariBoshiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.RENPATSU_NAMARI_BOSHI.get(), world, player, ability);
      this.setDamage(15.0F);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 3.0F);
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
