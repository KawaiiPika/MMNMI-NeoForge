package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class BowTriggerComponent extends AbilityComponent<IAbility> {
   private final PriorityEventPool<IOnShootEvent> shootEvents = new PriorityEventPool<IOnShootEvent>();

   public BowTriggerComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.BOW_TRIGGER.get(), ability);
   }

   public BowTriggerComponent addShootEvent(IOnShootEvent event) {
      this.shootEvents.addEvent(event);
      return this;
   }

   public boolean tryShoot(LivingEntity entity) {
      this.ensureIsRegistered();
      return !this.shootEvents.dispatchCancelable((event) -> !event.shoot(entity, this.getAbility()));
   }

   @FunctionalInterface
   public interface IOnShootEvent {
      boolean shoot(LivingEntity var1, IAbility var2);
   }
}
