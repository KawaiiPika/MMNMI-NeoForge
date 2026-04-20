package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

// DFEncyclopediaItem stores information about discovered devil fruits.
// The NBT data was complex compound tags - full port deferred to Phase 3.
public class DFEncyclopediaItem extends Item {
    public DFEncyclopediaItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.mineminenomi.devil_fruit_encyclopedia.desc").withStyle(ChatFormatting.GRAY));
    }

    // TODO: Phase 3 - Restore updateEncyclopediae logic using Data Components
    // public static void updateEncyclopediae(Player player, ResourceLocation fruitKey, DFEncyclopediaEntry elements) { ... }
}
