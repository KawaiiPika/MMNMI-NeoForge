package xyz.pixelatedw.mineminenomi.packets.client.crew;

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
import xyz.pixelatedw.mineminenomi.api.ui.SimpleMessageScreenEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class CCreateCrewPacket {
   private String name;

   public CCreateCrewPacket() {
   }

   public CCreateCrewPacket(String name) {
      this.name = name;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.name.length());
      buffer.m_130070_(this.name);
   }

   public static CCreateCrewPacket decode(FriendlyByteBuf buffer) {
      CCreateCrewPacket msg = new CCreateCrewPacket();
      int len = buffer.readInt();
      msg.name = buffer.m_130136_(len);
      return msg;
   }

   public static void handle(CCreateCrewPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (props != null) {
               FactionsWorldData worldProps = FactionsWorldData.get();
               boolean hasSakeBottle = !player.m_21205_().m_41619_() && player.m_21205_().m_41720_().equals(ModItems.SAKE_BOTTLE.get());
               boolean isAlreadyInCrew = worldProps.getCrewWithMember(player.m_20148_()) != null;
               SimpleMessageScreenEvent screenEvent = new SimpleMessageScreenEvent();
               screenEvent.setTimeVisible(40);
               if (hasSakeBottle && props.isPirate()) {
                  if (isAlreadyInCrew) {
                     screenEvent.setMessage(ModI18n.CREW_MESSAGE_ALREADY_IN_CREW);
                     screenEvent.sendEvent(player);
                  } else {
                     Crew crewCheck = worldProps.getCrewByName(message.name);
                     if (crewCheck != null) {
                        screenEvent.setMessage(ModI18n.CREW_MESSAGE_NAME_ALREADY_EXISTS);
                        screenEvent.sendEvent(player);
                     } else {
                        Crew crew = new Crew(message.name, player);
                        CrewEvent.Create event = new CrewEvent.Create(player, crew);
                        if (!MinecraftForge.EVENT_BUS.post(event)) {
                           worldProps.addCrew(crew);
                           crew.create(player.m_9236_());
                           screenEvent.sendEvent(player);
                           if (ServerConfig.isCrewWorldMessageEnabled()) {
                              Component newCrewMsg = Component.m_237110_(ModI18n.CREW_MESSAGE_NEW_CREW, new Object[]{message.name});

                              for(Player target : player.m_9236_().m_6907_()) {
                                 target.m_213846_(newCrewMsg);
                              }
                           }
                        }

                     }
                  }
               } else {
                  screenEvent.setMessage(ModI18n.INFO_GENERIC_ERROR);
                  screenEvent.sendEvent(player);
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
