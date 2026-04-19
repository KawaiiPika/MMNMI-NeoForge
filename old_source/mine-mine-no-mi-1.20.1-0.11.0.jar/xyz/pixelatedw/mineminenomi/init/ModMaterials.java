package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.api.WyArmorMaterial;

public class ModMaterials {
   public static final WyArmorMaterial GLASSES_MATERIAL;
   public static final WyArmorMaterial SIMPLE_LEATHER_MATERIAL;
   public static final WyArmorMaterial SIMPLE_WOOL_MATERIAL;
   public static final WyArmorMaterial SIMPLE_IRON_MATERIAL;
   public static final WyArmorMaterial GAS_MASK_MATERIAL;
   public static final WyArmorMaterial BUBBLE_MATERIAL;
   public static final WyArmorMaterial SCARF_MATERIAL;
   /** @deprecated */
   @Deprecated
   public static final WyArmorMaterial BASIC_ARMOR_MATERIAL;
   public static final WyArmorMaterial STRAW_HAT_MATERIAL;
   public static final WyArmorMaterial KILLER_MASK_MATERIAL;
   public static final WyArmorMaterial COLA_BACKPACK_MATERIAL;
   public static final WyArmorMaterial PEARL_MATERIAL;
   public static final WyArmorMaterial CAPTAIN_CAPE_MATERIAL;
   public static final WyArmorMaterial FLUFFY_CAPE_MATERIAL;
   public static final WyArmorMaterial MEDIC_BAG_MATERIAL;
   public static final WyArmorMaterial TOMOE_DRUMS_MATERIAL;
   public static final WyArmorMaterial SNIPER_GOGGLES_MATERIAL;
   public static final WyArmorMaterial WOOTZ_STEEL_MATERIAL;
   public static final WyArmorMaterial BIG_NOSE_MATERIAL;
   public static final WyArmorMaterial SANDALS_MATERIAL;

   static {
      GLASSES_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:glasses")).setDurabilityMultiplier(5).setEnchantability(5).setEquipSound(SoundEvents.f_11672_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_})).build();
      SIMPLE_LEATHER_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:simple_leather")).setDurabilityMultiplier(5).setEnchantability(5).setProtectionForSlot(Type.HELMET, 1).setEquipSound(SoundEvents.f_11678_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42454_})).build();
      SIMPLE_WOOL_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:simple_wool")).setDurabilityMultiplier(5).setEnchantability(8).setProtectionForSlot(Type.HELMET, 1).setEquipSound(SoundEvents.f_12642_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_41870_})).build();
      SIMPLE_IRON_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:simple_iron")).setDurabilityMultiplier(5).setEnchantability(8).setProtectionForSlot(Type.HELMET, 1).setEquipSound(SoundEvents.f_12064_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_})).build();
      GAS_MASK_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:gas_mask")).setDurabilityMultiplier(5).setEnchantability(8).setProtectionForSlot(Type.HELMET, 2).setEquipSound(SoundEvents.f_11676_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42417_})).build();
      BUBBLE_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:bubble")).setDurabilityMultiplier(5).setEnchantability(8).setEquipSound(SoundEvents.f_11676_).build();
      SCARF_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:scarf")).setDurabilityMultiplier(5).setEnchantability(8).setEquipSound(SoundEvents.f_12642_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_41870_})).build();
      BASIC_ARMOR_MATERIAL = SIMPLE_LEATHER_MATERIAL;
      STRAW_HAT_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:straw_hat")).setDurabilityMultiplier(5).setEnchantability(15).setEquipSound(SoundEvents.f_11678_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42405_})).build();
      KILLER_MASK_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:killer_mask")).setDurabilityMultiplier(15).setEnchantability(12).setProtectionForSlot(Type.HELMET, 3).setEquipSound(SoundEvents.f_11677_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_})).build();
      COLA_BACKPACK_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:cola_backpack")).setDurabilityMultiplier(8).setEnchantability(12).setProtectionForSlot(Type.CHESTPLATE, 2).setEquipSound(SoundEvents.f_11677_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_})).build();
      PEARL_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:pearl")).setDurabilityMultiplier(40).setEnchantability(12).setProtectionForSlot(Type.HELMET, 5).setProtectionForSlot(Type.CHESTPLATE, 7).setProtectionForSlot(Type.LEGGINGS, 5).setEquipSound(SoundEvents.f_11677_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_})).build();
      CAPTAIN_CAPE_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:captain_cape")).setDurabilityMultiplier(5).setEnchantability(12).setProtectionForSlot(Type.CHESTPLATE, 3).setEquipSound(SoundEvents.f_11678_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42454_})).build();
      FLUFFY_CAPE_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:fluffy_cape")).setDurabilityMultiplier(5).setEnchantability(12).setProtectionForSlot(Type.CHESTPLATE, 3).setEquipSound(SoundEvents.f_12642_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_41870_})).build();
      MEDIC_BAG_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:medic_bag")).setDurabilityMultiplier(5).setEnchantability(8).setProtectionForSlot(Type.CHESTPLATE, 2).setEquipSound(SoundEvents.f_11678_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42454_})).build();
      TOMOE_DRUMS_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:tomoe_drums")).setDurabilityMultiplier(5).setEnchantability(8).setProtectionForSlot(Type.CHESTPLATE, 2).setEquipSound(SoundEvents.f_11677_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_, Items.f_42417_})).build();
      SNIPER_GOGGLES_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:sniper_goggles")).setDurabilityMultiplier(5).setEnchantability(8).setEquipSound(SoundEvents.f_11677_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_})).build();
      WOOTZ_STEEL_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:wootz_steel")).setDurabilityMultiplier(30).setEnchantability(12).setProtectionForSlot(Type.CHESTPLATE, 10).setEquipSound(SoundEvents.f_11676_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_, Items.f_42417_})).build();
      BIG_NOSE_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:big_nose")).setDurabilityMultiplier(5).setEnchantability(8).setEquipSound(SoundEvents.f_12642_).build();
      SANDALS_MATERIAL = (new WyArmorMaterial.Builder("mineminenomi:sandals")).setDurabilityMultiplier(0).setEnchantability(5).setProtectionForSlot(Type.BOOTS, 1).setEquipSound(SoundEvents.f_11678_).setRepairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{Items.f_42454_})).build();
   }
}
