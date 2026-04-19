package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilityTreeScreen;

public class SOpenAbilityTreeScreenPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static SOpenAbilityTreeScreenPacket decode(FriendlyByteBuf buffer) {
      return new SOpenAbilityTreeScreenPacket();
   }

   public static void handle(SOpenAbilityTreeScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenAbilityTreeScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenAbilityTreeScreenPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         AbstractClientPlayer player = mc.f_91074_;
         if (player != null) {
            Minecraft.m_91087_().m_91152_(new AbilityTreeScreen());
         }
      }
   }
}
