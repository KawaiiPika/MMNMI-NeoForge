package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GenericEnchantment extends Enchantment {
   private final boolean isTreasure;

   public GenericEnchantment(Enchantment.Rarity rarityIn, EnchantmentCategory category, boolean isTreasure, EquipmentSlot... slots) {
      super(rarityIn, category, slots);
      this.isTreasure = isTreasure;
   }

   public int m_6586_() {
      return 1;
   }

   public boolean m_6591_() {
      return this.isTreasure;
   }
}
