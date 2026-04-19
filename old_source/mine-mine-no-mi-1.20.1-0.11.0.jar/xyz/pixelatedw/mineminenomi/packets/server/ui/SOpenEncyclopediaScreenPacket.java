package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ui.screens.EncyclopediaScreen;

public class SOpenEncyclopediaScreenPacket {
   private ItemStack book;

   public SOpenEncyclopediaScreenPacket() {
   }

   public SOpenEncyclopediaScreenPacket(ItemStack book) {
      this.book = book;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130055_(this.book);
   }

   public static SOpenEncyclopediaScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenEncyclopediaScreenPacket msg = new SOpenEncyclopediaScreenPacket();
      msg.book = buffer.m_130267_();
      return msg;
   }

   public static void handle(SOpenEncyclopediaScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenEncyclopediaScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenEncyclopediaScreenPacket message) {
         EncyclopediaScreen.open(message.book);
      }
   }
}
