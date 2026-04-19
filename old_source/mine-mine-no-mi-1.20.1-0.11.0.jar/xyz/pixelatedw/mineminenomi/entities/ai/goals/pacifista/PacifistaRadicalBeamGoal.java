package xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista;

import java.util.UUID;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.RadicalBeamAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PacifistaRadicalBeamGoal extends ProjectileAbilityWrapperGoal<RadicalBeamAbility> {
   private static final BonusManager.BonusValue NO_COLA_BONUS;

   public PacifistaRadicalBeamGoal(Mob entity) {
      super(entity, (AbilityCore)RadicalBeamAbility.INSTANCE.get());
      ((RadicalBeamAbility)this.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COLA_USAGE.get()).ifPresent((comp) -> comp.getColaBonusManager().addBonus(NO_COLA_BONUS));
      if (this.chargeComponent != null) {
         AnimationComponent animationComp = (AnimationComponent)((RadicalBeamAbility)this.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.ANIMATION.get()).orElse((Object)null);
         this.chargeComponent.addStartEvent(110, (_entity, _ability) -> {
            if (animationComp != null) {
               animationComp.start(entity, ModAnimations.HEAD_LASER_CHARGE);
            }

            entity.m_9236_().m_7605_(entity, (byte)100);
         });
         this.chargeComponent.addEndEvent(110, (_entity, _ability) -> {
            if (animationComp != null) {
               animationComp.stop(entity);
            }

            entity.m_9236_().m_7605_(entity, (byte)101);
         });
      }

   }

   public void tickWrapper() {
      super.tickWrapper();
      this.entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 10, 0));
      LivingEntity target = this.entity.m_5448_();
      if (target != null) {
         GoalHelper.lookAtEntity(this.entity, target);
      }

   }

   static {
      NO_COLA_BONUS = new BonusManager.BonusValue(UUID.fromString("68573843-cc45-4639-84ec-67efaabc464a"), "No Cola Bonus", BonusOperation.MUL, 0.0F);
   }
}
