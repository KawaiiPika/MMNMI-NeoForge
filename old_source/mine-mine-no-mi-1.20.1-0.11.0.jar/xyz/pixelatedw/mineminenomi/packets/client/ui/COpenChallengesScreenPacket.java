package xyz.pixelatedw.mineminenomi.packets.client.ui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenChallengesScreenPacket;

public class COpenChallengesScreenPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static COpenChallengesScreenPacket decode(FriendlyByteBuf buffer) {
      COpenChallengesScreenPacket msg = new COpenChallengesScreenPacket();
      return msg;
   }

   public static void handle(COpenChallengesScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            Optional<IChallengeData> challengesprops = ChallengeCapability.get(player);
            List<Challenge> challenges = (List)challengesprops.map((props) -> props.getChallenges()).orElse(Lists.newArrayList());
            List<ChallengeInvitation> invites = (List)challengesprops.map((props) -> props.getInvitations()).orElse(ImmutableList.of());
            ModNetwork.sendTo(new SOpenChallengesScreenPacket(challenges, invites), player);
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
