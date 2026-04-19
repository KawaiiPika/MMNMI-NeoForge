package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public class ReachDorikiObjective extends Objective implements IReachDorikiObjective {
   private int doriki;

   public ReachDorikiObjective(Quest parent, Component titleId, int doriki) {
      super(parent, titleId);
      this.setMaxProgress(1.0F);
      this.doriki = doriki;
   }

   public boolean checkDoriki(Player player) {
      double doriki = (Double)EntityStatsCapability.get(player).map((props) -> props.getDoriki()).orElse((double)0.0F);
      return doriki >= (double)this.doriki;
   }
}
