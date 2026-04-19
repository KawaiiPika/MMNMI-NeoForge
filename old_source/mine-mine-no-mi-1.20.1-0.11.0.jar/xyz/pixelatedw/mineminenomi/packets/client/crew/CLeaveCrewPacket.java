package xyz.pixelatedw.mineminenomi.packets.client.crew;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class CLeaveCrewPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static CLeaveCrewPacket decode(FriendlyByteBuf buffer) {
      CLeaveCrewPacket msg = new CLeaveCrewPacket();
      return msg;
   }

   public static void handle(CLeaveCrewPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            UUID uuid = player.m_20148_();
            FactionsWorldData worldData = FactionsWorldData.get();
            Crew crew = worldData.getCrewWithMember(uuid);
            if (crew != null) {
               CrewEvent.Leave event = new CrewEvent.Leave(player, crew);
               if (!MinecraftForge.EVENT_BUS.post(event)) {
                  boolean captainChange = false;
                  boolean isLastMemeber = crew.getMembers().size() == 1;
                  if (!isLastMemeber) {
                     FactionHelper.sendMessageToCrew(player.m_9236_(), crew, Component.m_237110_(ModI18n.CREW_MESSAGE_LEFT, new Object[]{player.m_7755_().getString()}));
                     if (crew.getCaptain().getUUID().equals(uuid)) {
                        captainChange = true;
                     }
                  }

                  worldData.removeCrewMember(player.m_9236_(), crew, uuid);
                  if (captainChange && crew.getCaptain() != null) {
                     FactionHelper.sendMessageToCrew(player.m_9236_(), crew, Component.m_237110_(ModI18n.CREW_MESSAGE_NEW_CAPTAIN, new Object[]{crew.getCaptain().getUsername()}));
                  }
               }
            }

         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
