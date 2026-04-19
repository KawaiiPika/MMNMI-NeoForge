package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.data.entity.cameralock.CameraLockCapability;
import xyz.pixelatedw.mineminenomi.data.entity.cameralock.ICameraLock;

public class CameraHandler {
   public static float[] cameraLock(Minecraft mc, Player player) {
      ICameraLock props = (ICameraLock)CameraLockCapability.get(player).orElse((Object)null);
      if (props != null && props.hasCameraPinned()) {
         props.tickCameraPin();
         float pitch = props.getCameraRotations()[0];
         float yaw = props.getCameraRotations()[1];
         if (props.hasCameraPitchClamped()) {
            pitch = Mth.m_14036_(player.m_146909_() % 360.0F, props.getCameraInitialPitch() - props.getCameraMaxPitch(), props.getCameraInitialPitch() + props.getCameraMaxPitch());
         }

         if (props.hasCameraYawClamped()) {
            yaw = Mth.m_14036_(player.m_146908_() % 360.0F, props.getCameraInitialYaw() - props.getCameraMaxYaw(), props.getCameraInitialYaw() + props.getCameraMaxYaw());
         }

         player.m_146922_(yaw);
         player.f_19859_ = yaw;
         player.m_146926_(pitch);
         player.f_19860_ = pitch;
         if (mc.f_91066_.m_92176_() == CameraType.FIRST_PERSON) {
            return new float[]{pitch, yaw};
         }
      }

      return null;
   }
}
