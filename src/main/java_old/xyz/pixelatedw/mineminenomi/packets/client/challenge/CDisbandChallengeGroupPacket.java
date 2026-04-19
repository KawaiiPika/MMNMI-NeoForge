package xyz.pixelatedw.mineminenomi.packets.client.challenge;

import com.google.common.collect.UnmodifiableIterator;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;

public class CDisbandChallengeGroupPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static CDisbandChallengeGroupPacket decode(FriendlyByteBuf buffer) {
      CDisbandChallengeGroupPacket msg = new CDisbandChallengeGroupPacket();
      return msg;
   }

   public static void handle(CDisbandChallengeGroupPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            if (ServerConfig.isChallengesEnabled()) {
               ServerPlayer sender = ((NetworkEvent.Context)ctx.get()).getSender();
               ServerLevel world = sender.m_284548_();
               IChallengeData props = (IChallengeData)ChallengeCapability.get(sender).orElse((Object)null);
               if (props != null && !props.getGroupMembersIds().isEmpty() && !props.isInGroup()) {
                  WyHelper.sendMessage(sender, ModI18nChallenges.MESSAGE_GROUP_DISBAND, true);
                  props.setInGroup((UUID)null);
                  UnmodifiableIterator var4 = props.getGroupMembersIds().iterator();

                  while(var4.hasNext()) {
                     UUID id = (UUID)var4.next();
                     Player member = world.m_46003_(id);
                     if (member != null) {
                        WyHelper.sendMessage(member, ModI18nChallenges.MESSAGE_GROUP_DISBAND, true);
                        ChallengeCapability.get(member).ifPresent((memberProps) -> memberProps.setInGroup((UUID)null));
                        props.removeGroupMember(id);
                     }
                  }

               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
