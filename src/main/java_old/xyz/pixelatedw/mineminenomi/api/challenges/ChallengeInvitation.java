package xyz.pixelatedw.mineminenomi.api.challenges;

import java.util.UUID;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ChallengeInvitation {
   private static final int INVITE_TIME = 2400;
   private UUID senderId;
   private ChallengeCore<?> challenge;
   private long gameTime;
   private int groupSlot;

   public ChallengeInvitation(UUID senderId, ChallengeCore<?> challenge, long gameTime, int groupSlot) {
      this.senderId = senderId;
      this.challenge = challenge;
      this.gameTime = gameTime;
      this.groupSlot = Mth.m_14045_(groupSlot, 0, 2);
   }

   public boolean isExpired(Level world) {
      return this.gameTime + 2400L < world.m_46467_();
   }

   public @Nullable Player getSender(Level world) {
      return world.m_46003_(this.senderId);
   }

   public UUID getSenderId() {
      return this.senderId;
   }

   public ChallengeCore<?> getChallenge() {
      return this.challenge;
   }

   public long getSendTime() {
      return this.gameTime;
   }

   public int getGroupSlot() {
      return this.groupSlot;
   }
}
