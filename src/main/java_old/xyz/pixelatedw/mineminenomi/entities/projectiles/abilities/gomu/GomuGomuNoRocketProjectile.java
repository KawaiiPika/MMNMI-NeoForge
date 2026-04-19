package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class GomuGomuNoRocketProjectile extends NuHorizontalLightningEntity {
   public GomuGomuNoRocketProjectile(EntityType<? extends GomuGomuNoRocketProjectile> type, Level world) {
      super(type, world);
   }

   public GomuGomuNoRocketProjectile(Level world, LivingEntity entity, Ability ability) {
      super((EntityType)ModProjectiles.GOMU_GOMU_NO_ROCKET.get(), entity, 100.0F, 8.0F, ability);
      this.setRetracting();
      this.offsetToHand(entity);
      this.setFollowOwner();
      this.setSize(0.025F);
      this.setOneTimeHit();
      this.setDepth(1);
      this.setPassThroughEntities(false);
      this.setDamage(4.0F);
      this.setFist();
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      LivingEntity owner = this.getOwner();
      if (owner != null) {
         owner.m_21195_((MobEffect)ModEffects.REDUCED_FALL.get());
         BlockPos distance = result.m_82425_().m_121996_(owner.m_20183_());
         AbilityHelper.setDeltaMovement(owner, (double)distance.m_123341_() * 0.35, 0.3 + (double)distance.m_123342_() * 0.35, (double)distance.m_123343_() * 0.35);
      }

   }
}
