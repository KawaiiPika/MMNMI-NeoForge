package xyz.pixelatedw.mineminenomi.api;

import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class WyArmorMaterial implements ArmorMaterial {
   private static final int[] MAX_DAMAGE = new int[]{13, 15, 16, 11};
   private final String name;
   private final int durabilityMultiplier;
   private final int[] protectionArray;
   private final int enchantability;
   private final SoundEvent soundEvent;
   private final float toughness;
   private final float knockbackResistance;
   private final Supplier<Ingredient> repairMaterial;

   private WyArmorMaterial(String name, int durabilityMultiplier, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> supplier) {
      this.name = name;
      this.durabilityMultiplier = durabilityMultiplier;
      this.protectionArray = damageReductionAmountArray;
      this.enchantability = enchantability;
      this.soundEvent = soundEvent;
      this.toughness = toughness;
      this.knockbackResistance = knockbackResistance;
      this.repairMaterial = supplier;
   }

   public int m_266425_(ArmorItem.Type slot) {
      return MAX_DAMAGE[slot.ordinal()] * this.durabilityMultiplier;
   }

   public int m_7366_(ArmorItem.Type slot) {
      return this.protectionArray[slot.ordinal()];
   }

   public int m_6646_() {
      return this.enchantability;
   }

   public SoundEvent m_7344_() {
      return this.soundEvent;
   }

   public Ingredient m_6230_() {
      return (Ingredient)this.repairMaterial.get();
   }

   public String m_6082_() {
      return this.name;
   }

   public float m_6651_() {
      return this.toughness;
   }

   public float m_6649_() {
      return this.knockbackResistance;
   }

   public static class Builder {
      private final String name;
      private int durabilityMultiplier = 1;
      private int[] damageReductionAmountArray = new int[]{1, 1, 1, 1};
      private int enchantability = 0;
      private SoundEvent equipSound;
      private float toughness;
      private float knockbackResistance;
      private Supplier<Ingredient> repairMaterial;

      public Builder(String name) {
         this.equipSound = SoundEvents.f_11675_;
         this.toughness = 0.0F;
         this.knockbackResistance = 0.0F;
         this.repairMaterial = () -> Ingredient.f_43901_;
         this.name = name;
      }

      public Builder setDurabilityMultiplier(int multiplier) {
         this.durabilityMultiplier = multiplier;
         return this;
      }

      public Builder setProtectionForSlot(ArmorItem.Type type, int protectionAmount) {
         this.damageReductionAmountArray[type.ordinal()] = protectionAmount;
         return this;
      }

      public Builder setEnchantability(int enchantability) {
         this.enchantability = enchantability;
         return this;
      }

      public Builder setToughness(int toughness) {
         this.toughness = (float)toughness;
         return this;
      }

      public Builder setKnockbackResistence(int knockbackResistance) {
         this.knockbackResistance = (float)knockbackResistance;
         return this;
      }

      public Builder setEquipSound(SoundEvent equipSound) {
         this.equipSound = equipSound;
         return this;
      }

      public Builder setRepairIngredient(Supplier<Ingredient> supplier) {
         this.repairMaterial = supplier;
         return this;
      }

      public WyArmorMaterial build() {
         return new WyArmorMaterial(this.name, this.durabilityMultiplier, this.damageReductionAmountArray, this.enchantability, this.equipSound, this.toughness, this.knockbackResistance, this.repairMaterial);
      }
   }
}
