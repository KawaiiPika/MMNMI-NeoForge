package xyz.pixelatedw.mineminenomi.api.crafting;

import com.google.gson.JsonObject;
import java.util.stream.Stream;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class SmithingEnchantmentRecipe implements SmithingRecipe {
   private final ResourceLocation id;
   final Ingredient base;
   final Ingredient addition;
   final int additionAmount;
   final Enchantment enchantment;
   final int enchantmentMaxLevel;

   public SmithingEnchantmentRecipe(ResourceLocation id, Ingredient baseIngredient, Ingredient additionIngredient, int additionAmount, Enchantment enchantment, int enchantmentMaxLevel) {
      this.id = id;
      this.base = baseIngredient;
      this.addition = additionIngredient;
      this.additionAmount = additionAmount;
      this.enchantment = enchantment;
      this.enchantmentMaxLevel = enchantmentMaxLevel;
   }

   public boolean m_5818_(Container container, Level level) {
      return this.base.test(container.m_8020_(1)) && this.addition.test(container.m_8020_(2)) && container.m_8020_(2).m_41613_() >= this.additionAmount;
   }

   public ItemStack m_5874_(Container container, RegistryAccess registry) {
      ItemStack baseStack = container.m_8020_(1).m_41777_();
      if (baseStack.getEnchantmentLevel(this.enchantment) > 0) {
         return baseStack;
      } else {
         int additionCount = container.m_8020_(2).m_41613_();
         int level = Math.min((int)Math.floor((double)(additionCount / this.additionAmount)), this.enchantmentMaxLevel);
         baseStack.m_41663_(this.enchantment, level);
         return baseStack;
      }
   }

   public ItemStack m_8043_(RegistryAccess registry) {
      return ItemStack.f_41583_;
   }

   public boolean m_266166_(ItemStack stack) {
      return false;
   }

   public boolean m_266343_(ItemStack stack) {
      return this.base.test(stack) && stack.getEnchantmentLevel(this.enchantment) <= 0;
   }

   public boolean m_266253_(ItemStack stack) {
      return this.addition.test(stack) && stack.m_41613_() >= this.additionAmount;
   }

   public int getAdditionalAmount(Container container) {
      int additionCount = container.m_8020_(2).m_41613_();
      int level = Math.min((int)Math.floor((double)(additionCount / this.additionAmount)), this.enchantmentMaxLevel);
      return level * this.additionAmount;
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer)ModRecipes.SMITHING_ENCHANTMENT.get();
   }

   public boolean m_142505_() {
      return Stream.of(this.base, this.addition).anyMatch(ForgeHooks::hasNoElements);
   }

   public static class Serializer implements RecipeSerializer<SmithingEnchantmentRecipe> {
      public SmithingEnchantmentRecipe fromJson(ResourceLocation id, JsonObject json) {
         Ingredient baseIngredient = Ingredient.m_43917_(GsonHelper.m_289747_(json, "base"));
         Ingredient additionIngredient = Ingredient.m_43917_(GsonHelper.m_289747_(json, "addition"));
         int additionAmount = GsonHelper.m_13824_(json, "additionAmount", 0);
         JsonObject resultJson = GsonHelper.m_13930_(json, "result");
         ResourceLocation enchantmentId = ResourceLocation.parse(GsonHelper.m_13906_(resultJson, "enchantment"));
         Enchantment enchantment = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(enchantmentId);
         int enchantmentLevel = GsonHelper.m_13824_(resultJson, "maxLevel", 0);
         return new SmithingEnchantmentRecipe(id, baseIngredient, additionIngredient, additionAmount, enchantment, enchantmentLevel);
      }

      public SmithingEnchantmentRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
         Ingredient baseIngredient = Ingredient.m_43940_(buffer);
         Ingredient additionIngredient = Ingredient.m_43940_(buffer);
         int additionAmount = buffer.readInt();
         Enchantment enchantment = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(buffer.m_130281_());
         int enchantmentMaxLevel = buffer.readInt();
         return new SmithingEnchantmentRecipe(id, baseIngredient, additionIngredient, additionAmount, enchantment, enchantmentMaxLevel);
      }

      public void toNetwork(FriendlyByteBuf buffer, SmithingEnchantmentRecipe recipe) {
         recipe.base.m_43923_(buffer);
         recipe.addition.m_43923_(buffer);
         buffer.writeInt(recipe.additionAmount);
         buffer.m_130085_(ForgeRegistries.ENCHANTMENTS.getKey(recipe.enchantment));
         buffer.writeInt(recipe.enchantmentMaxLevel);
      }
   }
}
