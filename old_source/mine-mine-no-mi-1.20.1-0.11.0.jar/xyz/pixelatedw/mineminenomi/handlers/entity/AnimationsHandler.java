package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class AnimationsHandler {
   public static void toggleMountingAnimations(LivingEntity living, Entity mountedTarget, boolean isMounting) {
      if (isMounting) {
         if (mountedTarget instanceof StrikerEntity) {
            ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(living, ModAnimations.RIDE_STRIKER, -1, true), living);
         } else if (mountedTarget instanceof CannonEntity) {
            ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(living, ModAnimations.CANNON_HANDLING, -1, true), living);
         } else if (mountedTarget instanceof UnicycleEntity) {
            ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(living, ModAnimations.RIDE_UNICYCLE, -1, true), living);
         }
      } else if (mountedTarget instanceof StrikerEntity) {
         ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(living, ModAnimations.RIDE_STRIKER), living);
      } else if (mountedTarget instanceof CannonEntity) {
         ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(living, ModAnimations.CANNON_HANDLING), living);
      } else if (mountedTarget instanceof UnicycleEntity) {
         ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(living, ModAnimations.RIDE_UNICYCLE), living);
      }

   }
}
