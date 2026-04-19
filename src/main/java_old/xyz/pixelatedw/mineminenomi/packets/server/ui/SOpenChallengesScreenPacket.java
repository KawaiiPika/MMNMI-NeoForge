package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;

public class SOpenChallengesScreenPacket {
   private List<Challenge> challenges;
   private List<ChallengeInvitation> invites;

   public SOpenChallengesScreenPacket() {
   }

   public SOpenChallengesScreenPacket(List<Challenge> challenges, List<ChallengeInvitation> invites) {
      this.challenges = challenges;
      this.invites = invites;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.challenges.size());

      for(Challenge ch : this.challenges) {
         buffer.m_130085_(ch.getCore().getRegistryKey());
         buffer.m_130079_(ch.save(new CompoundTag()));
      }

      buffer.writeInt(this.invites.size());

      for(ChallengeInvitation invite : this.invites) {
         buffer.m_130077_(invite.getSenderId());
         buffer.m_130085_(invite.getChallenge().getRegistryKey());
         buffer.writeLong(invite.getSendTime());
         buffer.writeInt(invite.getGroupSlot());
      }

   }

   public static SOpenChallengesScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenChallengesScreenPacket msg = new SOpenChallengesScreenPacket();
      msg.challenges = new ArrayList();
      int size = buffer.readInt();

      for(int i = 0; i < size; ++i) {
         ChallengeCore<?> core = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(buffer.m_130281_());
         if (core != null) {
            Challenge challenge = core.createChallenge();
            challenge.load(buffer.m_130260_());
            msg.challenges.add(challenge);
         }
      }

      msg.invites = new ArrayList();
      size = buffer.readInt();

      for(int i = 0; i < size; ++i) {
         UUID uuid = buffer.m_130259_();
         ChallengeCore<?> challenge = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(buffer.m_130281_());
         long gameTime = buffer.readLong();
         int groupSlot = buffer.readInt();
         ChallengeInvitation invite = new ChallengeInvitation(uuid, challenge, gameTime, groupSlot);
         msg.invites.add(invite);
      }

      return msg;
   }

   public static void handle(SOpenChallengesScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenChallengesScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenChallengesScreenPacket message) {
         Minecraft.m_91087_().m_91152_(new ChallengesScreen(message.challenges, message.invites));
      }
   }
}
