package xyz.pixelatedw.mineminenomi.api.items;

import net.minecraft.world.item.ItemStack;

// Porting to NeoForge 1.21.1: Simplified for StrawDoll usage, full color component system will be done in Phase 3.
public interface IMultiChannelColorItem {
   default int getLayerColor(ItemStack stack, int layer) {
      return this.getDefaultLayerColor(layer);
   }

   default boolean hasLayerColor(ItemStack stack, int layer) {
      return false;
   }

   default void setLayerColor(ItemStack stack, int layer, int color) {}

   default void removeLayerColor(ItemStack stack, int layer) {}

   static void dyeArmor(ItemStack stack, int layer, int rgb) {
      if (stack.getItem() instanceof IMultiChannelColorItem colorItem) {
         colorItem.setLayerColor(stack, layer, rgb);
      }
   }

   int getDefaultLayerColor(int var1);

   boolean canBeDyed();

   int getMaxLayers();
}
