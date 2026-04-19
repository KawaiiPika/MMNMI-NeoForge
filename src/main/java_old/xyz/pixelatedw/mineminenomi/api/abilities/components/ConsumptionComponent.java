package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class ConsumptionComponent extends AbilityComponent<IAbility> {
   private final PriorityEventPool<IOnConsumptionEvent> consumptionEvents = new PriorityEventPool<IOnConsumptionEvent>();

   public ConsumptionComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.CONSUMPTION.get(), ability);
   }

   public ConsumptionComponent addConsumptionEvent(IOnConsumptionEvent event) {
      this.consumptionEvents.addEvent(event);
      return this;
   }

   public boolean tryConsumption(LivingEntity entity, int nutrition, float saturationModifier) {
      return !this.consumptionEvents.dispatchCancelable((event) -> !event.consume(entity, this.getAbility(), nutrition, saturationModifier));
   }

   @FunctionalInterface
   public interface IOnConsumptionEvent {
      boolean consume(LivingEntity var1, IAbility var2, int var3, float var4);
   }
}
