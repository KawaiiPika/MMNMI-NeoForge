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

public class SUnpinCameraPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static SUnpinCameraPacket decode(FriendlyByteBuf buffer) {
      SUnpinCameraPacket msg = new SUnpinCameraPacket();
      return msg;
   }

   public static void handle(SUnpinCameraPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUnpinCameraPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUnpinCameraPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         ICameraLock props = (ICameraLock)CameraLockCapability.get(player).orElse((Object)null);
         if (props != null) {
            props.unpinCamera();
         }
      }
   }
}
