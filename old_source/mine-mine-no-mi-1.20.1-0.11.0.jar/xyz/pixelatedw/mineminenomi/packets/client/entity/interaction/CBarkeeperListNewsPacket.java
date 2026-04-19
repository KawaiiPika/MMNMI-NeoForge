package xyz.pixelatedw.mineminenomi.packets.client.entity.interaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.poi.NewsEntry;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;

public class CBarkeeperListNewsPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static CBarkeeperListNewsPacket decode(FriendlyByteBuf buffer) {
      CBarkeeperListNewsPacket msg = new CBarkeeperListNewsPacket();
      return msg;
   }

   public static void handle(CBarkeeperListNewsPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            NPCWorldData npcWorldData = NPCWorldData.get();
            EventsWorldData eventWorldData = EventsWorldData.get();
            List<TrackedNPC> trackedList = (List)eventWorldData.getNotoriousTargets().stream().map((target) -> target.getTrackedNPC()).collect(Collectors.toList());
            TrackedNPC tracked = null;
            if (trackedList != null && !trackedList.isEmpty()) {
               Collections.shuffle(trackedList);
               tracked = (TrackedNPC)trackedList.get(0);
            } else {
               trackedList = new ArrayList(npcWorldData.getTrackedMobs());
               Collections.shuffle(trackedList);
               tracked = (TrackedNPC)trackedList.get(0);
            }

            if (tracked != null) {
               NewsEntry newsEntry = tracked.getNewsEntry(player.m_9236_());
               if (newsEntry != null) {
                  if (newsEntry.getMessage() == null) {
                     ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNothingNewMessage()), player);
                     return;
                  }

                  ModNetwork.sendTo(SDialogueResponsePacket.setMessage(newsEntry.getMessage()), player);
               }
            }

         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
