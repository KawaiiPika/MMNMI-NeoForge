package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.cameralock.CameraLockCapability;
import xyz.pixelatedw.mineminenomi.data.entity.cameralock.ICameraLock;

public class SPinCameraPacket {
   private boolean clampYaw = false;
   private boolean clampPitch = false;
   private float initialYaw;
   private float initialPitch;
   private float maxYaw;
   private float maxPitch;
   private int ticks;

   public static SPinCameraPacket pinFixed() {
      SPinCameraPacket packet = new SPinCameraPacket();
      return packet;
   }

   public static SPinCameraPacket pinClampedYaw(float initialYaw, float maxYaw) {
      SPinCameraPacket packet = new SPinCameraPacket();
      packet.initialYaw = initialYaw;
      packet.maxYaw = maxYaw;
      packet.clampYaw = true;
      return packet;
   }

   public static SPinCameraPacket pinClampedPitch(float initialPitch, float maxPitch) {
      SPinCameraPacket packet = new SPinCameraPacket();
      packet.initialPitch = initialPitch;
      packet.maxPitch = maxPitch;
      packet.clampPitch = true;
      return packet;
   }

   public static SPinCameraPacket pinClampedYawAndPitch(float initialYaw, float maxYaw, float initialPitch, float maxPitch) {
      SPinCameraPacket packet = new SPinCameraPacket();
      packet.initialYaw = initialYaw;
      packet.initialPitch = initialPitch;
      packet.maxYaw = maxYaw;
      packet.maxPitch = maxPitch;
      packet.clampYaw = true;
      packet.clampPitch = true;
      return packet;
   }

   public SPinCameraPacket setTimed(int ticks) {
      this.ticks = ticks;
      return this;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.clampYaw);
      buffer.writeBoolean(this.clampPitch);
      buffer.writeFloat(this.initialYaw);
      buffer.writeFloat(this.initialPitch);
      buffer.writeFloat(this.maxYaw);
      buffer.writeFloat(this.maxPitch);
      buffer.writeInt(this.ticks);
   }

   public static SPinCameraPacket decode(FriendlyByteBuf buffer) {
      SPinCameraPacket msg = new SPinCameraPacket();
      msg.clampYaw = buffer.readBoolean();
      msg.clampPitch = buffer.readBoolean();
      msg.initialYaw = buffer.readFloat();
      msg.initialPitch = buffer.readFloat();
      msg.maxYaw = buffer.readFloat();
      msg.maxPitch = buffer.readFloat();
      msg.ticks = buffer.readInt();
      return msg;
   }

   public static void handle(SPinCameraPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SPinCameraPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SPinCameraPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         ICameraLock props = (ICameraLock)CameraLockCapability.get(player).orElse((Object)null);
         if (props != null) {
            props.pinCamera(player);
            if (message.clampYaw) {
               player.m_146922_(message.initialYaw);
               props.clampCameraYaw(player, message.initialYaw, message.maxYaw);
            }

            if (message.clampPitch) {
               player.m_146926_(message.initialPitch);
               props.clampCameraPitch(player, message.initialPitch, message.maxPitch);
            }

            props.setCameraPinTimer(message.ticks);
         }
      }
   }
}
