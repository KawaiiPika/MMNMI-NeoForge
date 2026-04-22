package xyz.pixelatedw.mineminenomi.api.helpers;

import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class TargetHelper {
   public static <T extends Entity> List<T> getEntitiesAroundCircle(LivingEntity entity, Vec3 pos, double radius, double innerDepth, @Nullable TargetPredicate targetPredicate, Class<? extends T>... classEntities) {
      double x = pos.f_82479_;
      double y = pos.f_82480_;
      double z = pos.f_82481_;
      AABB aabb = (new AABB(x, y, z, x, y, z)).m_82377_(radius, radius, radius);
      List<T> list = new ArrayList();

      for(Class<? extends T> clzz : classEntities) {
         for(T target : entity.m_9236_().m_45976_(clzz, aabb)) {
            double distanceSqr = target.m_20275_(x, y, z);
            double maxDistanceSqr = (radius - innerDepth) * (radius - innerDepth);
            if ((targetPredicate == null || !targetPredicate.test(entity, target)) && distanceSqr >= maxDistanceSqr) {
               list.add(target);
            }
         }
      }

      return list;
   }

   public static boolean isEntityInView(LivingEntity entity, Entity target, float fov) {
      double dx = target.m_20185_() - entity.m_20185_();

      double dz;
      for(dz = target.m_20189_() - entity.m_20189_(); dx * dx + dz * dz < 1.0E-4; dz = (Math.random() - Math.random()) * 0.01) {
         dx = (Math.random() - Math.random()) * 0.01;
      }

      float yaw;
      for(yaw = (float)(Math.atan2(dz, dx) * (double)180.0F / Math.PI) - entity.m_146908_() - 90.0F; yaw < -180.0F; yaw += 360.0F) {
      }

      while(yaw >= 180.0F) {
         yaw -= 360.0F;
      }

      return yaw < fov && yaw > -fov;
   }

   public static <T extends Player> List<T> getNearbyPlayers(LevelAccessor world, Vec3 pos, double radius, @Nullable Predicate<Entity> predicate) {
      if (predicate == null) {
         predicate = Predicates.alwaysTrue();
      }

      Predicate<Entity> var5 = ModEntityPredicates.IS_ALIVE_AND_SURVIVAL.and(predicate);
      return (List)world.m_6907_().stream().filter(var5).filter((target) -> {
         double d0 = target.m_20275_(pos.f_82479_, pos.f_82480_, pos.f_82481_);
         double d1 = radius * radius;
         return d1 < (double)0.0F || d0 < d1;
      }).collect(Collectors.toList());
   }

   public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor level, LivingEntity entity, double size, @Nullable TargetPredicate targetPredicate, Class<T> clz) {
      return getEntitiesInArea(level, entity, entity.m_20183_(), size, size, size, targetPredicate, clz);
   }

   public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor level, @Nullable LivingEntity entity, BlockPos centerPos, double size, @Nullable TargetPredicate targetPredicate, Class<T> clz) {
      return getEntitiesInArea(level, entity, centerPos, size, size, size, targetPredicate, clz);
   }

   public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor level, @Nullable LivingEntity entity, BlockPos centerPos, double sizeX, double sizeY, double sizeZ, @Nullable TargetPredicate targetPredicate, Class<T> clz) {
      AABB aabb = (new AABB(centerPos, centerPos.m_7918_(1, 1, 1))).m_82377_(sizeX, sizeY, sizeZ);
      return getEntitiesInArea(level, entity, aabb, targetPredicate, clz);
   }

   public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor level, @Nullable LivingEntity entity, AABB aabb, @Nullable TargetPredicate targetPredicate, Class<T> clz) {
      if (targetPredicate == null) {
         targetPredicate = TargetPredicate.DEFAULT_AREA_CHECK;
      }

      List<T> targets = new ArrayList();
      Stream var10000 = level.m_45976_(clz, aabb).stream().filter((target) -> !targets.contains(target) && targetPredicate.test(entity, target));
      Objects.requireNonNull(targets);
      var10000.forEach(targets::add);
      return targets;
   }

   public static <T extends LivingEntity> List<T> getEntitiesInLine(LivingEntity entity, float distance, float size, @Nullable TargetPredicate targetPredicate, Class<T> clz) {
      return getEntitiesInLine(entity, distance, size, size, size, targetPredicate, clz);
   }

   public static <T extends LivingEntity> List<T> getEntitiesInLine(LivingEntity entity, BlockPos centerPos, float distance, float size, @Nullable TargetPredicate targetPredicate, Class<T> clz) {
      return getEntitiesInLine(entity, centerPos, distance, size, size, size, targetPredicate, clz);
   }

   public static <T extends LivingEntity> List<T> getEntitiesInLine(LivingEntity entity, float distance, float sizeX, float sizeY, float sizeZ, @Nullable TargetPredicate targetPredicate, Class<T> clz) {
      return getEntitiesInLine(entity, entity.m_20183_(), distance, sizeX, sizeY, sizeZ, targetPredicate, clz);
   }

   public static <T extends LivingEntity> List<T> getEntitiesInLine(LivingEntity entity, BlockPos centerPos, float distance, float sizeX, float sizeY, float sizeZ, @Nullable TargetPredicate targetPredicate, Class<T> clz) {
      if (targetPredicate == null) {
         targetPredicate = TargetPredicate.DEFAULT_LINE_CHECK;
      }

      List<T> targets = new ArrayList();
      Vec3 lookVec = entity.m_20154_().m_82541_();
      Vec3 pos = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F);
      double distanceTraveled = (double)0.0F;

      while(distanceTraveled < (double)distance) {
         distanceTraveled += lookVec.m_82553_();
         pos = pos.m_82549_(lookVec.m_82490_(lookVec.m_82553_()));
         double xOffset = (double)sizeX / (double)2.0F;
         double yOffset = (double)sizeY / (double)2.0F;
         double zOffset = (double)sizeZ / (double)2.0F;
         AABB aabb = new AABB(pos.f_82479_ - xOffset, pos.f_82480_ - yOffset, pos.f_82481_ - zOffset, pos.f_82479_ + xOffset, pos.f_82480_ + yOffset, pos.f_82481_ + zOffset);
         Stream var10000 = entity.m_9236_().m_45976_(clz, aabb).stream().filter((target) -> !targets.contains(target) && targetPredicate.test(entity, target));
         Objects.requireNonNull(targets);
         var10000.forEach(targets::add);
      }

      targets.sort(closestComparator(entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F)));
      return targets;
   }

   public static Comparator<Entity> closestComparator(Vec3 pos) {
      return (e1, e2) -> (int)(e1.m_20238_(pos) - e2.m_20238_(pos));
   }
}
