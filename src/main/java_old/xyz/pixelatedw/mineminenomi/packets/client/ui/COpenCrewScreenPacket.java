package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCrewScreenPacket;

public class COpenCrewScreenPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static COpenCrewScreenPacket decode(FriendlyByteBuf buffer) {
      COpenCrewScreenPacket msg = new COpenCrewScreenPacket();
      return msg;
   }

   public static void handle(COpenCrewScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            FactionsWorldData worldData = FactionsWorldData.get();
            Crew crew = worldData.getCrewWithMember(player.m_20148_());
            if (crew != null) {
               ModNetwork.sendTo(new SOpenCrewScreenPacket(crew), player);
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
