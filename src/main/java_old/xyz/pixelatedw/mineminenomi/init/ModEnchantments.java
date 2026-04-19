package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.GenericEnchantment;

public class ModEnchantments {
   public static final RegistryObject<Enchantment> DIAL_IMPACT = ModRegistry.<Enchantment>registerEnchantment("Impact Dial", () -> new GenericEnchantment(Rarity.RARE, EnchantmentCategory.WEAPON, false, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
   public static final RegistryObject<Enchantment> DIAL_FLASH = ModRegistry.<Enchantment>registerEnchantment("Flash Dial", () -> new GenericEnchantment(Rarity.RARE, EnchantmentCategory.WEAPON, false, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
   public static final RegistryObject<Enchantment> KAIROSEKI = ModRegistry.<Enchantment>registerEnchantment("Kairoseki", () -> new GenericEnchantment(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, false, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));

   public static void init() {
   }
}
