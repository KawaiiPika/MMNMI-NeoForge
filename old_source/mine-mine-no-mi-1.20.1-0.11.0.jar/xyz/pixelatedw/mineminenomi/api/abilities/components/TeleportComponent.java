package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class TeleportComponent extends AbilityComponent<IAbility> {
   private final PriorityEventPool<ITeleportEvent> teleportEvents = new PriorityEventPool<ITeleportEvent>();
   private boolean stopIfHit;
   private boolean blockYGain;
   private BlockPos location;
   private float teleportDistance;

   public TeleportComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.TELEPORT.get(), ability);
   }

   public TeleportComponent setStopIfHit(boolean flag) {
      this.stopIfHit = flag;
      return this;
   }

   public TeleportComponent setBlockYGain(boolean flag) {
      this.blockYGain = true;
      return this;
   }

   public BlockHitResult getTeleportHit(LivingEntity entity, float maxDistance, boolean safe) {
      Vec3 startVec = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F);
      Vec3 endVec = startVec.m_82549_(entity.m_20154_().m_82490_((double)maxDistance));
      if (entity instanceof Mob mob) {
         if (mob.m_5448_() != null && mob.m_5448_().m_6084_() && entity.m_20270_(mob.m_5448_()) < maxDistance) {
            endVec = mob.m_5448_().m_20182_();
         }
      }

      BlockHitResult result = entity.m_9236_().m_45547_(new ClipContext(startVec, endVec, Block.COLLIDER, Fluid.NONE, entity));
      if (safe && result.m_6662_() != Type.MISS) {
         BlockPos dashPos = result.m_82434_().equals(Direction.DOWN) ? result.m_82425_().m_6625_(2) : result.m_82425_().m_121955_(result.m_82434_().m_122436_());
         if (dashPos.m_123342_() > entity.m_9236_().m_151558_()) {
            dashPos = dashPos.m_7918_(0, entity.m_9236_().m_151558_() - dashPos.m_123342_(), 0);
         }

         Direction dir = Direction.m_122372_((float)dashPos.m_123341_(), (float)dashPos.m_123342_(), (float)dashPos.m_123343_());
         result = new BlockHitResult(Vec3.m_82512_(dashPos), dir, dashPos, false);
      }

      return result;
   }

   @Nullable
   public BlockPos calculateDestination(LivingEntity entity, float maxDistance) {
      return this.calculateDestination(entity, maxDistance, maxDistance / 2.0F);
   }

   @Nullable
   public BlockPos calculateDestination(LivingEntity entity, float maxDistance, float safeDistance) {
      this.ensureIsRegistered();
      if (entity.m_9236_().f_46443_) {
         return null;
      } else {
         this.location = entity.m_20183_();
         if (!entity.m_6084_()) {
            return null;
         } else {
            BlockHitResult mop = this.getTeleportHit(entity, maxDistance, false);
            boolean isMiss = mop.m_6662_() == Type.MISS;
            if (this.stopIfHit) {
               Vec3 startPos = entity.m_20182_();

               for(double f = (double)0.0F; f < (double)1.0F; f += 0.13) {
                  double x = Mth.m_14139_(f, startPos.m_7096_(), (double)mop.m_82425_().m_123341_());
                  double y = Mth.m_14139_(f, startPos.m_7098_(), (double)mop.m_82425_().m_123342_());
                  double z = Mth.m_14139_(f, startPos.m_7094_(), (double)mop.m_82425_().m_123343_());
                  BlockPos pos = BlockPos.m_274561_(x, y, z);
                  List<Projectile> projectiles = TargetHelper.<Projectile>getEntitiesInArea(entity.m_9236_(), entity, pos, (double)entity.m_20205_(), (double)entity.m_20206_(), (double)entity.m_20205_(), TargetPredicate.EVERYTHING, Projectile.class);
                  if (!projectiles.isEmpty()) {
                     projectiles.sort(TargetHelper.closestComparator(startPos));
                     safeDistance = Mth.m_14116_((float)((Projectile)projectiles.get(0)).m_20238_(startPos));
                     break;
                  }
               }
            }

            BlockPos blockpos = null;
            if (isMiss) {
               this.teleportDistance = safeDistance;
               blockpos = this.getTeleportHit(entity, safeDistance, true).m_82425_();
            } else {
               this.teleportDistance = maxDistance;
               blockpos = WyHelper.getClearPositionForPlayer(entity, mop.m_82425_());
               if (blockpos == null) {
                  blockpos = this.getTeleportHit(entity, safeDistance, true).m_82425_();
               }
            }

            if (blockpos == null) {
               return null;
            } else {
               if (this.blockYGain) {
                  double floorDifference = AbilityHelper.getDifferenceToFloor(entity);
                  if (floorDifference > (double)1.0F && (double)blockpos.m_123342_() > entity.m_20186_()) {
                     blockpos = blockpos.m_175288_((int)entity.m_20186_());
                  }
               }

               this.location = blockpos;
               return blockpos;
            }
         }
      }
   }

   public boolean teleport(LivingEntity entity, float maxDistance, float safeDistance) {
      this.calculateDestination(entity, maxDistance, safeDistance);
      return this.teleport(entity);
   }

   public boolean teleport(LivingEntity entity) {
      this.ensureIsRegistered();
      if (this.location == null) {
         return false;
      } else {
         entity.m_8127_();
         entity.m_20324_((double)this.location.m_123341_(), (double)this.location.m_123342_(), (double)this.location.m_123343_());
         this.teleportEvents.dispatch((event) -> event.fireTeleportEvent(entity, this.location, this.getAbility()));
         return true;
      }
   }

   public BlockPos getTeleportLocation() {
      return this.location;
   }

   public float getTeleportDistance() {
      return this.teleportDistance;
   }

   @FunctionalInterface
   public interface ITeleportEvent {
      void fireTeleportEvent(LivingEntity var1, BlockPos var2, IAbility var3);
   }
}
