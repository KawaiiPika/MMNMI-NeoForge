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
      return this.itemStack.m_41613_();
   }

   public TradeEntry setCount(int count) {
      this.itemStack.m_41764_(count);
      return this;
   }

   public boolean hasInfiniteStock() {
      return this.itemStack.m_41784_().m_128471_("isInfinite");
   }

   public int getPrice() {
      return this.itemStack.m_41784_().m_128451_("price");
   }
}
