package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSwingTriggerPacket;

public class SwingTriggerComponent extends AbilityComponent<IAbility> {
   private final PriorityEventPool<IOnSwingEvent> swingEvents = new PriorityEventPool<IOnSwingEvent>();
   private long lastSwingTime;

   public SwingTriggerComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.SWING_TRIGGER.get(), ability);
   }

   public SwingTriggerComponent addSwingEvent(IOnSwingEvent event) {
      this.swingEvents.addEvent(event);
      return this;
   }

   public SwingTriggerComponent addSwingEvent(int priority, IOnSwingEvent event) {
      this.swingEvents.addEvent(priority, event);
      return this;
   }

   public void swing(LivingEntity entity) {
      this.ensureIsRegistered();
      if (!this.getAbility().hasComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()) || !((DisableComponent)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).isDisabled()) {
         if (!this.getAbility().hasComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()) || !((CooldownComponent)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).get()).isOnCooldown()) {
            this.swingEvents.dispatch((event) -> event.swing(entity, this.getAbility()));
            this.lastSwingTime = entity.m_9236_().m_46467_();
            if (!entity.m_9236_().f_46443_) {
               ModNetwork.sendToAllTrackingAndSelf(new SSwingTriggerPacket(entity, this.getAbility()), entity);
            }

         }
      }
   }

   public long getLastSwingTime() {
      return this.lastSwingTime;
   }

   @FunctionalInterface
   public interface IOnSwingEvent {
      void swing(LivingEntity var1, IAbility var2);
   }
}
