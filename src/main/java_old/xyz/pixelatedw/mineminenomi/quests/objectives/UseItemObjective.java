package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IUseItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class UseItemObjective extends Objective implements IUseItemObjective {
   protected ICheckItemUse useEvent;

   public UseItemObjective(Quest parent, Component titleId, int count) {
      this(parent, titleId, count, (ICheckItemUse)null);
   }

   public UseItemObjective(Quest parent, Component titleId, int count, Item item) {
      this(parent, titleId, count, (ICheckItemUse)((player, itemStack, duration) -> itemStack.m_41720_() == item));
   }

   public UseItemObjective(Quest parent, Component titleId, int count, ICheckItemUse check) {
      super(parent, titleId);
      this.useEvent = (player, itemStack, duration) -> true;
      this.setMaxProgress((float)count);
      if (check != null) {
         this.useEvent = check;
      }

   }

   public boolean checkItem(Player player, ItemStack itemStack, int duration) {
      return this.useEvent.test(player, itemStack, duration);
   }

   @FunctionalInterface
   public interface ICheckItemUse {
      boolean test(Player var1, ItemStack var2, int var3);

      default ICheckItemUse and(ICheckItemUse check) {
         return (player, itemStack, duration) -> this.test(player, itemStack, duration) && check.test(player, itemStack, duration);
      }

      default ICheckItemUse or(ICheckItemUse check) {
         return (player, itemStack, duration) -> this.test(player, itemStack, duration) || check.test(player, itemStack, duration);
      }
   }
}
