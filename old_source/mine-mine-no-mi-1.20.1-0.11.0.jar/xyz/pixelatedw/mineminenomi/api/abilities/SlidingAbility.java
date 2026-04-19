package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public abstract class SlidingAbility extends Ability {
   protected final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(200, this::startContinuityEvent).addEndEvent(200, this::endContinuityEvent);
   protected final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);

   public SlidingAbility(AbilityCore<? extends SlidingAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.changeStatsComponent});
      this.addUseEvent(200, this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      AbilityAttributeModifier frictionMod = new AbilityAttributeModifier(UUID.fromString("679d5c5a-1508-47f8-8bb2-b60de7c81d95"), this, "Sliding Friction Modifier", (double)1.0F + this.getSlidingModifier(), Operation.ADDITION);
      AbilityAttributeModifier speedMod = new AbilityAttributeModifier(UUID.fromString("fd5db293-485a-46a3-8a7b-f508614f842a"), this, "Sliding Speed Modifier", this.getSpeedModifier(), Operation.MULTIPLY_BASE);
      this.changeStatsComponent.addAttributeModifier((Attribute)ModAttributes.FRICTION.get(), frictionMod);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22279_, speedMod);
      this.changeStatsComponent.applyModifiers(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.changeStatsComponent.removeModifiers(entity);
   }

   public abstract double getSpeedModifier();

   public abstract double getSlidingModifier();
}
