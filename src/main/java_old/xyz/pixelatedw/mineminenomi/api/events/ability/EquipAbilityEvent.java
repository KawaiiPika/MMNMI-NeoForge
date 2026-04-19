package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@Cancelable
public class EquipAbilityEvent extends LivingEvent {
   private IAbility ability;

   public EquipAbilityEvent(LivingEntity entity, IAbility ability) {
      super(entity);
      this.ability = ability;
   }

   public IAbility getAbility() {
      return this.ability;
   }
}
