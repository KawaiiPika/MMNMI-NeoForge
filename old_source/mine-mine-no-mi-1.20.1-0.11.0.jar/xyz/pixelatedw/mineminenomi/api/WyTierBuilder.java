package xyz.pixelatedw.mineminenomi.api;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

public class WyTierBuilder {
   private int level = 0;
   private final int uses;
   private float speed = 0.0F;
   private float attackDamageBonus = 0.0F;
   private int enchantmentValue = 14;
   private TagKey<Block> tag;
   private final Supplier<Ingredient> repairIngredient;

   public static Tier aboveNothing(int uses, Supplier<Ingredient> repairIngredient, ResourceLocation id) {
      WyTierBuilder builder = new WyTierBuilder(0, uses, repairIngredient);
      return builder.build(id, List.of(), List.of(Tiers.WOOD));
   }

   public static Tier aboveWood(int uses, Supplier<Ingredient> repairIngredient, ResourceLocation id) {
      WyTierBuilder builder = new WyTierBuilder(1, uses, repairIngredient);
      return builder.build(id, List.of(Tiers.WOOD), List.of(Tiers.STONE));
   }

   public static Tier aboveStone(int uses, Supplier<Ingredient> repairIngredient, ResourceLocation id) {
      WyTierBuilder builder = new WyTierBuilder(2, uses, repairIngredient);
      return builder.build(id, List.of(Tiers.STONE), List.of(Tiers.IRON));
   }

   public static Tier aboveIron(int uses, Supplier<Ingredient> repairIngredient, ResourceLocation id) {
      WyTierBuilder builder = new WyTierBuilder(3, uses, repairIngredient);
      return builder.build(id, List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
   }

   public static Tier aboveDiamond(int uses, Supplier<Ingredient> repairIngredient, ResourceLocation id) {
      WyTierBuilder builder = new WyTierBuilder(4, uses, repairIngredient);
      return builder.build(id, List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
   }

   public static Tier aboveNetherite(int uses, Supplier<Ingredient> repairIngredient, ResourceLocation id) {
      WyTierBuilder builder = new WyTierBuilder(5, uses, repairIngredient);
      return builder.build(id, List.of(Tiers.NETHERITE), List.of());
   }

   public WyTierBuilder(int level, int uses, Supplier<Ingredient> repairIngredient) {
      this.level = level;
      this.uses = uses;
      this.repairIngredient = repairIngredient;
   }

   public WyTierBuilder setMiningSpeed(float speed) {
      this.speed = speed;
      return this;
   }

   public WyTierBuilder setEnchantmentValue(int enchantmentValue) {
      this.enchantmentValue = enchantmentValue;
      return this;
   }

   public WyTierBuilder setAttackDamageBonus(int attackDamageBonus) {
      this.attackDamageBonus = (float)attackDamageBonus;
      return this;
   }

   public WyTierBuilder setBlockTag(TagKey<Block> blockTag) {
      this.tag = blockTag;
      return this;
   }

   public Tier build(ResourceLocation id, List<Object> after, List<Object> before) {
      Tier tier = new ForgeTier(this.level, this.uses, this.speed, this.attackDamageBonus, this.enchantmentValue, this.tag, this.repairIngredient);
      Tier var5 = TierSortingRegistry.registerTier(tier, id, after, before);
      return var5;
   }
}
