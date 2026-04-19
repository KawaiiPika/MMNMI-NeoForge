package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IHealEntityObjective {
   boolean checkHeal(Player var1, LivingEntity var2, float var3);
}
