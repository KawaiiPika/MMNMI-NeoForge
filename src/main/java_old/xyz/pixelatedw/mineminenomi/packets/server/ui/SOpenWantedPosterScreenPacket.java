package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;
import xyz.pixelatedw.mineminenomi.ui.screens.WantedPosterScreen;

public class SOpenWantedPosterScreenPacket {
   private CompoundTag wantedData;

   public SOpenWantedPosterScreenPacket() {
   }

   public SOpenWantedPosterScreenPacket(WantedPosterData wantedData) {
      this.wantedData = wantedData.write();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130079_(this.wantedData);
   }

   public static SOpenWantedPosterScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenWantedPosterScreenPacket msg = new SOpenWantedPosterScreenPacket();
      msg.wantedData = buffer.m_130261_();
      return msg;
   }

   public static void handle(SOpenWantedPosterScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenWantedPosterScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenWantedPosterScreenPacket message) {
         WantedPosterData wantedPosterData = WantedPosterData.from(message.wantedData);
         Minecraft.m_91087_().m_91152_(new WantedPosterScreen(wantedPosterData));
      }
   }
}
