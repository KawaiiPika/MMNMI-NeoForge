package xyz.pixelatedw.mineminenomi.data.entity.cameralock;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICameraLock extends INBTSerializable<CompoundTag> {
   void pinCamera(Player var1);

   void clampCameraYaw(Player var1, float var2, float var3);

   void clampCameraPitch(Player var1, float var2, float var3);

   void unpinCamera();

   boolean hasCameraPinned();

   float[] getCameraRotations();

   float getCameraInitialYaw();

   float getCameraMaxYaw();

   boolean hasCameraYawClamped();

   float getCameraInitialPitch();

   float getCameraMaxPitch();

   boolean hasCameraPitchClamped();

   void setCameraPinTimer(int var1);

   void tickCameraPin();
}
