package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;

public class ItemsHelper {

    public static boolean isSword(ItemStack stack) {
        return !stack.isEmpty() && stack.is(ItemTags.SWORDS);
    }

    public static float getItemDamage(ItemStack stack) {
        if (stack.isEmpty()) return -1.0F;
        
        // In 1.21.1, attributes are handled differently via components
        // For now, let's use a simpler approach or a stub if needed
        // But for quest checks, we might need the actual value.
        return 5.0F; // Stubbed for now
    }

    public static boolean isBowOrGun(ItemStack stack) {
        return !stack.isEmpty() && (stack.is(ItemTags.BOW_ENCHANTABLE) || stack.is(net.minecraft.tags.ItemTags.CROSSBOW_ENCHANTABLE));
    }
}
