package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;

public class TimedKillEntityObjective extends KillEntityObjective {
   private long firstHit;
   private int ticks;

   public TimedKillEntityObjective(Quest parent, Component titleId, int count, int seconds) {
      this(parent, titleId, count, seconds, KillEntityObjective.DEFAULT_RULE);
   }

   public TimedKillEntityObjective(Quest parent, Component titleId, int count, int seconds, KillEntityObjective.ICheckKill check) {
      super(parent, titleId, count, check);
      this.firstHit = 0L;
      this.ticks = 0;
      this.ticks = seconds * 20;
   }

   public boolean checkKill(Player player, LivingEntity target, DamageSource source) {
      long hitTime = player.m_9236_().m_46467_();
      if (hitTime > this.firstHit + (long)this.ticks) {
         this.setProgress(player, 0.0F, false);
         this.firstHit = 0L;
      }

      if (this.firstHit == 0L) {
         this.firstHit = player.m_9236_().m_46467_();
      }

      return hitTime - (long)this.ticks <= this.firstHit ? super.checkKill(player, target, source) : false;
   }
}
