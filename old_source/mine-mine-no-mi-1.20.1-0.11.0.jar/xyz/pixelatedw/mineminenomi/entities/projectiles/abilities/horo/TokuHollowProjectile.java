package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.horo;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TokuHollowProjectile extends NuProjectileEntity {
   public TokuHollowProjectile(EntityType<? extends TokuHollowProjectile> type, Level world) {
      super(type, world);
   }

   public TokuHollowProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.TOKU_HOLLOW.get(), world, player, ability);
      this.setDamage(25.0F);
      this.setPassThroughEntities();
      this.setEntityCollisionSize((double)5.0F);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 350, 0));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 350, 0));
      }

   }

   private void onBlockImpactEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 7.0F);
         explosion.setStaticDamage(35.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION8);
         explosion.setDamageEntities(true);
         explosion.m_46061_();
      });
   }
}
