package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.challenges.IChallengeBoss;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SUpdateOPBossEventPacket;

public class ServerOPBossEvent extends ServerBossEvent {
   private static final BossEvent.BossBarColor[] COLOR_ORDER;
   private int totalBars = -1;
   private int activeBars = -1;
   private final int type = 2;

   public ServerOPBossEvent(Component name) {
      super(name, BossBarColor.RED, BossBarOverlay.NOTCHED_10);
   }

   public void tick(LivingEntity boss) {
      if (boss instanceof IChallengeBoss challengeBoss) {
         if (challengeBoss.getChallengeInfo().isDifficultyHard()) {
            Style style = this.m_18861_().m_7383_().m_131157_(ChatFormatting.DARK_RED).m_131136_(true);
            this.m_6456_(this.m_18861_().m_6881_().m_6270_(style));
         }
      }

      if (this.type == 0) {
         this.m_5648_(BossBarOverlay.NOTCHED_10);
         this.m_6451_(BossBarColor.RED);
         float percentage = boss.m_21223_() / boss.m_21233_();
         this.m_142711_(percentage);
      } else if (this.type == 1) {
         int maxBars = (int)(boss.m_21233_() / 500.0F * 5.0F);
         maxBars = Math.max(1, maxBars);
         maxBars = Math.min(COLOR_ORDER.length, maxBars);
         float hpThreshold = boss.m_21233_() / (float)maxBars;
         if (boss.m_21223_() == boss.m_21233_()) {
            this.m_6451_(COLOR_ORDER[maxBars - 1]);
            this.m_142711_(1.0F);
            return;
         }

         float percentage = boss.m_21223_() % hpThreshold / hpThreshold;
         int currentBar = (int)Math.ceil((double)(boss.m_21223_() / boss.m_21233_() * (float)maxBars)) - 1;
         currentBar = Math.max(0, currentBar);
         BossEvent.BossBarColor currentColor = COLOR_ORDER[currentBar];
         this.m_6451_(currentColor);
         this.m_142711_(percentage);
      } else if (this.type == 2) {
         float hpThreshold = Math.min(boss.m_21233_(), 100.0F);
         int maxBars = (int)Math.ceil((double)((boss.m_21233_() - hpThreshold) / hpThreshold));
         int activeBars = (int)Math.ceil((double)((boss.m_21223_() - hpThreshold) / hpThreshold));
         this.setBars(maxBars, activeBars);
         if (boss.m_21223_() == boss.m_21233_()) {
            this.m_142711_(1.0F);
            return;
         }

         float finalBarThreshold = Math.min(boss.m_21233_() - (float)(activeBars * 100), 100.0F);
         float percentage = boss.m_21223_() % hpThreshold / finalBarThreshold;
         this.m_142711_(percentage);
      }

   }

   public void setBars(int total, int active) {
      boolean needsUpdate = false;
      if (this.totalBars != total) {
         this.totalBars = total;
         needsUpdate = true;
      }

      if (this.activeBars != active) {
         this.activeBars = active;
         needsUpdate = true;
      }

      if (needsUpdate) {
         this.update();
      }

   }

   public void update() {
      if (this.m_8323_()) {
         SUpdateOPBossEventPacket packet = new SUpdateOPBossEventPacket(this.m_18860_(), this.totalBars, this.activeBars);

         for(ServerPlayer serverplayerentity : this.m_8324_()) {
            ModNetwork.sendTo(packet, serverplayerentity);
         }
      }

   }

   static {
      COLOR_ORDER = new BossEvent.BossBarColor[]{BossBarColor.GREEN, BossBarColor.YELLOW, BossBarColor.PINK, BossBarColor.RED, BossBarColor.PURPLE};
   }
}
