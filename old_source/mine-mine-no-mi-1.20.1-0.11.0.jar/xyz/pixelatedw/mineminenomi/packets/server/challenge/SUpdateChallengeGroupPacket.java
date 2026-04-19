package xyz.pixelatedw.mineminenomi.packets.server.challenge;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;

public class SUpdateChallengeGroupPacket {
   private UUID memberId;
   private int groupSlot;

   public SUpdateChallengeGroupPacket() {
   }

   public SUpdateChallengeGroupPacket(UUID memberId, int groupSlot) {
      this.memberId = memberId;
      this.groupSlot = groupSlot;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130077_(this.memberId);
      buffer.writeInt(this.groupSlot);
   }

   public static SUpdateChallengeGroupPacket decode(FriendlyByteBuf buffer) {
      SUpdateChallengeGroupPacket msg = new SUpdateChallengeGroupPacket();
      msg.memberId = buffer.m_130259_();
      msg.groupSlot = buffer.readInt();
      return msg;
   }

   public static void handle(SUpdateChallengeGroupPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateChallengeGroupPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateChallengeGroupPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         if (player != null) {
            Player member = mc.f_91073_.m_46003_(message.memberId);
            if (member != null) {
               if (mc.f_91080_ instanceof ChallengesScreen) {
                  ChallengesScreen screen = (ChallengesScreen)mc.f_91080_;
                  screen.setGroupMember(message.groupSlot, member);
               }
            }
         }
      }
   }
}
