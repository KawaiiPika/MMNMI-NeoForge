package xyz.pixelatedw.mineminenomi.packets.client.challenge;

import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.ui.events.ChallengesScreenEvent;

public class CFetchPossibleInvitesPacket {
   private static final TargetPredicate TARGET_PICKER = (new TargetPredicate()).testFriendlyFaction();
   private int slot;

   public CFetchPossibleInvitesPacket() {
   }

   public CFetchPossibleInvitesPacket(int slot) {
      this.slot = slot;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.slot);
   }

   public static CFetchPossibleInvitesPacket decode(FriendlyByteBuf buffer) {
      CFetchPossibleInvitesPacket msg = new CFetchPossibleInvitesPacket();
      msg.slot = buffer.readInt();
      return msg;
   }

   public static void handle(CFetchPossibleInvitesPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            if (ServerConfig.isChallengesEnabled()) {
               ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
               IChallengeData props = (IChallengeData)ChallengeCapability.get(player).orElse((Object)null);
               if (props != null) {
                  List<LivingEntity> nearbyGroupMembers = TargetHelper.<LivingEntity>getEntitiesInArea(player.m_9236_(), player, (double)20.0F, TARGET_PICKER, LivingEntity.class);
                  nearbyGroupMembers.removeIf((entity) -> !(entity instanceof Player));
                  nearbyGroupMembers.removeIf((entity) -> {
                     UnmodifiableIterator var2 = props.getGroupMembersIds().iterator();

                     while(var2.hasNext()) {
                        UUID targetId = (UUID)var2.next();
                        if (targetId != null && targetId.equals(entity.m_20148_())) {
                           return true;
                        }
                     }

                     return false;
                  });
                  List<Integer> ids = (List)nearbyGroupMembers.stream().map((e) -> e.m_19879_()).collect(Collectors.toList());
                  ChallengesScreenEvent event = new ChallengesScreenEvent();
                  event.setPossibleInvites(message.slot, ids);
                  event.sendEvent(player);
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
