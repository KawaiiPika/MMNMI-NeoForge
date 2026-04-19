package xyz.pixelatedw.mineminenomi.packets.client.entity.interaction;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.poi.NTEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.POIEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.BarkeeperEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;

public class CBarkeeperListRumorsPacket {
   private int entityId;

   public CBarkeeperListRumorsPacket() {
   }

   public CBarkeeperListRumorsPacket(int entityId) {
      this.entityId = entityId;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
   }

   public static CBarkeeperListRumorsPacket decode(FriendlyByteBuf buffer) {
      CBarkeeperListRumorsPacket msg = new CBarkeeperListRumorsPacket();
      msg.entityId = buffer.readInt();
      return msg;
   }

   public static void handle(CBarkeeperListRumorsPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            Entity entity = ((NetworkEvent.Context)ctx.get()).getSender().m_9236_().m_6815_(message.entityId);
            if (entity instanceof BarkeeperEntity barkeeper) {
               ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
               IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
               if (props != null) {
                  int payment = 1000;
                  if (props.getBelly() < (long)payment) {
                     ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNoBellyMessage()), player);
                  } else {
                     boolean isMarine = props.isMarine() || props.isBountyHunter();
                     Component response = null;
                     if (isMarine) {
                        response = findTargetEvent(player, barkeeper, true);
                     } else {
                        int kind = player.m_217043_().m_188503_(2);
                        if (kind == 0) {
                           findTargetEvent(player, barkeeper, false);
                        } else if (kind == 1) {
                           findCaravanEvent(player, barkeeper);
                        }
                     }

                     if (response == null) {
                        ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNothingNewMessage()), player);
                     } else {
                        int currentHash = response.hashCode();
                        if (barkeeper.previousHash != 0 && currentHash == barkeeper.previousHash) {
                           return;
                        }

                        barkeeper.previousHash = currentHash;
                        ModNetwork.sendTo(SDialogueResponsePacket.setMessage(response), player);
                        WyHelper.sendMessage(player, response, true);
                     }

                     props.alterBelly((long)(-payment), StatChangeSource.STORE);
                     ModNetwork.sendTo(new SSyncEntityStatsPacket(player, props), player);
                  }
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   private static Component findCaravanEvent(Player player, LivingEntity entity) {
      EventsWorldData eventsWorldData = EventsWorldData.get();
      Optional<POIEventTarget> event = eventsWorldData.getCaravanPOIs().stream().map((ev) -> ImmutablePair.of(ev, ev.getPosition().m_82554_(entity.m_20182_()))).sorted((p1, p2) -> (Double)p2.right - (Double)p1.right > (double)0.0F ? 1 : -1).map((pair) -> (POIEventTarget)pair.left).findFirst();
      return !event.isPresent() ? null : ModI18nInteractions.getRandomCaravanRumorMessage(((POIEventTarget)event.get()).getPosition());
   }

   private static Component findTargetEvent(Player player, LivingEntity entity, boolean isMarine) {
      EventsWorldData eventsWorldData = EventsWorldData.get();
      Optional<NTEventTarget> event = eventsWorldData.getNotoriousTargets().stream().filter((ev) -> !isMarine || !ev.getTrackedNPC().getFaction().equals(ModFactions.MARINE.get())).map((ev) -> ImmutablePair.of(ev, ev.getPosition().m_82554_(entity.m_20182_()))).sorted((p1, p2) -> (Double)p2.right - (Double)p1.right > (double)0.0F ? 1 : -1).map((pair) -> (NTEventTarget)pair.left).findFirst();
      if (!event.isPresent()) {
         return null;
      } else {
         TrackedNPC npc = ((NTEventTarget)event.get()).getTrackedNPC();
         String npcName = npc.createTrackedMob(entity.m_9236_()).m_5446_().getString();
         Component message = null;
         if (npc.getFaction().equals(ModFactions.PIRATE.get())) {
            message = ModI18nInteractions.getRandomPirateRumorMessage(((NTEventTarget)event.get()).getPosition(), npcName);
         } else if (((NTEventTarget)event.get()).getTrackedNPC().getFaction().equals(ModFactions.MARINE.get())) {
            message = ModI18nInteractions.getRandomMarineRumorMessage(((NTEventTarget)event.get()).getPosition(), npcName);
         }

         return message;
      }
   }
}
