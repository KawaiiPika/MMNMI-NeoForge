package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class QuestHelper {
    public static boolean removeQuestItem(Player player, Item item, int amount) {
        int id = player.getInventory().findSlotMatchingItem(new net.minecraft.world.item.ItemStack(item));
        if (id < 0 || player.getInventory().getItem(id).getCount() < amount) {
            player.sendSystemMessage(Component.literal("Missing quest items: " + amount + "x " + item.getDescriptionId()));
            return false;
        } else {
            player.getInventory().getItem(id).shrink(amount);
            return true;
        }
    }
}
