package xyz.pixelatedw.mineminenomi.api.items;

import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public interface IFruitColor {

    default boolean hasBaseColor(ItemStack stack) {
        return stack.has(ModDataComponents.BASE_COLOR.get());
    }

    default boolean hasStemColor(ItemStack stack) {
        return stack.has(ModDataComponents.STEM_COLOR.get());
    }

    default int getBaseColor(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.BASE_COLOR.get(), -1);
    }

    default int getStemColor(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.STEM_COLOR.get(), 10511680);
    }

    default void setBaseColor(ItemStack stack, int color) {
        stack.set(ModDataComponents.BASE_COLOR.get(), color);
    }

    default void setStemColor(ItemStack stack, int color) {
        stack.set(ModDataComponents.STEM_COLOR.get(), color);
    }

    default void removeBaseColor(ItemStack stack) {
        stack.remove(ModDataComponents.BASE_COLOR.get());
    }

    default void removeStemColor(ItemStack stack) {
        stack.remove(ModDataComponents.STEM_COLOR.get());
    }
}
