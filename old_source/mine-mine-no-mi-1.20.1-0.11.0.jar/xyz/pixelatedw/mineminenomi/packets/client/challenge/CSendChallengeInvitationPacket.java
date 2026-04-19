package xyz.pixelatedw.mineminenomi.packets.client.challenge;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;

public class CSendChallengeInvitationPacket {
   private static final TargetPredicate TARGET_PICKER = (new TargetPredicate()).testFriendlyFaction();
   private UUID targetId;
   private ResourceLocation challengeId;
   private int slotId;

   public CSendChallengeInvitationPacket() {
   }

   public CSendChallengeInvitationPacket(Player target, ChallengeCore<?> challenge, int slotId) {
      this.targetId = target.m_20148_();
      this.challengeId = challenge.getRegistryKey();
      this.slotId = slotId;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130077_(this.targetId);
      buffer.m_130085_(this.challengeId);
      buffer.writeInt(this.slotId);
   }

   public static CSendChallengeInvitationPacket decode(FriendlyByteBuf buffer) {
      CSendChallengeInvitationPacket msg = new CSendChallengeInvitationPacket();
      msg.targetId = buffer.m_130259_();
      msg.challengeId = buffer.m_130281_();
      msg.slotId = buffer.readInt();
      return msg;
   }

   public static void handle(CSendChallengeInvitationPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            if (ServerConfig.isChallengesEnabled()) {
               ServerPlayer sender = ((NetworkEvent.Context)ctx.get()).getSender();
               ServerLevel world = sender.m_284548_();
               Player target = world.m_46003_(message.targetId);
               if (target != null && target.m_6084_()) {
                  if (TARGET_PICKER.test(sender, target)) {
                     if (!WyHelper.isInCombat(target)) {
                        ChallengeCore<?> challenge = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(message.challengeId);
                        if (challenge != null) {
                           IChallengeData props = (IChallengeData)ChallengeCapability.get(target).orElse((Object)null);
                           if (props != null && !props.isInGroup()) {
                              if (!props.hasInvitationFrom((Player)sender)) {
                                 ChallengeInvitation invite = new ChallengeInvitation(sender.m_20148_(), challenge, world.m_46467_(), message.slotId);
                                 props.addInvitation(invite);
                                 Component textMessage = Component.m_237110_(ModI18nChallenges.MESSAGE_INVITATION, new Object[]{sender.m_36316_().getName(), challenge.getLocalizedObjective().getString()});
                                 WyHelper.sendMessage(target, textMessage, true);
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
