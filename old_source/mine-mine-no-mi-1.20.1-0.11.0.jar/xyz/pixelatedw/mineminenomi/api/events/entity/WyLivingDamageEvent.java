package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class WyLivingDamageEvent extends LivingEvent {
   private DamageSource source;
   private float amount;

   public WyLivingDamageEvent(LivingEntity entity, DamageSource source, float amount) {
      super(entity);
      this.source = source;
      this.amount = amount;
   }

   public DamageSource getSource() {
      return this.source;
   }

   public void setSource(DamageSource source) {
      this.source = source;
   }

   public float getAmount() {
      return this.amount;
   }

   public void setAmount(float amount) {
      this.amount = amount;
   }
}
