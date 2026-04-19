package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.ui.screens.CrewDetailsScreen;

public class SOpenCrewScreenPacket {
   private Crew crew;

   public SOpenCrewScreenPacket() {
   }

   public SOpenCrewScreenPacket(Crew crew) {
      this.crew = crew;
   }

   public void encode(FriendlyByteBuf buffer) {
      CompoundTag crewData = this.crew.write();
      buffer.m_130079_(crewData);
   }

   public static SOpenCrewScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenCrewScreenPacket msg = new SOpenCrewScreenPacket();
      CompoundTag crewData = buffer.m_130260_();
      msg.crew = Crew.from(crewData);
      return msg;
   }

   public static void handle(SOpenCrewScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenCrewScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenCrewScreenPacket message) {
         Minecraft.m_91087_().m_91152_(new CrewDetailsScreen(message.crew));
      }
   }
}
