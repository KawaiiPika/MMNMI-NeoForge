package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class HakiAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   public HakiAbilityWrapperGoal(Mob entity, AbilityCore<A> core) {
      super(entity, core);
   }

   public boolean canUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         boolean hasEmptyHands = this.entity.m_21205_().m_41619_();
         if (this.getAbility().getCore() != BusoshokuHakiHardeningAbility.INSTANCE.get() && this.getAbility().getCore() != BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()) {
            if (this.getAbility().getCore() == BusoshokuHakiImbuingAbility.INSTANCE.get()) {
               return !hasEmptyHands;
            } else {
               return true;
            }
         } else {
            return hasEmptyHands;
         }
      }
   }

   public boolean canContinueToUseWrapper() {
      return GoalHelper.hasAliveTarget(this.entity);
   }

   public void startWrapper() {
   }

   public void tickWrapper() {
   }

   public void stopWrapper() {
   }
}
