package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public class FactionHurtByTargetGoal extends HurtByTargetGoal {
   @Nullable
   private Predicate<Entity> factionPredicate;

   public FactionHurtByTargetGoal(PathfinderMob entity, @Nullable Predicate<Entity> factionPredicate, Class<?>... exclude) {
      super(entity, exclude);
      this.factionPredicate = factionPredicate;
   }

   public boolean m_8045_() {
      return this.factionPredicate != null && !this.factionPredicate.test(this.f_26137_) ? false : super.m_8045_();
   }

   protected void m_5766_(Mob other, LivingEntity target) {
      Faction selfFaction = (Faction)EntityStatsCapability.get(this.f_26135_).flatMap((props) -> props.getFaction()).orElse((Object)null);
      Faction otherFaction = (Faction)EntityStatsCapability.get(other).flatMap((props) -> props.getFaction()).orElse((Object)null);
      if (selfFaction != null && otherFaction != null && selfFaction.equals(otherFaction)) {
         super.m_5766_(other, target);
      }
   }

   protected boolean m_26150_(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
      return this.factionPredicate != null && this.factionPredicate.test(potentialTarget) ? super.m_26150_(potentialTarget, targetPredicate) : false;
   }
}
