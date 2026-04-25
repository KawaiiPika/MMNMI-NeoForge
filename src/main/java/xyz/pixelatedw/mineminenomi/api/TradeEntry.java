package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.world.item.ItemStack;

public class TradeEntry {
   private ItemStack itemStack;

   public TradeEntry(ItemStack itemStack) {
      this.itemStack = itemStack;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   public int getCount() {
      return this.itemStack.getCount();
   }

   public TradeEntry setCount(int count) {
      this.itemStack.setCount(count);
      return this;
   }

   public boolean hasInfiniteStock() {
      return this.itemStack.has(net.minecraft.core.component.DataComponents.CUSTOM_DATA) && this.itemStack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA).copyTag().getBoolean("isInfinite");
   }

   public int getPrice() {
      return this.itemStack.has(net.minecraft.core.component.DataComponents.CUSTOM_DATA) ? this.itemStack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA).copyTag().getInt("price") : 0;
   }
}
