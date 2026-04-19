package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICollectItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class CollectItemObjective extends Objective implements ICollectItemObjective {
    private Predicate<ItemStack> check = (itemStack) -> false;

    public CollectItemObjective(Quest parent, Component titleId, int count, Predicate<ItemStack> check) {
        super(parent, titleId);
        this.setMaxProgress((float) count);
        this.check = check;
    }

    @Override
    public boolean checkItem(ItemStack stack) {
        return this.check.test(stack);
    }
}
