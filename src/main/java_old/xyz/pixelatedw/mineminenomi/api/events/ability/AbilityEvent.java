package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityEvent extends LivingEvent {
   private IAbility ability;

   public AbilityEvent(LivingEntity entity, IAbility ability) {
      super(entity);
      this.ability = ability;
   }

   public IAbility getAbility() {
      return this.ability;
   }
}
