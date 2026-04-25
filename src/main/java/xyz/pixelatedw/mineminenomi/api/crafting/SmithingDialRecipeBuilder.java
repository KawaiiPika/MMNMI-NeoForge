package xyz.pixelatedw.mineminenomi.api.crafting;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmithingDialRecipeBuilder implements RecipeBuilder {
    private final Ingredient base;
    private final Ingredient addition;
    private final int additionAmount;
    private final RecipeCategory category;
    private final Item result;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public SmithingDialRecipeBuilder(Ingredient base, Ingredient addition, int additionAmount, RecipeCategory category, Item result) {
        this.category = category;
        this.base = base;
        this.addition = addition;
        this.additionAmount = additionAmount;
        this.result = result;
    }

    public static SmithingDialRecipeBuilder smithing(Ingredient base, Ingredient addition, int additionAmount, RecipeCategory category, Item result) {
        return new SmithingDialRecipeBuilder(base, addition, additionAmount, category, result);
    }

    @Override
    public SmithingDialRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public SmithingDialRecipeBuilder group(String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        SmithingDialRecipe recipe = new SmithingDialRecipe(this.base, this.addition, this.additionAmount, new ItemStack(this.result));
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
