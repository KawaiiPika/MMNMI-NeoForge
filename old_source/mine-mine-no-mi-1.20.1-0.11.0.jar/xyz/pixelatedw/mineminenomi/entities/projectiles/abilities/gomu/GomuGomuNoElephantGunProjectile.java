package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GomuGomuNoElephantGunProjectile extends NuHorizontalLightningEntity {
   private static final double KNOCKBACK = (double)7.0F;
   private Vec3 lookVec;

   public GomuGomuNoElephantGunProjectile(EntityType<? extends GomuGomuNoElephantGunProjectile> type, Level world) {
      super(type, world);
      this.lookVec = Vec3.f_82478_;
   }

   public GomuGomuNoElephantGunProjectile(Level world, LivingEntity entity, IAbility ability) {
      super((EntityType)ModProjectiles.GOMU_GOMU_NO_ELEPHANT_GUN.get(), entity, 80.0F, 7.0F, ability);
      this.lookVec = Vec3.f_82478_;
      this.setRetracting();
      this.setSize(0.05F);
      this.setOneTimeHit();
      this.setDepth(1);
      this.setDamage(24.0F);
      this.setFist();
      this.setEntityCollisionSize((double)5.0F, (double)3.0F, (double)5.0F);
      this.lookVec = entity.m_20154_();
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         Vec3 speed = this.lookVec.m_82541_().m_82542_((double)7.0F, (double)1.0F, (double)7.0F).m_82520_((double)0.0F, 0.15, (double)0.0F);
         AbilityHelper.setDeltaMovement(target, speed);
      }

   }

   private void onBlockImpactEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 2.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)null);
         explosion.setDamageEntities(false);
         explosion.m_46061_();
      });
   }
}
