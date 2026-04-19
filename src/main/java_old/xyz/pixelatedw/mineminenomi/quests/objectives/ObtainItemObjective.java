package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICollectItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class ObtainItemObjective<T extends Item> extends Objective implements ICollectItemObjective {
   private Predicate<ItemStack> check = (itemStack) -> false;

   public ObtainItemObjective(Quest parent, Component titleId, int count, Supplier<T> itemTarget) {
      super(parent, titleId);
      this.setMaxProgress((float)count);
      this.check = (itemStack) -> itemStack.m_41720_().m_5456_() == itemTarget.get();
   }

   public ObtainItemObjective(Quest parent, Component titleId, int count, Predicate<ItemStack> check) {
      super(parent, titleId);
      this.setMaxProgress((float)count);
      this.check = check;
   }

   public boolean checkItem(ItemStack stack) {
      return this.check.test(stack);
   }

   public int checkItems(ArrayList<ItemStack> stacks) {
      return stacks.stream().filter(this::checkItem).mapToInt((stack) -> stack.m_41613_()).sum();
   }
}
