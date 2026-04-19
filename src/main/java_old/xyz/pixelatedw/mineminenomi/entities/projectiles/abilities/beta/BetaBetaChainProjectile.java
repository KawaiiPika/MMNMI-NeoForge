package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.beta;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BetaBetaChainProjectile extends NuHorizontalLightningEntity {
   public BetaBetaChainProjectile(EntityType<? extends BetaBetaChainProjectile> type, Level world) {
      super(type, world);
   }

   public BetaBetaChainProjectile(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.BETA_BETA_CHAIN.get(), thrower, 80.0F, 6.0F, ability);
      this.setFadeTime(20);
      this.setSize(0.07F);
      this.setOneTimeHit();
      this.setDamage(4.0F);
      this.setDepth(1);
      this.setPhysical();
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      Entity entity = this.getOwner();
      if (entity instanceof LivingEntity living) {
         BlockPos distance = result.m_82425_().m_121996_(living.m_20183_());
         AbilityHelper.setDeltaMovement(living, (double)distance.m_123341_() * 0.15, 0.3 + (double)(distance.m_123342_() / 5), (double)distance.m_123343_() * 0.15);
      }

   }
}
