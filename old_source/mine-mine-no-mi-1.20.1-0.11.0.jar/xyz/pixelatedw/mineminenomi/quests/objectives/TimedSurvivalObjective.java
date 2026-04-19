package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ISurviveObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class TimedSurvivalObjective extends Objective implements ISurviveObjective {
   private int timeToSurvive;
   private float initialHP;

   public TimedSurvivalObjective(Quest parent, Component titleId, int seconds) {
      super(parent, titleId);
      this.timeToSurvive = seconds;
      this.setMaxProgress((float)this.timeToSurvive);
      this.addStartEvent(100, this::onStartEvent);
   }

   private boolean onStartEvent(Player player) {
      this.initialHP = player.m_21223_();
      return true;
   }

   public boolean checkTime(Player player) {
      if (player.m_21223_() > this.initialHP) {
         this.initialHP = player.m_21223_();
      }

      if (player.m_21223_() < this.initialHP) {
         this.setProgress(player, 0.0F, false);
         this.initialHP = player.m_21223_();
         return false;
      } else {
         return true;
      }
   }
}
