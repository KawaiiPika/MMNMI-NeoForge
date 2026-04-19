package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event.HasResult;

@HasResult
public class SetCameraOffsetEvent extends PlayerEvent {
   private Vec3 cameraPos;

   public SetCameraOffsetEvent(Player player, Vec3 cameraPos) {
      super(player);
      this.cameraPos = cameraPos;
   }

   public Vec3 getCameraPosition() {
      return this.cameraPos;
   }

   public void setCameraPosition(Vec3 cameraPos) {
      this.cameraPos = cameraPos;
   }
}
