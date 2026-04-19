package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class LivingHealByEvent extends LivingHealEvent {
   private LivingEntity healer;

   public LivingHealByEvent(LivingEntity healer, LivingEntity entity, float amount) {
      super(entity, amount);
      this.healer = healer;
   }

   public LivingEntity getHealer() {
      return this.healer;
   }
}
