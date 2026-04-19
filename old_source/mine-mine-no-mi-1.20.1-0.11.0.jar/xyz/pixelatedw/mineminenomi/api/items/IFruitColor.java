package xyz.pixelatedw.mineminenomi.api.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IFruitColor {
   default boolean hasBaseColor(ItemStack stack) {
      CompoundTag compoundnbt = stack.m_41737_("display");
      return compoundnbt != null && compoundnbt.m_128425_("color", 99);
   }

   default int getBaseColor(ItemStack stack) {
      CompoundTag compoundnbt = stack.m_41737_("display");
      return compoundnbt != null && compoundnbt.m_128425_("color", 99) ? compoundnbt.m_128451_("color") : -1;
   }

   default void removeBaseColor(ItemStack stack) {
      CompoundTag compoundnbt = stack.m_41737_("display");
      if (compoundnbt != null && compoundnbt.m_128441_("color")) {
         compoundnbt.m_128473_("color");
      }

   }

   default void setBaseColor(ItemStack stack, int color) {
      stack.m_41698_("display").m_128405_("color", color);
   }

   default boolean hasStemColor(ItemStack stack) {
      CompoundTag compoundnbt = stack.m_41737_("display");
      return compoundnbt != null && compoundnbt.m_128425_("stem_color", 99);
   }

   default int getStemColor(ItemStack stack) {
      CompoundTag compoundnbt = stack.m_41737_("display");
      return compoundnbt != null && compoundnbt.m_128425_("stem_color", 99) ? compoundnbt.m_128451_("stem_color") : 10511680;
   }

   default void removeStemColor(ItemStack stack) {
      CompoundTag compoundnbt = stack.m_41737_("display");
      if (compoundnbt != null && compoundnbt.m_128441_("stem_color")) {
         compoundnbt.m_128473_("stem_color");
      }

   }

   default void setStemColor(ItemStack stack, int color) {
      stack.m_41698_("display").m_128405_("stem_color", color);
   }
}
