package xyz.pixelatedw.mineminenomi.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public ModRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
        this.lookupProvider = lookupProvider;
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        HolderLookup.Provider lookupProvider = this.lookupProvider.join();

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DENSE_KAIROSEKI.get(), 1).define('k', ModItems.KAIROSEKI.get()).pattern("kk ").pattern("kk ").pattern("   ").unlockedBy("has_kairoseki", has(ModItems.KAIROSEKI.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.KAIROSEKI.get(), 9).requires(ModBlocks.KAIROSEKI.get()).unlockedBy("has_kairoseki", has(ModItems.KAIROSEKI.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.KAIROSEKI.get(), 1).define('k', ModItems.KAIROSEKI.get()).pattern("kkk").pattern("kkk").pattern("kkk").unlockedBy("has_kairoseki", has(ModItems.KAIROSEKI.get())).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.COLA.get(), 1).define('s', Items.SUGAR).define('b', Items.GLASS_BOTTLE).pattern(" s ").pattern(" s ").pattern(" b ").unlockedBy("has_sugar", has(Items.SUGAR)).unlockedBy("has_bottle", has(Items.GLASS_BOTTLE)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.COLA.get(), 1).define('s', Items.SUGAR).define('b', ModItems.EMPTY_COLA.get()).pattern(" s ").pattern(" s ").pattern(" b ").unlockedBy("has_sugar", has(Items.SUGAR)).unlockedBy("has_bottle", has(Items.GLASS_BOTTLE)).unlockedBy("has_empty_cola", has(ModItems.EMPTY_COLA.get())).save(output, ResourceLocation.fromNamespaceAndPath("mineminenomi", "cola_from_empty_bottle"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.ULTRA_COLA.get(), 1).define('s', Items.SUGAR).define('c', ModItems.COLA.get()).pattern("sss").pattern("sss").pattern("scs").unlockedBy("has_cola", has(ModItems.COLA.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.ULTRA_COLA.get(), 1).define('s', Items.SUGAR).define('b', ModItems.EMPTY_ULTRA_COLA.get()).pattern("sss").pattern("sss").pattern("sbs").unlockedBy("has_sugar", has(Items.SUGAR)).unlockedBy("has_bottle", has(Items.GLASS_BOTTLE)).unlockedBy("has_empty_cola", has(ModItems.EMPTY_ULTRA_COLA.get())).save(output, ResourceLocation.fromNamespaceAndPath("mineminenomi", "ultra_cola_from_empty_bottle"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.SAKE_CUP.get(), 1).define('c', Items.CLAY_BALL).pattern("   ").pattern("c c").pattern(" c ").unlockedBy("has_clay", has(Items.CLAY_BALL)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModArmors.COLA_BACKPACK.get(), 1).define('u', ModItems.ULTRA_COLA.get()).define('i', Items.IRON_INGOT).pattern("u u").pattern("uiu").pattern("u u").unlockedBy("has_iron", has(Items.IRON_INGOT)).unlockedBy("has_ultra_cola", has(ModItems.ULTRA_COLA.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModArmors.MEDIC_BAG.get(), 1).define('s', Items.REDSTONE).define('l', Items.LEATHER).define('b', Items.LAPIS_LAZULI).pattern("s s").pattern("lbl").pattern("lll").unlockedBy("has_lapis", has(Items.LAPIS_LAZULI)).unlockedBy("has_leather", has(Items.LEATHER)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.NORMAL_HANDCUFFS.get(), 1).define('i', Items.IRON_INGOT).define('c', Items.IRON_BARS).pattern("   ").pattern("ici").pattern("   ").unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.KAIROSEKI_HANDCUFFS.get(), 1).define('k', ModItems.DENSE_KAIROSEKI.get()).define('c', Items.IRON_BARS).pattern("   ").pattern("kck").pattern("   ").unlockedBy("has_kairoseki", has(ModItems.DENSE_KAIROSEKI.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModWeapons.JITTE.get(), 1).define('k', ModItems.DENSE_KAIROSEKI.get()).define('i', Items.IRON_INGOT).pattern(" k ").pattern(" i ").pattern(" i ").unlockedBy("has_kairoseki", has(ModItems.DENSE_KAIROSEKI.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModWeapons.SCISSORS.get(), 1).define('c', Items.SHEARS).define('i', Items.IRON_INGOT).pattern(" ii").pattern("cii").pattern("cc ").unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModWeapons.PIPE.get(), 1).define('i', Items.IRON_INGOT).pattern(" i ").pattern(" i ").pattern(" i ").unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModWeapons.MACE.get(), 1).define('s', Items.STICK).define('i', Items.IRON_INGOT).define('b', Items.GUNPOWDER).pattern(" b ").pattern(" i ").pattern(" s ").unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModWeapons.CLIMA_TACT.get(), 1).define('s', Items.STICK).define('b', Items.LAPIS_LAZULI).pattern("bsb").pattern("bsb").pattern("bsb").unlockedBy("has_lapis", has(Items.LAPIS_LAZULI)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModWeapons.PERFECT_CLIMA_TACT.get(), 1).define('c', ModWeapons.CLIMA_TACT.get()).define('b', ModItems.BREATH_DIAL.get()).define('f', ModItems.FLAME_DIAL.get()).define('e', ModItems.EISEN_DIAL.get()).define('l', ModItems.FLASH_DIAL.get()).define('m', ModItems.MILKY_DIAL.get()).pattern("eme").pattern("bcf").pattern("ele").unlockedBy("has_clima_tact", has(ModWeapons.CLIMA_TACT.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModWeapons.SORCERY_CLIMA_TACT.get(), 1).define('c', ModWeapons.PERFECT_CLIMA_TACT.get()).define('g', Items.GOLD_INGOT).pattern("ggg").pattern("gcg").pattern("ggg").unlockedBy("has_perfect_clima_tact", has(ModWeapons.PERFECT_CLIMA_TACT.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get(), 1).requires(ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()).requires(ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()).unlockedBy("has_encyclopedia", has(ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get())).save(output, ResourceLocation.fromNamespaceAndPath("mineminenomi", "devil_fruit_encyclopedia_merge"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KEY.get(), 1).define('g', Items.GOLD_INGOT).pattern(" g ").pattern(" g ").pattern(" g ").unlockedBy("has_gold", has(Items.GOLD_INGOT)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VIVRE_CARD.get(), 1).requires(Items.PAPER).unlockedBy("has_paper", has(Items.PAPER)).save(output);

        xyz.pixelatedw.mineminenomi.api.crafting.SmithingEnchantmentRecipeBuilder.smithing(Ingredient.of(ModWeapons.CLIMA_TACT.get()), Ingredient.of(Items.ECHO_SHARD), 1, RecipeCategory.COMBAT, lookupProvider.lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getOrThrow(net.minecraft.world.item.enchantment.Enchantments.MENDING), 1)
            .unlockedBy("has_clima_tact", has(ModWeapons.CLIMA_TACT.get()))
            .save(output, ResourceLocation.fromNamespaceAndPath("mineminenomi", "clima_tact_enchantment"));

        xyz.pixelatedw.mineminenomi.api.crafting.SmithingDialRecipeBuilder.smithing(Ingredient.of(ModWeapons.CLIMA_TACT.get()), Ingredient.of(ModItems.FLAME_DIAL.get()), 3, RecipeCategory.COMBAT, ModWeapons.CLIMA_TACT.get())
            .unlockedBy("has_clima_tact", has(ModWeapons.CLIMA_TACT.get()))
            .save(output, ResourceLocation.fromNamespaceAndPath("mineminenomi", "clima_tact_flame_dial"));



    }
}
