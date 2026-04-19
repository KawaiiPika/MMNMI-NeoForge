package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IEquipItemObjective {
   boolean checkEquippedItem(Player var1, ItemStack var2);

   boolean checkSlot(EquipmentSlot var1);
}
