package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PadHoProjectile extends NuProjectileEntity {
   public PadHoProjectile(EntityType<? extends PadHoProjectile> type, Level world) {
      super(type, world);
   }

   public PadHoProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.PAD_HO.get(), world, player, ability);
      this.setDamage(15.0F);
      this.setArmorPiercing(1.0F);
      this.setPassThroughEntities();
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      if (this.getDamage() > 10.0F) {
         Vec3 speed = result.m_82443_().m_20154_().m_82542_((double)-1.0F, (double)-1.0F, (double)-1.0F).m_82542_(WyHelper.randomWithRange(4, 6), WyHelper.randomWithRange(1, 3), WyHelper.randomWithRange(4, 6));
         AbilityHelper.setDeltaMovement(result.m_82443_(), speed.f_82479_, speed.f_82480_, speed.f_82481_);
         result.m_82443_().f_19789_ = 0.0F;
      }

   }

   private void onBlockImpactEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), this.getDamage() / 5.0F);
         explosion.setStaticDamage(this.getDamage() / 3.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         explosion.setDamageEntities(false);
         explosion.m_46061_();
      });
   }
}
