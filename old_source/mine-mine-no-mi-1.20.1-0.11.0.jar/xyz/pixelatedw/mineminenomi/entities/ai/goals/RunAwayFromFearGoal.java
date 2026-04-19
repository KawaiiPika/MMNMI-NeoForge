package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class RunAwayFromFearGoal extends TickedGoal<Mob> {
   private static final int COOLDOWN = 100;
   private int targetPosX;
   private int targetPosY;
   private int targetPosZ;
   private double speed;
   private LivingEntity target;
   private final double doriki;
   private final Predicate<Entity> filter;

   public RunAwayFromFearGoal(Mob entity, double speed, double threatMultiplier) {
      super(entity);
      this.speed = speed;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.TARGET));
      this.doriki = (Double)EntityStatsCapability.get(entity).map((props) -> props.getDoriki()).orElse((double)0.0F);
      this.filter = EntitySelector.f_20406_.and(ModEntityPredicates.getEnemyFactions(this.entity)).and((target) -> {
         if (!(target instanceof LivingEntity)) {
            return false;
         } else if (MobsHelper.shouldAttackTarget(entity).test(target)) {
            return false;
         } else {
            double entityThreatLevel = (double)MobsHelper.getThreatLevel(entity);
            double targetThreatLevel = (double)MobsHelper.getThreatLevel((LivingEntity)target);
            return targetThreatLevel > entityThreatLevel * threatMultiplier;
         }
      });
   }

   public boolean m_8036_() {
      if (NuWorld.isChallengeDimension(this.entity.m_9236_())) {
         return false;
      } else if (this.doriki >= (double)6000.0F) {
         return false;
      } else if (this.getLastEndTick() != 0L && !this.hasTimePassedSinceLastEnd(100.0F)) {
         return false;
      } else {
         Optional<LivingEntity> target = WyHelper.getNearbyLiving(this.entity.m_20182_(), this.entity.m_9236_(), (double)20.0F, this.filter).stream().findAny();
         if (target.isPresent()) {
            this.target = (LivingEntity)target.get();
            return true;
         } else {
            return false;
         }
      }
   }

   public void m_8056_() {
   }

   public void m_8037_() {
      if (this.target != null && this.target.m_6084_() && this.entity.m_21573_().m_26571_()) {
         BlockPos targetPos = this.target.m_20183_();
         this.targetPosX = (int)(this.entity.m_20185_() - ((double)targetPos.m_123341_() - this.entity.m_20185_()));
         this.targetPosY = (int)(this.entity.m_20186_() - ((double)targetPos.m_123342_() - this.entity.m_20186_()));
         this.targetPosZ = (int)(this.entity.m_20189_() - ((double)targetPos.m_123343_() - this.entity.m_20189_()));
         this.entity.m_21573_().m_26519_((double)this.targetPosX, (double)this.targetPosY, (double)this.targetPosZ, this.speed);
      }

   }

   public boolean m_8045_() {
      return this.target != null && this.target.m_6084_() && this.entity.m_20270_(this.target) < 40.0F;
   }
}
