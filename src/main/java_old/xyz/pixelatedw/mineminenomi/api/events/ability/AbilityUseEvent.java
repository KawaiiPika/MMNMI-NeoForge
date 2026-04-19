package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityUseEvent extends AbilityEvent {
   public AbilityUseEvent(LivingEntity living, IAbility ability) {
      super(living, ability);
   }

   @Cancelable
   public static class Pre extends AbilityUseEvent {
      public Pre(LivingEntity living, IAbility ability) {
         super(living, ability);
      }
   }

   public static class Post extends AbilityUseEvent {
      public Post(LivingEntity living, IAbility ability) {
         super(living, ability);
      }
   }
}
