package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class SFlightValuePacket {
   private boolean value;

   public SFlightValuePacket() {
   }

   public SFlightValuePacket(boolean value) {
      this.value = value;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.value);
   }

   public static SFlightValuePacket decode(FriendlyByteBuf buffer) {
      SFlightValuePacket msg = new SFlightValuePacket();
      msg.value = buffer.readBoolean();
      return msg;
   }

   public static void handle(SFlightValuePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SFlightValuePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SFlightValuePacket message) {
         LocalPlayer player = Minecraft.m_91087_().f_91074_;
         player.m_150110_().f_35936_ = message.value;
      }
   }
}
