package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class RepeaterComponent extends AbilityComponent<IAbility> {
   private int maxTriggers;
   private Interval triggerInterval;
   private boolean isActive;
   private int triggers;
   private final PriorityEventPool<IRepeaterTriggerEvent> triggerRepeaterEvents = new PriorityEventPool<IRepeaterTriggerEvent>();
   private final PriorityEventPool<IRepeaterStopEvent> stopRepeaterEvents = new PriorityEventPool<IRepeaterStopEvent>();

   public RepeaterComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.REPEATER.get(), ability);
   }

   public RepeaterComponent addTriggerEvent(IRepeaterTriggerEvent event) {
      this.triggerRepeaterEvents.addEvent(event);
      return this;
   }

   public RepeaterComponent addTriggerEvent(int priority, IRepeaterTriggerEvent event) {
      this.triggerRepeaterEvents.addEvent(priority, event);
      return this;
   }

   public RepeaterComponent addStopEvent(IRepeaterStopEvent event) {
      this.stopRepeaterEvents.addEvent(event);
      return this;
   }

   public RepeaterComponent addStopEvent(int priority, IRepeaterStopEvent event) {
      this.stopRepeaterEvents.addEvent(priority, event);
      return this;
   }

   public boolean hasFinished() {
      return this.triggers >= this.maxTriggers;
   }

   private void resetShots() {
      this.triggers = 0;
   }

   protected void doTick(LivingEntity entity) {
      this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get()).ifPresent((component) -> component.setDisabled(this.isActive));
      if (this.isActive && this.triggerInterval.canTick()) {
         ++this.triggers;
         this.triggerRepeaterEvents.dispatch((event) -> event.trigger(entity, this.getAbility()));
         if (this.hasFinished()) {
            this.stop(entity);
         }
      }

   }

   public void start(LivingEntity user, int maxTriggers, int triggerInterval) {
      this.ensureIsRegistered();
      if (!this.isActive) {
         this.isActive = true;
         this.maxTriggers = maxTriggers;
         this.triggerInterval = new Interval(triggerInterval);
         this.resetShots();
      }
   }

   public void stop(LivingEntity entity) {
      if (this.isActive) {
         this.isActive = false;
         this.stopRepeaterEvents.dispatch((event) -> event.stop(entity, this.getAbility()));
      }
   }

   public int getTriggerCount() {
      return this.triggers;
   }

   public int getMaxTriggers() {
      return this.maxTriggers;
   }

   @FunctionalInterface
   public interface IRepeaterStopEvent {
      void stop(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IRepeaterTriggerEvent {
      void trigger(LivingEntity var1, IAbility var2);
   }
}
