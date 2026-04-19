package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event.HasResult;

@HasResult
public class SetCameraZoomEvent extends PlayerEvent {
   private double zoom;

   public SetCameraZoomEvent(Player player, double zoom) {
      super(player);
      this.zoom = zoom;
   }

   public double getZoom() {
      return this.zoom;
   }

   public void setZoom(double zoom) {
      this.zoom = zoom;
   }
}
