package xyz.pixelatedw.mineminenomi.packets.client.crew;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.events.JollyRogerEvent;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class CUpdateJollyRogerPacket {
   private JollyRoger jollyRoger = new JollyRoger();

   public CUpdateJollyRogerPacket() {
   }

   public CUpdateJollyRogerPacket(JollyRoger jollyRoger) {
      this.jollyRoger = jollyRoger;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130079_(this.jollyRoger.write());
   }

   public static CUpdateJollyRogerPacket decode(FriendlyByteBuf buffer) {
      CUpdateJollyRogerPacket msg = new CUpdateJollyRogerPacket();
      msg.jollyRoger.read(buffer.m_130260_());
      return msg;
   }

   public static void handle(CUpdateJollyRogerPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            UUID uuid = player.m_20148_();
            FactionsWorldData worldData = FactionsWorldData.get();
            Crew crew = worldData.getCrewWithCaptain(uuid);
            if (crew != null) {
               JollyRogerEvent.Update event = new JollyRogerEvent.Update(message.jollyRoger, crew);
               MinecraftForge.EVENT_BUS.post(event);
               worldData.updateCrewJollyRoger(player, crew, message.jollyRoger);
            }

         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
