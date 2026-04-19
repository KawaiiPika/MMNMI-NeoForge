package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class TameEntityObjective extends Objective {
   public TameEntityObjective(Quest parent, Component titleId, int count) {
      super(parent, titleId);
      this.setMaxProgress((float)count);
   }
}
