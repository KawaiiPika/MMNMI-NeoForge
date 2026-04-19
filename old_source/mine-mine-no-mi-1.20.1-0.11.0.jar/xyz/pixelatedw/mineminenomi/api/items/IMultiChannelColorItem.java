package xyz.pixelatedw.mineminenomi.api.items;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IMultiChannelColorItem {
   String TAG_COLOR = "color";
   String TAG_DISPLAY = "display";

   default int getLayerColor(ItemStack stack, int layer) {
      CompoundTag tag = stack.m_41737_("display");
      String colorTag = this.getColorTag(layer);
      return tag != null && tag.m_128425_(colorTag, 99) ? tag.m_128451_(colorTag) : this.getDefaultLayerColor(layer);
   }

   default boolean hasLayerColor(ItemStack stack, int layer) {
      CompoundTag tag = stack.m_41737_("display");
      return tag == null ? false : tag.m_128441_(this.getColorTag(layer));
   }

   default void setLayerColor(ItemStack stack, int layer, int color) {
      color &= 16777215;
      stack.m_41698_("display").m_128405_(this.getColorTag(layer), color);
   }

   default void removeLayerColor(ItemStack stack, int layer) {
      CompoundTag tag = stack.m_41737_("display");
      String colorTag = this.getColorTag(layer);
      if (tag != null && tag.m_128441_(colorTag)) {
         tag.m_128473_(colorTag);
      }

   }

   default String getColorTag(int layer) {
      return "color_" + layer;
   }

   static void dyeArmor(ItemStack stack, int layer, int rgb) {
      Item var4 = stack.m_41720_();
      if (var4 instanceof IMultiChannelColorItem colorItem) {
         colorItem.setLayerColor(stack, layer, rgb);
      }

   }

   static ItemStack dyeArmor(ItemStack stack, int layer, List<DyeItem> dyes) {
      ItemStack newStack = ItemStack.f_41583_;
      int[] colors = new int[3];
      int i = 0;
      int j = 0;
      Item item = stack.m_41720_();
      if (item instanceof IMultiChannelColorItem multiChannelColorItem) {
         newStack = stack.m_255036_(1);
         if (multiChannelColorItem.hasLayerColor(stack, layer)) {
            int k = multiChannelColorItem.getLayerColor(newStack, layer);
            float r = (float)(k >> 16 & 255) / 255.0F;
            float g = (float)(k >> 8 & 255) / 255.0F;
            float b = (float)(k & 255) / 255.0F;
            i += (int)(Math.max(r, Math.max(g, b)) * 255.0F);
            colors[0] += (int)(r * 255.0F);
            colors[1] += (int)(g * 255.0F);
            colors[2] += (int)(b * 255.0F);
            ++j;
         }

         for(DyeItem dyeItem : dyes) {
            float[] afloat = dyeItem.m_41089_().m_41068_();
            int i2 = (int)(afloat[0] * 255.0F);
            int l = (int)(afloat[1] * 255.0F);
            int i1 = (int)(afloat[2] * 255.0F);
            i += Math.max(i2, Math.max(l, i1));
            colors[0] += i2;
            colors[1] += l;
            colors[2] += i1;
            ++j;
         }
      }

      if (multiChannelColorItem == null) {
         return ItemStack.f_41583_;
      } else {
         int j1 = colors[0] / j;
         int k1 = colors[1] / j;
         int l1 = colors[2] / j;
         float f3 = (float)i / (float)j;
         float f4 = (float)Math.max(j1, Math.max(k1, l1));
         j1 = (int)((float)j1 * f3 / f4);
         k1 = (int)((float)k1 * f3 / f4);
         l1 = (int)((float)l1 * f3 / f4);
         int j2 = (j1 << 8) + k1;
         j2 = (j2 << 8) + l1;
         multiChannelColorItem.setLayerColor(newStack, layer, j2);
         return newStack;
      }
   }

   int getDefaultLayerColor(int var1);

   boolean canBeDyed();

   int getMaxLayers();
}
