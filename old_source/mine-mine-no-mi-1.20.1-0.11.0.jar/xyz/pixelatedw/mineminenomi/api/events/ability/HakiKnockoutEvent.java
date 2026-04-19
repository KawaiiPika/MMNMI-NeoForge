package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class HakiKnockoutEvent extends LivingEvent {
   private LivingEntity source;
   private boolean isInitialBurst;
   private int knockoutTimer;

   public HakiKnockoutEvent(LivingEntity entity, LivingEntity source, boolean isInitialBurst, int knockoutTimer) {
      super(entity);
      this.source = source;
      this.isInitialBurst = isInitialBurst;
      this.knockoutTimer = knockoutTimer;
   }

   public LivingEntity getSource() {
      return this.source;
   }

   public boolean isInitialBurst() {
      return this.isInitialBurst;
   }

   public int getKnockdownTimer() {
      return this.knockoutTimer;
   }

   @Cancelable
   public static class Pre extends HakiKnockoutEvent {
      public Pre(LivingEntity entity, LivingEntity source, boolean isInitialBurst, int knockoutTimer) {
         super(entity, source, isInitialBurst, knockoutTimer);
      }
   }

   public static class Post extends HakiKnockoutEvent {
      public Post(LivingEntity entity, LivingEntity source, boolean isInitialBurst, int knockoutTimer) {
         super(entity, source, isInitialBurst, knockoutTimer);
      }
   }
}
