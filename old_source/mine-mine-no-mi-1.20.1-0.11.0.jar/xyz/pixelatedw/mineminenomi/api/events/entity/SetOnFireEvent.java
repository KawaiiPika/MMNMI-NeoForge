package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class SetOnFireEvent extends EntityEvent {
   private LivingEntity attacker;
   private int fireTime;

   public SetOnFireEvent(LivingEntity attacker, Entity target, int fireTime) {
      super(target);
      this.attacker = attacker;
      this.fireTime = fireTime;
   }

   public LivingEntity getAttacker() {
      return this.attacker;
   }

   public int getFireTime() {
      return this.fireTime;
   }
}
