package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import java.util.ArrayList;
import net.minecraft.world.item.ItemStack;

public interface ICollectItemObjective {
   boolean checkItem(ItemStack var1);

   default int checkItems(ArrayList<ItemStack> stacks) {
      return stacks.stream().filter(this::checkItem).mapToInt((stack) -> stack.m_41613_()).sum();
   }
}
