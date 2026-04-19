package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class YomiTriggerEvent extends PlayerEvent {
   private Player newPlayer;

   public YomiTriggerEvent(Player oldPlayer, Player newPlayer) {
      super(oldPlayer);
      this.newPlayer = newPlayer;
   }
}
