package xyz.pixelatedw.mineminenomi.packets.server.quest;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class SFinishObjectivePacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static SFinishObjectivePacket decode(FriendlyByteBuf buffer) {
      SFinishObjectivePacket msg = new SFinishObjectivePacket();
      return msg;
   }

   public static void handle(SFinishObjectivePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SFinishObjectivePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SFinishObjectivePacket message) {
         Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_11871_, 1.0F));
      }
   }
}
