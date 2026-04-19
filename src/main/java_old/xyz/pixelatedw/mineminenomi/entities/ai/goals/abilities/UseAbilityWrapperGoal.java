package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class UseAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   public UseAbilityWrapperGoal(Mob entity, AbilityCore<A> core) {
      super(entity, core);
   }

   public boolean canUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         LivingEntity target = this.entity.m_5448_();
         return GoalHelper.canSee(this.entity, target);
      }
   }

   public boolean canContinueToUseWrapper() {
      return false;
   }

   public void startWrapper() {
   }

   public void tickWrapper() {
   }

   public void stopWrapper() {
   }
}
