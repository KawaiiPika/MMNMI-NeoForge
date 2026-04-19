package xyz.pixelatedw.mineminenomi.data.entity.cameralock;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CameraLockBase implements ICameraLock {
   private float cameraYaw = 0.0F;
   private float cameraPitch = 0.0F;
   private float[] cameraRotations;
   private boolean hasCameraPinned = false;
   private float initialYaw;
   private float maxYaw;
   private boolean clampCameraYaw = false;
   private float initialPitch;
   private float maxPitch;
   private boolean clampCameraPitch = false;
   private int cameraPinTicks = 0;

   @OnlyIn(Dist.CLIENT)
   public void pinCamera(Player player) {
      this.cameraYaw = player.m_146908_();
      this.cameraPitch = player.m_146909_();
      this.cameraRotations = new float[]{this.cameraPitch, this.cameraYaw};
      this.hasCameraPinned = true;
      this.initialYaw = 0.0F;
      this.maxYaw = 0.0F;
      this.initialPitch = 0.0F;
      this.maxPitch = 0.0F;
      this.clampCameraYaw = false;
      this.clampCameraPitch = false;
      this.cameraPinTicks = 0;
   }

   @OnlyIn(Dist.CLIENT)
   public void clampCameraYaw(Player player, float initialYaw, float maxYaw) {
      this.initialYaw = initialYaw;
      this.maxYaw = maxYaw;
      this.clampCameraYaw = true;
   }

   @OnlyIn(Dist.CLIENT)
   public void clampCameraPitch(Player player, float initialPitch, float maxPitch) {
      this.initialPitch = initialPitch;
      this.maxPitch = maxPitch;
      this.clampCameraPitch = true;
   }

   public void unpinCamera() {
      this.hasCameraPinned = false;
   }

   public boolean hasCameraPinned() {
      return this.hasCameraPinned;
   }

   public float[] getCameraRotations() {
      return this.cameraRotations;
   }

   public float getCameraInitialYaw() {
      return this.initialYaw;
   }

   public float getCameraMaxYaw() {
      return this.maxYaw;
   }

   public boolean hasCameraYawClamped() {
      return this.clampCameraYaw;
   }

   public float getCameraInitialPitch() {
      return this.initialPitch;
   }

   public float getCameraMaxPitch() {
      return this.maxPitch;
   }

   public boolean hasCameraPitchClamped() {
      return this.clampCameraPitch;
   }

   public void setCameraPinTimer(int ticks) {
      this.cameraPinTicks = ticks;
   }

   public void tickCameraPin() {
      if (this.cameraPinTicks > 0 && this.cameraPinTicks-- <= 0) {
         this.unpinCamera();
      }

   }

   public CompoundTag serializeNBT() {
      return new CompoundTag();
   }

   public void deserializeNBT(CompoundTag nbt) {
   }
}
