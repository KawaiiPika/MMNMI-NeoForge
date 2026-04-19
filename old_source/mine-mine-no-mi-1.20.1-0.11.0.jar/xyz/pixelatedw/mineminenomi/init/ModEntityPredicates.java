package xyz.pixelatedw.mineminenomi.init;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class ModEntityPredicates {
   public static final Predicate<Entity> ENTITY_EXISTS = (entity) -> entity != null;
   public static final Predicate<Entity> IS_ALIVE_AND_SURVIVAL;
   public static final Predicate<Entity> IS_ENTITY_HARMLESS;
   public static final Predicate<Entity> IS_INVISIBLE;
   public static final Predicate<Entity> TARGET_HAS_ISSUED_BOUNTY;

   public static Predicate<Entity> canBeSeenBy(LivingEntity entity) {
      return IS_ALIVE_AND_SURVIVAL.and((target) -> entity.m_142582_(target));
   }

   public static Predicate<Entity> canSee(LivingEntity entity) {
      return IS_ALIVE_AND_SURVIVAL.and((target) -> target instanceof LivingEntity ? ((LivingEntity)target).m_142582_(entity) : false);
   }

   public static Predicate<Entity> hasTargetWithinDistance(LivingEntity entity, float distance) {
      return canSee(entity).and(EntitySelector.m_20410_(entity.m_20182_().f_82479_, entity.m_20182_().f_82480_, entity.m_20182_().f_82481_, (double)distance));
   }

   public static Predicate<Entity> canBeTamed() {
      return (target) -> {
         if (target instanceof LivingEntity living) {
            return MobsHelper.canBeTamed(living);
         } else {
            return false;
         }
      };
   }

   public static Predicate<Entity> isTamedBy(@Nullable LivingEntity tamer) {
      return (target) -> {
         if (tamer == null) {
            return false;
         } else if (target instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)target;
            return MobsHelper.isTamedBy(living, tamer);
         } else {
            return false;
         }
      };
   }

   public static Predicate<Entity> getEnemyFactions(LivingEntity entity) {
      return (Predicate<Entity>)(entity == null ? Predicates.alwaysTrue() : (targetEntity) -> {
         if (targetEntity == null) {
            return false;
         } else if (targetEntity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)targetEntity;
            return FactionHelper.isEnemyFactions(entity, living);
         } else {
            return true;
         }
      });
   }

   public static Predicate<Entity> getFriendlyFactions(LivingEntity entity) {
      return getEnemyFactions(entity).negate();
   }

   static {
      IS_ALIVE_AND_SURVIVAL = ENTITY_EXISTS.and(EntitySelector.f_20406_).and(EntitySelector.f_20402_);
      IS_ENTITY_HARMLESS = (target) -> {
         MobCategory classification = target.m_6095_().m_20674_();
         if (!classification.equals(MobCategory.WATER_AMBIENT) && !classification.equals(MobCategory.AMBIENT)) {
            if (!(target instanceof Animal) && !(target instanceof AmbientCreature) && !(target instanceof AbstractFish)) {
               return target instanceof Squid;
            } else {
               return true;
            }
         } else {
            return true;
         }
      };
      IS_INVISIBLE = (target) -> target.m_20145_();
      TARGET_HAS_ISSUED_BOUNTY = (targetEntity) -> {
         if (!(targetEntity instanceof LivingEntity target)) {
            return false;
         } else {
            FactionsWorldData worldData = FactionsWorldData.get();
            IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
            if (targetProps == null) {
               return false;
            } else {
               boolean canHaveBounty = (Boolean)targetProps.getFaction().map((f) -> f.canReceiveBounty(target)).orElse(false);
               if (canHaveBounty) {
                  boolean isBelowOrNormalDifficulty = target.m_20193_().m_46791_().m_19028_() <= Difficulty.NORMAL.m_19028_();
                  if (target instanceof Player && isBelowOrNormalDifficulty && !worldData.hasIssuedBounty(target)) {
                     return false;
                  }
               }

               return true;
            }
         }
      };
   }
}
