package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event.HasResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

@HasResult
public class UnlockAbilityEvent extends LivingEvent {
   private AbilityCore<?> abilityCore;

   public UnlockAbilityEvent(LivingEntity entity, AbilityCore<?> abilityCore) {
      super(entity);
      this.abilityCore = abilityCore;
   }

   public AbilityCore<?> getAbilityCore() {
      return this.abilityCore;
   }
}
