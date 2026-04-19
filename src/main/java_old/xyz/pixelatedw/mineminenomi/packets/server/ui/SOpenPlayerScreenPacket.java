package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ui.screens.PlayerStatsScreen;

public class SOpenPlayerScreenPacket {
   private double doriki;
   private boolean hasQuests;
   private int questAmount;
   private boolean hasChallenges;
   private int challengeAmount;
   private boolean isInCombat;
   private boolean isInChallengeDimension;
   private int invites;
   private boolean hasCrew;

   private SOpenPlayerScreenPacket() {
   }

   public SOpenPlayerScreenPacket(double doriki, boolean hasQuests, int questAmount, boolean hasChallenges, int challengeAmount, boolean isInCombat, boolean isInChallengeDimension, int invites, boolean hasCrew) {
      this.doriki = doriki;
      this.hasQuests = hasQuests;
      this.questAmount = questAmount;
      this.hasChallenges = hasChallenges;
      this.challengeAmount = challengeAmount;
      this.isInCombat = isInCombat;
      this.isInChallengeDimension = isInChallengeDimension;
      this.invites = invites;
      this.hasCrew = hasCrew;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeDouble(this.doriki);
      buffer.writeBoolean(this.hasQuests);
      buffer.writeInt(this.questAmount);
      buffer.writeBoolean(this.hasChallenges);
      buffer.writeInt(this.challengeAmount);
      buffer.writeBoolean(this.isInCombat);
      buffer.writeBoolean(this.isInChallengeDimension);
      buffer.writeInt(this.invites);
      buffer.writeBoolean(this.hasCrew);
   }

   public static SOpenPlayerScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenPlayerScreenPacket msg = new SOpenPlayerScreenPacket();
      msg.doriki = buffer.readDouble();
      msg.hasQuests = buffer.readBoolean();
      msg.questAmount = buffer.readInt();
      msg.hasChallenges = buffer.readBoolean();
      msg.challengeAmount = buffer.readInt();
      msg.isInCombat = buffer.readBoolean();
      msg.isInChallengeDimension = buffer.readBoolean();
      msg.invites = buffer.readInt();
      msg.hasCrew = buffer.readBoolean();
      return msg;
   }

   public static void handle(SOpenPlayerScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenPlayerScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenPlayerScreenPacket message) {
         PlayerStatsScreen.open(message.doriki, message.hasQuests, message.questAmount, message.hasChallenges, message.challengeAmount, message.isInCombat, message.isInChallengeDimension, message.invites, message.hasCrew);
      }
   }
}
