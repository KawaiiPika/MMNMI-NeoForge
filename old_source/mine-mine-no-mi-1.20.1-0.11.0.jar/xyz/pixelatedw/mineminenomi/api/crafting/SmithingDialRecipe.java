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
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class SmithingDialRecipe implements SmithingRecipe {
   private final ResourceLocation id;
   final Ingredient base;
   final Ingredient addition;
   final int additionAmount;
   final ItemStack result;

   public SmithingDialRecipe(ResourceLocation id, Ingredient baseIngredient, Ingredient additionIngredient, int additionAmount, ItemStack result) {
      this.id = id;
      this.base = baseIngredient;
      this.addition = additionIngredient;
      this.additionAmount = additionAmount;
      this.result = result;
   }

   public boolean m_5818_(Container container, Level level) {
      return this.base.test(container.m_8020_(1)) && this.addition.test(container.m_8020_(2)) && container.m_8020_(2).m_41613_() >= this.additionAmount;
   }

   public ItemStack m_5874_(Container container, RegistryAccess registry) {
      ItemStack itemstack = this.result.m_41777_();
      return itemstack;
   }

   public ItemStack m_8043_(RegistryAccess registry) {
      return this.result;
   }

   public boolean m_266166_(ItemStack stack) {
      return false;
   }

   public boolean m_266343_(ItemStack stack) {
      return this.base.test(stack);
   }

   public boolean m_266253_(ItemStack stack) {
      return this.addition.test(stack) && stack.m_41613_() >= this.additionAmount;
   }

   public int getAdditionalAmount() {
      return this.additionAmount;
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer)ModRecipes.SMITHING_DIAL.get();
   }

   public boolean m_142505_() {
      return Stream.of(this.base, this.addition).anyMatch(ForgeHooks::hasNoElements);
   }

   public static class Serializer implements RecipeSerializer<SmithingDialRecipe> {
      public SmithingDialRecipe fromJson(ResourceLocation id, JsonObject json) {
         Ingredient baseIngredient = Ingredient.m_43917_(GsonHelper.m_289747_(json, "base"));
         Ingredient additionIngredient = Ingredient.m_43917_(GsonHelper.m_289747_(json, "addition"));
         int additionAmount = GsonHelper.m_13824_(json, "additionAmount", 0);
         ItemStack itemstack = ShapedRecipe.m_151274_(GsonHelper.m_13930_(json, "result"));
         return new SmithingDialRecipe(id, baseIngredient, additionIngredient, additionAmount, itemstack);
      }

      public SmithingDialRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
         Ingredient baseIngredient = Ingredient.m_43940_(buffer);
         Ingredient additionIngredient = Ingredient.m_43940_(buffer);
         int additionAmount = buffer.readInt();
         ItemStack itemstack = buffer.m_130267_();
         return new SmithingDialRecipe(id, baseIngredient, additionIngredient, additionAmount, itemstack);
      }

      public void toNetwork(FriendlyByteBuf buffer, SmithingDialRecipe recipe) {
         recipe.base.m_43923_(buffer);
         recipe.addition.m_43923_(buffer);
         buffer.writeInt(recipe.additionAmount);
         buffer.m_130055_(recipe.result);
      }
   }
}
