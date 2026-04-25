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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.core.Holder;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmithingEnchantmentRecipeBuilder implements RecipeBuilder {
    private final Ingredient base;
    private final Ingredient addition;
    private final int additionAmount;
    private final RecipeCategory category;
    private final Holder<Enchantment> enchantment;
    private final int enchantmentMaxLevel;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public SmithingEnchantmentRecipeBuilder(Ingredient base, Ingredient addition, int additionAmount, RecipeCategory category, Holder<Enchantment> enchantment, int enchantmentMaxLevel) {
        this.category = category;
        this.base = base;
        this.addition = addition;
        this.additionAmount = additionAmount;
        this.enchantment = enchantment;
        this.enchantmentMaxLevel = enchantmentMaxLevel;
    }

    public static SmithingEnchantmentRecipeBuilder smithing(Ingredient base, Ingredient addition, int additionAmount, RecipeCategory category, Holder<Enchantment> enchantment, int enchantmentMaxLevel) {
        return new SmithingEnchantmentRecipeBuilder(base, addition, additionAmount, category, enchantment, enchantmentMaxLevel);
    }

    @Override
    public SmithingEnchantmentRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public SmithingEnchantmentRecipeBuilder group(String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.ENCHANTED_BOOK; // The builder requires an item, but the real result varies
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        SmithingEnchantmentRecipe recipe = new SmithingEnchantmentRecipe(this.base, this.addition, this.additionAmount, this.enchantment, this.enchantmentMaxLevel);
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
