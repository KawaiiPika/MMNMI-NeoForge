package xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista;

import java.util.UUID;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.RadicalBeamAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PacifistaRadicalBeamGoal extends ProjectileAbilityWrapperGoal<RadicalBeamAbility> {
   private static final BonusManager.BonusValue NO_COLA_BONUS = new BonusManager.BonusValue(UUID.fromString("68573843-cc45-4639-84ec-67efaabc464a"), "No Cola Bonus", BonusOperation.MUL, 0.0F);

   public PacifistaRadicalBeamGoal(Mob entity) {
      super(entity, RadicalBeamAbility.INSTANCE.get());
      this.getAbility().getComponent(xyz.pixelatedw.mineminenomi.init.ModAbilityComponents.COLA_USAGE.get()).ifPresent((comp) -> comp.getColaBonusManager().addBonus(NO_COLA_BONUS));
      if (this.chargeComponent != null) {
         xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent animationComp = this.getAbility().getComponent(xyz.pixelatedw.mineminenomi.init.ModAbilityComponents.ANIMATION.get()).orElse(null);
         this.chargeComponent.addStartEvent(110, (_entity, _ability) -> {
            if (animationComp != null) {
               animationComp.start(entity, xyz.pixelatedw.mineminenomi.init.ModAnimations.HEAD_LASER_CHARGE);
            }

            entity.level().broadcastEntityEvent(entity, (byte)100);
         });
         this.chargeComponent.addEndEvent(110, (_entity, _ability) -> {
            if (animationComp != null) {
               animationComp.stop(entity);
            }

            entity.level().broadcastEntityEvent(entity, (byte)101);
         });
      }

   }

   @Override
   public void tickWrapper() {
      super.tickWrapper();
      this.entity.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED.get(), 10, 0));
      LivingEntity target = this.entity.getTarget();
      if (target != null) {
         GoalHelper.lookAtEntity(this.entity, target);
      }
   }
}
