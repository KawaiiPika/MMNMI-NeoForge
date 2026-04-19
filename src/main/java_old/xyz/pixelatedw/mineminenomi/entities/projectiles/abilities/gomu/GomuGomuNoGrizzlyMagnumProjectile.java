package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class GomuGomuNoGrizzlyMagnumProjectile extends NuHorizontalLightningEntity {
   private static final double KNOCKBACK = (double)14.0F;
   private Vec3 lookVec;

   public GomuGomuNoGrizzlyMagnumProjectile(EntityType<? extends GomuGomuNoGrizzlyMagnumProjectile> type, Level world) {
      super(type, world);
      this.lookVec = Vec3.f_82478_;
   }

   public GomuGomuNoGrizzlyMagnumProjectile(Level world, LivingEntity entity, Ability ability) {
      super((EntityType)ModProjectiles.GOMU_GOMU_NO_GRIZZLY_MAGNUM.get(), entity, 40.0F, 8.0F, ability);
      this.lookVec = Vec3.f_82478_;
      this.setRetracting();
      this.setSize(0.05F);
      this.setOneTimeHit();
      this.setDepth(1);
      this.setDamage(40.0F);
      this.setPassThroughEntities();
      this.setFist();
      this.setEntityCollisionSize((double)5.0F, (double)3.0F, (double)5.0F);
      this.lookVec = entity.m_20154_();
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         Vec3 speed = this.lookVec.m_82541_().m_82542_((double)14.0F, (double)1.0F, (double)14.0F).m_82520_((double)0.0F, 0.15, (double)0.0F);
         AbilityHelper.setDeltaMovement(target, speed);
      }

   }
}
