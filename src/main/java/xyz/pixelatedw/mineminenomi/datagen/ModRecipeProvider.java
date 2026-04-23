package xyz.pixelatedw.mineminenomi.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // Example Shapeless Recipe
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.EMPTY_COLA.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_glass_bottle", has(Items.GLASS_BOTTLE))
                .save(output);

        // Another Shapeless Recipe example
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.NORMAL_HANDCUFFS.get())
                .requires(Items.IRON_INGOT, 3)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(output);

        // Another example to demonstrate usage on weapons
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModWeapons.AXE.get())
                .requires(Items.IRON_AXE)
                .requires(Items.IRON_INGOT)
                .unlockedBy("has_iron_axe", has(Items.IRON_AXE))
                .save(output);

        // Example Smithing Transform Recipe as requested
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(ModWeapons.AXE.get()),
                Ingredient.of(Items.NETHERITE_INGOT),
                RecipeCategory.COMBAT,
                ModWeapons.MACE.get()
        ).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
         .save(output, "mineminenomi:mace_smithing");
    }
}
