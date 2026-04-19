package xyz.pixelatedw.mineminenomi.api.crafting;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class SmithingDialRecipeBuilder {
   private final Ingredient base;
   private final Ingredient addition;
   private final int additionAmount;
   private final RecipeCategory category;
   private final Item result;
   private final Advancement.Builder advancement = Builder.m_285878_();
   private final RecipeSerializer<?> type;

   public SmithingDialRecipeBuilder(RecipeSerializer<?> type, Ingredient base, Ingredient addition, int additionAmount, RecipeCategory category, Item result) {
      this.category = category;
      this.type = type;
      this.base = base;
      this.addition = addition;
      this.additionAmount = additionAmount;
      this.result = result;
   }

   public static SmithingDialRecipeBuilder smithing(Ingredient base, Ingredient addition, int additionAmount, RecipeCategory category, Item result) {
      return new SmithingDialRecipeBuilder((RecipeSerializer)ModRecipes.SMITHING_DIAL.get(), base, addition, additionAmount, category, result);
   }

   public SmithingDialRecipeBuilder unlocks(String name, CriterionTriggerInstance crit) {
      this.advancement.m_138386_(name, crit);
      return this;
   }

   public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
      this.ensureValid(id);
      this.advancement.m_138396_(RecipeBuilder.f_236353_).m_138386_("has_the_recipe", RecipeUnlockedTrigger.m_63728_(id)).m_138354_(net.minecraft.advancements.AdvancementRewards.Builder.m_10009_(id)).m_138360_(RequirementsStrategy.f_15979_);
      consumer.accept(new Result(id, this.type, this.base, this.addition, this.additionAmount, this.result, this.advancement, id.m_246208_("recipes/" + this.category.m_247710_() + "/")));
   }

   private void ensureValid(ResourceLocation p_267259_) {
      if (this.advancement.m_138405_().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(p_267259_));
      }
   }

   public static record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient base, Ingredient addition, int additionAmount, Item result, Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {
      public void m_7917_(JsonObject json) {
         json.add("base", this.base.m_43942_());
         json.add("addition", this.addition.m_43942_());
         json.addProperty("additionAmount", this.additionAmount);
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
         json.add("result", jsonobject);
      }

      public ResourceLocation m_6445_() {
         return this.id;
      }

      public RecipeSerializer<?> m_6637_() {
         return this.type;
      }

      @Nullable
      public JsonObject m_5860_() {
         return this.advancement.m_138400_();
      }

      @Nullable
      public ResourceLocation m_6448_() {
         return this.advancementId;
      }
   }
}
