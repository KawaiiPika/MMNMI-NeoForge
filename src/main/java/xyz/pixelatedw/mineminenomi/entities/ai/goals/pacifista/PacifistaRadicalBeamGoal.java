package xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista;

import java.util.UUID;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
// import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PacifistaRadicalBeamGoal extends TickedGoal {

   public PacifistaRadicalBeamGoal(Mob entity) {
      super(entity);
   }

   @Override
   public boolean canUse() {
       return false; // TEMPORARY disabled due to RadicalBeamAbility and wrappers not existing yet.
   }

   @Override
   public boolean canContinueToUse() {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      // this.entity.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED.get(), 10, 0));
      LivingEntity target = this.entity.getTarget();
      if (target != null) {
         GoalHelper.lookAtEntity(this.entity, target);
      }
   }
}
