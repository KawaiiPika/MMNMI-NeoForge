package xyz.pixelatedw.mineminenomi.packets.client.challenge;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.packets.server.challenge.SUpdateChallengeGroupPacket;

public class CAcceptChallengeInvitationPacket {
   private UUID id;
   private int groupSlot;

   public CAcceptChallengeInvitationPacket() {
   }

   public CAcceptChallengeInvitationPacket(ChallengeInvitation invite) {
      this.id = invite.getSenderId();
      this.groupSlot = invite.getGroupSlot();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130077_(this.id);
      buffer.writeInt(this.groupSlot);
   }

   public static CAcceptChallengeInvitationPacket decode(FriendlyByteBuf buffer) {
      CAcceptChallengeInvitationPacket msg = new CAcceptChallengeInvitationPacket();
      msg.id = buffer.m_130259_();
      msg.groupSlot = buffer.readInt();
      return msg;
   }

   public static void handle(CAcceptChallengeInvitationPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            if (ServerConfig.isChallengesEnabled()) {
               ServerPlayer sender = ((NetworkEvent.Context)ctx.get()).getSender();
               ServerLevel world = sender.m_284548_();
               IChallengeData props = (IChallengeData)ChallengeCapability.get(sender).orElse((Object)null);
               if (props != null && !props.isInGroup() && !WyHelper.isInCombat(sender)) {
                  Optional<ChallengeInvitation> inviteOptional = props.getInvitationFrom(message.id);
                  if (inviteOptional.isPresent()) {
                     ChallengeInvitation invite = (ChallengeInvitation)inviteOptional.get();
                     Player invitationSender = world.m_46003_(invite.getSenderId());
                     if (invitationSender != null && invitationSender.m_6084_()) {
                        if (!invite.isExpired(world)) {
                           ChallengesWorldData worldData = ChallengesWorldData.get();
                           if (worldData.getInProgressChallengeFor((LivingEntity)sender) == null) {
                              IChallengeData senderChallengeProps = (IChallengeData)ChallengeCapability.get(invitationSender).orElse((Object)null);
                              if (senderChallengeProps != null) {
                                 Component joinMessage = Component.m_237110_(ModI18nChallenges.MESSAGE_GROUP_JOIN, new Object[]{sender.m_36316_().getName()});
                                 WyHelper.sendMessage(invitationSender, joinMessage, true);
                                 UnmodifiableIterator var11 = senderChallengeProps.getGroupMembersIds().iterator();

                                 while(var11.hasNext()) {
                                    UUID memberId = (UUID)var11.next();
                                    Player member = world.m_46003_(memberId);
                                    WyHelper.sendMessage(member, joinMessage, true);
                                 }

                                 senderChallengeProps.addGroupMember(sender);
                                 props.setInGroup(invitationSender.m_20148_());
                                 props.removeInvitationFrom(invitationSender);
                                 ModNetwork.sendTo(new SUpdateChallengeGroupPacket(sender.m_20148_(), message.groupSlot), invitationSender);
                              }
                           }
                        }
                     }
                  }
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
