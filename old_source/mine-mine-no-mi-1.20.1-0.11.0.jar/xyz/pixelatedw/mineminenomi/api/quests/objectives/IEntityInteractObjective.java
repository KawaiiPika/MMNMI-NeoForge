package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface IEntityInteractObjective {
   boolean checkInteraction(Player var1, Entity var2);
}
