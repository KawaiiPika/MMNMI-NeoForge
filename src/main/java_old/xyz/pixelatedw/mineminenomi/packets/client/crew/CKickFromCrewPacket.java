package xyz.pixelatedw.mineminenomi.packets.client.crew;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class CKickFromCrewPacket {
   private UUID uuid;

   public CKickFromCrewPacket() {
   }

   public CKickFromCrewPacket(UUID uuid) {
      this.uuid = uuid;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130077_(this.uuid);
   }

   public static CKickFromCrewPacket decode(FriendlyByteBuf buffer) {
      CKickFromCrewPacket msg = new CKickFromCrewPacket();
      msg.uuid = buffer.m_130259_();
      return msg;
   }

   public static void handle(CKickFromCrewPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer sender = ((NetworkEvent.Context)ctx.get()).getSender();
            UUID uuid = message.uuid;
            FactionsWorldData worldData = FactionsWorldData.get();
            Crew crew = worldData.getCrewWithCaptain(sender.m_20148_());
            Player memberPlayer = sender.m_9236_().m_46003_(uuid);
            if (memberPlayer != null && crew != null && crew.hasMember(uuid) && !crew.getCaptain().getUUID().equals(uuid)) {
               CrewEvent.Kick event = new CrewEvent.Kick(memberPlayer, crew);
               if (!MinecraftForge.EVENT_BUS.post(event)) {
                  FactionHelper.sendMessageToCrew(sender.m_9236_(), crew, Component.m_237110_(ModI18n.CREW_MESSAGE_KICKED, new Object[]{crew.getMember(uuid).getUsername()}));
                  worldData.removeCrewMember(sender.m_9236_(), crew, uuid);
               }
            }

         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
