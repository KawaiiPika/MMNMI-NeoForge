package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IEquipItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class EquippedItemObjective extends Objective implements IEquipItemObjective {
   private Supplier<Item> itemTarget;
   private EquipmentSlot slotTarget;

   public EquippedItemObjective(Quest parent, Component titleId, Supplier<Item> item, EquipmentSlot slot) {
      super(parent, titleId);
      this.setMaxProgress(1.0F);
      this.itemTarget = item;
      this.slotTarget = slot;
   }

   public boolean checkEquippedItem(Player player, ItemStack toCheck) {
      return toCheck.m_41720_().equals(this.itemTarget.get());
   }

   public boolean checkSlot(EquipmentSlot slot) {
      return slot == this.slotTarget;
   }
}
