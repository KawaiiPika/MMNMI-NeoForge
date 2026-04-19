package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hasshoken;

import net.minecraft.core.BlockPos;
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

public class ButoKaitenProjectile extends NuProjectileEntity {
   public ButoKaitenProjectile(EntityType<ButoKaitenProjectile> type, Level world) {
      super(type, world);
   }

   public ButoKaitenProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.BUTO_KAITEN.get(), world, player, ability);
      super.setMaxLife(5);
      super.addBlockHitEvent(100, this::onHitBlockEvent);
   }

   private void onHitBlockEvent(BlockHitResult result) {
      BlockPos hit = result.m_82425_();
      ExplosionComponent.createExplosion((IAbility)((IAbility)super.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)hit.m_123341_(), (double)hit.m_123342_(), (double)hit.m_123343_(), 1.0F);
         explosion.setStaticDamage(5.0F);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)2.0F));
         explosion.m_46061_();
      });
   }
}
