package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;

public class SSetServerMaxBarsPacket {
   private int bars;

   public SSetServerMaxBarsPacket() {
   }

   public SSetServerMaxBarsPacket(int bars) {
      this.bars = bars;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.bars);
   }

   public static SSetServerMaxBarsPacket decode(FriendlyByteBuf buffer) {
      SSetServerMaxBarsPacket msg = new SSetServerMaxBarsPacket();
      msg.bars = buffer.readInt();
      return msg;
   }

   public static void handle(SSetServerMaxBarsPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSetServerMaxBarsPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSetServerMaxBarsPacket message) {
         ModKeybindings.serverMaxBars = message.bars;
      }
   }
}
