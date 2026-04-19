package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IUseItemObjective {
   boolean checkItem(Player var1, ItemStack var2, int var3);
}
