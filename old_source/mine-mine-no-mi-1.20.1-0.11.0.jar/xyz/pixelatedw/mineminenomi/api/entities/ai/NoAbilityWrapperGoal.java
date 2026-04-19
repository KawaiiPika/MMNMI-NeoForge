package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class NoAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   public NoAbilityWrapperGoal(Mob entity, AbilityCore<A> core) {
      super(entity, core);
   }

   public boolean canUseWrapper() {
      return false;
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
