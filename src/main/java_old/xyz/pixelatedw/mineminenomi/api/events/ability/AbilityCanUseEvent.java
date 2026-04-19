package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@Cancelable
public class AbilityCanUseEvent extends AbilityEvent {
   public AbilityCanUseEvent(Player player, IAbility ability) {
      super(player, ability);
   }
}
