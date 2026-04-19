package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ui.screens.CreateCrewScreen;

public class SOpenCreateCrewScreenPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static SOpenCreateCrewScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenCreateCrewScreenPacket msg = new SOpenCreateCrewScreenPacket();
      return msg;
   }

   public static void handle(SOpenCreateCrewScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenCreateCrewScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenCreateCrewScreenPacket message) {
         Minecraft.m_91087_().m_91152_(new CreateCrewScreen());
      }
   }
}
