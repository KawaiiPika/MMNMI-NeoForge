package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityTickEvent extends AbilityEvent {
   public AbilityTickEvent(LivingEntity entity, IAbility ability) {
      super(entity, ability);
   }
}
