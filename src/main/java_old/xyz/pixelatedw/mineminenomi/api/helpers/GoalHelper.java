package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.IGoalMemoriesEntity;
import xyz.pixelatedw.mineminenomi.data.entity.gcd.GCDCapability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class GoalHelper {
   private static final TargetingConditions VISIBLE_TARGET_IN_RANGE = TargetingConditions.m_148352_().m_26883_((double)16.0F);
   private static final TargetingConditions ALIVE_TARGET = TargetingConditions.m_148352_().m_26893_();
   private static final TargetPredicate NEARBY_TARGETS_PREDICATE = (new TargetPredicate()).testEnemyFaction().testSimpleInView();

   public static boolean canMove(LivingEntity entity) {
      AttributeInstance attr = entity.m_21051_((Attribute)ModAttributes.JUMP_HEIGHT.get());
      if (attr != null && attr.m_22135_() <= (double)0.0F) {
         return false;
      } else {
         attr = entity.m_21051_(Attributes.f_22279_);
         return attr == null || !(attr.m_22135_() <= (double)0.0F);
      }
   }

   public static boolean shouldMove(Mob entity) {
      if (!canMove(entity)) {
         return false;
      } else if (hasAliveTarget(entity)) {
         return true;
      } else {
         return entity.m_21573_().m_26570_() != null;
      }
   }

   /** @deprecated */
   @Deprecated
   public static void startGCD(LivingEntity entity) {
      GCDCapability.startGCD(entity);
   }

   /** @deprecated */
   @Deprecated
   public static boolean isGCDActive(LivingEntity entity) {
      return GCDCapability.isOnGCD(entity);
   }

   public static List<LivingEntity> getNearbyVisibleEntities(LivingEntity entity, double distance, @Nullable Predicate<Entity> predicate) {
      TargetPredicate targetPredicate = (new TargetPredicate()).selector(predicate);
      return (List)TargetHelper.getEntitiesInArea(entity.m_9236_(), entity, entity.m_20183_(), distance, targetPredicate, LivingEntity.class).stream().filter((target) -> target != entity && VISIBLE_TARGET_IN_RANGE.m_26885_(entity, target)).collect(Collectors.toList());
   }

   public static boolean hasAliveOwner(TamableAnimal entity) {
      LivingEntity owner = entity.m_269323_();
      return owner != null && owner.m_6084_();
   }

   public static boolean hasAliveTarget(Mob entity) {
      LivingEntity target = entity.m_5448_();
      return target != null && target.m_6084_() ? ALIVE_TARGET.m_26885_(entity, target) : false;
   }

   public static boolean isAtSameHeight(LivingEntity entity, LivingEntity target, double variation) {
      return Math.abs(entity.m_20182_().m_82546_(target.m_20182_()).f_82480_) < (double)1.0F + variation;
   }

   public static boolean isOutsideDistance(LivingEntity entity, LivingEntity target, double distance) {
      return entity.m_20280_(target) > distance * distance;
   }

   public static boolean isWithinDistance(LivingEntity entity, LivingEntity target, double distance) {
      return entity.m_20280_(target) <= distance * distance;
   }

   public static boolean isWithinMeleeAttackRange(LivingEntity entity, LivingEntity target, float bonus) {
      double d0 = entity.m_20275_(target.m_20185_(), target.m_20186_(), target.m_20189_());
      double d1 = (double)(entity.m_20205_() * 2.0F * entity.m_20205_() * 2.0F + target.m_20205_());
      d1 += (double)bonus;
      return d0 <= d1;
   }

   public static boolean isWithinMeleeAttackRange(LivingEntity entity, LivingEntity target) {
      double d0 = entity.m_20275_(target.m_20185_(), target.m_20186_(), target.m_20189_());
      double d1 = (double)(entity.m_20205_() * 2.0F * entity.m_20205_() * 2.0F + target.m_20205_());
      return d0 <= d1;
   }

   /** @deprecated */
   @Deprecated
   public static <E extends LivingEntity & IGoalMemoriesEntity> boolean isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(E entity, LivingEntity target, double distance) {
      Optional<LivingEntity> optional = (entity).<LivingEntity>getMemory(MemoryModuleType.f_26372_);
      if (!optional.isPresent()) {
         return false;
      } else {
         double d0 = entity.m_20238_(((LivingEntity)optional.get()).m_20182_());
         double d1 = entity.m_20238_(target.m_20182_());
         return d1 > d0 + distance * distance;
      }
   }

   public static boolean canSee(Mob entity, Entity target) {
      return entity.m_21574_().m_148306_(target);
   }

   public static boolean isVisibleFrom(Mob entity, Entity target, Vec3 lookVec) {
      Vec3 targetVec = new Vec3(target.m_20185_(), target.m_20188_(), target.m_20189_());
      if (target.m_9236_() == entity.m_9236_() && !(targetVec.m_82557_(lookVec) > (double)16384.0F)) {
         return entity.m_9236_().m_45547_(new ClipContext(lookVec, targetVec, Block.COLLIDER, Fluid.NONE, entity)).m_6662_() == Type.MISS;
      } else {
         return false;
      }
   }

   public static boolean isEntityInView(LivingEntity entity, Entity target) {
      return TargetHelper.isEntityInView(entity, target, 60.0F);
   }

   public static LivingEntity getNearestTarget(LivingEntity entity, Optional<LivingEntity> optionalTarget, LivingEntity target) {
      return !optionalTarget.isPresent() ? target : getTargetNearestMe(entity, (LivingEntity)optionalTarget.get(), target);
   }

   public static LivingEntity getTargetNearestMe(LivingEntity entity, LivingEntity target1, LivingEntity target2) {
      Vec3 target1Position = target1.m_20182_();
      Vec3 target2Position = target2.m_20182_();
      return entity.m_20238_(target1Position) < entity.m_20238_(target2Position) ? target1 : target2;
   }

   public static void lookAt(Mob entity, Vec3 position) {
      entity.m_7618_(Anchor.EYES, position);
   }

   public static void lookAtEachOther(Mob entity1, Mob entity2) {
      lookAtEntity(entity1, entity2);
      lookAtEntity(entity2, entity1);
   }

   public static void lookAtEntity(Mob entity, @Nullable Entity target) {
      if (target != null) {
         entity.m_7618_(Anchor.EYES, target.m_20182_().m_82520_((double)0.0F, (double)target.m_20192_(), (double)0.0F));
      }
   }

   public static boolean hasEnoughTargetsAround(Mob entity, int minEnemies, float distance) {
      List<LivingEntity> targets = TargetHelper.<LivingEntity>getEntitiesInArea(entity.m_9236_(), entity, (double)distance, NEARBY_TARGETS_PREDICATE, LivingEntity.class);
      int confirmedTargets = 0;

      for(LivingEntity target : targets) {
         if (target != null && target.m_6084_() && entity.m_20280_(target) <= (double)(distance * distance) && canSee(entity, target)) {
            ++confirmedTargets;
         }

         if (confirmedTargets >= minEnemies) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasHealthAbovePercentage(LivingEntity entity, double percentage) {
      return (double)entity.m_21223_() >= WyHelper.percentage(percentage, (double)entity.m_21233_());
   }

   public static boolean hasHealthUnderPercentage(LivingEntity entity, double percentage) {
      return (double)entity.m_21223_() < WyHelper.percentage(percentage, (double)entity.m_21233_());
   }

   public static boolean hasSolidBlockAbove(LivingEntity entity) {
      if (entity instanceof Mob mob) {
         LivingEntity target = mob.m_5448_();
         int checkDist = (int)Math.min(target.m_20182_().m_82546_(entity.m_20182_()).f_82480_, (double)5.0F);
         if (checkDist > 1) {
            for(int i = 1; i < checkDist; ++i) {
               BlockPos abovePos = entity.m_20183_().m_6630_(i);
               BlockState above = entity.m_9236_().m_8055_(abovePos);
               if (!above.m_60795_() && above.m_60783_(entity.m_9236_(), abovePos, Direction.DOWN)) {
                  return true;
               }
            }
         }
      } else {
         BlockPos abovePos = entity.m_20183_().m_7494_();
         BlockState above = entity.m_9236_().m_8055_(abovePos);
         if (!above.m_60795_() && above.m_60783_(entity.m_9236_(), abovePos, Direction.DOWN)) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasBlockInFace(LivingEntity entity) {
      Vec3 start = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F);
      Direction dir = entity.m_6350_();
      Vec3 end = start.m_82520_((double)dir.m_122429_(), (double)0.0F, (double)dir.m_122431_());
      BlockHitResult result = entity.m_9236_().m_45547_(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, (Entity)null));
      return result != null && result.m_6662_() != Type.MISS;
   }

   public static int getFrontWallHeight(LivingEntity entity) {
      Vec3 start = entity.m_20299_(1.0F);
      Vec3 end = start.m_82549_(entity.m_20154_());
      BlockHitResult result = entity.m_9236_().m_45547_(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, (Entity)null));
      if (result != null && result.m_6662_() != Type.MISS) {
         Direction dir = result.m_82434_().m_122424_();
         int blocks = 0;

         for(int i = 0; i < 4; ++i) {
            BlockPos pos = result.m_82425_().m_6630_(i);
            BlockState state = entity.m_9236_().m_8055_(pos);
            if (state.m_60783_(entity.m_9236_(), pos, dir)) {
               ++blocks;
            }
         }

         return blocks;
      } else {
         return 0;
      }
   }
}
