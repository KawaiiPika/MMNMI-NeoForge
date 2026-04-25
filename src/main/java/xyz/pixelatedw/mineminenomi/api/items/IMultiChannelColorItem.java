package xyz.pixelatedw.mineminenomi.api.items;

import java.util.List;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IMultiChannelColorItem {
   default boolean hasLayerColor(ItemStack stack, int layer) { return false; }

   default int getLayerColor(ItemStack stack, int layer) { return 0; }

   default void setLayerColor(ItemStack stack, int layer, int rgb) {}

   default void removeLayerColor(ItemStack stack, int layer) {}

   static void dyeArmor(ItemStack stack, int layer, int rgb) {
      if (stack.getItem() instanceof IMultiChannelColorItem colorItem) {
         colorItem.setLayerColor(stack, layer, rgb);
      }
   }

   static ItemStack dyeArmor(ItemStack stack, int layer, List<DyeItem> dyes) {
      ItemStack newStack = ItemStack.EMPTY;
      int[] colors = new int[3];
      int i = 0;
      int j = 0;
      Item item = stack.getItem();
      if (item instanceof IMultiChannelColorItem multiChannelColorItem) {
         newStack = stack.copyWithCount(1);
         if (multiChannelColorItem.hasLayerColor(stack, layer)) {
            int k = multiChannelColorItem.getLayerColor(newStack, layer);
            float r = (float)(k >> 16 & 255) / 255.0F;
            float g = (float)(k >> 8 & 255) / 255.0F;
            float b = (float)(k & 255) / 255.0F;
            i = (int)((float)i + Math.max(r, Math.max(g, b)) * 255.0F);
            colors[0] = (int)((float)colors[0] + r * 255.0F);
            colors[1] = (int)((float)colors[1] + g * 255.0F);
            colors[2] = (int)((float)colors[2] + b * 255.0F);
            ++j;
         }

         for(DyeItem dyeitem : dyes) {
            int color = dyeitem.getDyeColor().getTextureDiffuseColor();
            float[] rgbArray = new float[] { (color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F };
            int r = (int)(rgbArray[0] * 255.0F);
            int g = (int)(rgbArray[1] * 255.0F);
            int b = (int)(rgbArray[2] * 255.0F);
            i += Math.max(r, Math.max(g, b));
            colors[0] += r;
            colors[1] += g;
            colors[2] += b;
            ++j;
         }
      }

      if (item instanceof IMultiChannelColorItem multiChannelColorItem) {
         int averageRed = colors[0] / j;
         int averageGreen = colors[1] / j;
         int averageBlue = colors[2] / j;
         float averageIntensity = (float)i / (float)j;
         float maxIntensity = (float)Math.max(averageRed, Math.max(averageGreen, averageBlue));
         averageRed = (int)((float)averageRed * averageIntensity / maxIntensity);
         averageGreen = (int)((float)averageGreen * averageIntensity / maxIntensity);
         averageBlue = (int)((float)averageBlue * averageIntensity / maxIntensity);
         int finalColor = (averageRed << 8) + averageGreen;
         finalColor = (finalColor << 8) + averageBlue;
         multiChannelColorItem.setLayerColor(newStack, layer, finalColor);
         return newStack;
      } else {
         return ItemStack.EMPTY;
      }
   }

   int getDefaultLayerColor(int var1);

   boolean canBeDyed();

   int getMaxLayers();
}
