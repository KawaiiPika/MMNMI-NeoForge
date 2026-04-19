package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import java.util.List;
import net.minecraft.world.item.ItemStack;

public interface ICollectItemObjective {
    boolean checkItem(ItemStack stack);

    default int checkItems(List<ItemStack> stacks) {
        return stacks.stream()
                .filter(this::checkItem)
                .mapToInt(ItemStack::getCount)
                .sum();
    }
}
