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
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("FLINT")),
                RecipeCategory.COMBAT,
                getItem("AXE_DIAL")
        ).unlocks("has_item", has(net.minecraft.world.item.Items.AIR))
         .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "axe_dial_smithing"));

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("FEATHER")),
                RecipeCategory.COMBAT,
                getItem("BREATH_DIAL")
        ).unlocks("has_item", has(net.minecraft.world.item.Items.AIR))
         .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "breath_dial_smithing"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("BULLET"), 8)
                .pattern("ic ")
                .pattern("ci ")
                .pattern("   ")
                .define('c', getItem("COBBLESTONE"))
                .define('i', getItem("IRON_INGOT"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "bullet"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("CANNON"), 1)
                .pattern("s  ")
                .pattern("iii")
                .pattern("ccc")
                .define('c', getItem("COBBLESTONE"))
                .define('i', getItem("IRON_BLOCK"))
                .define('s', getItem("STRING"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "cannon"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("CANNON_BALL"), 4)
                .pattern("cc ")
                .pattern("cc ")
                .pattern("   ")
                .define('c', getItem("COBBLESTONE"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "cannon_ball"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("CLIMA_TACT"), 1)
                .pattern("bsb")
                .pattern("bsb")
                .pattern("bsb")
                .define('b', getItem("LAPIS_LAZULI"))
                .define('s', getItem("STICK"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "clima_tact"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("COLA"), 1)
                .pattern(" s ")
                .pattern(" s ")
                .pattern(" b ")
                .define('b', getItem("GLASS_BOTTLE"))
                .define('s', getItem("SUGAR"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "cola"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("COLA_BACKPACK"), 1)
                .pattern("u u")
                .pattern("uiu")
                .pattern("u u")
                .define('i', getItem("IRON_INGOT"))
                .define('u', getItem("ULTRA_COLA"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "cola_backpack"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("COLA"), 1)
                .pattern(" s ")
                .pattern(" s ")
                .pattern(" b ")
                .define('b', getItem("EMPTY_COLA"))
                .define('s', getItem("SUGAR"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "cola_from_empty_bottle"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("DENSE_KAIROSEKI"), 1)
                .pattern("kk ")
                .pattern("kk ")
                .pattern("   ")
                .define('k', getItem("KAIROSEKI"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "dense_kairoseki"));

        net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, getItem("DEVIL_FRUIT_ENCYCLOPEDIA"), 1)
                .requires(getItem("DEVIL_FRUIT_ENCYCLOPEDIA"))
                .requires(getItem("DEVIL_FRUIT_ENCYCLOPEDIA"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "devil_fruit_encyclopedia_merge"));

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("IRON_INGOT")),
                RecipeCategory.COMBAT,
                getItem("EISEN_DIAL")
        ).unlocks("has_item", has(net.minecraft.world.item.Items.AIR))
         .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "eisen_dial_smithing"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("FLAG"), 1)
                .pattern("sww")
                .pattern("sww")
                .pattern("s  ")
                .define('s', getItem("STICK"))
                .define('w', net.minecraft.tags.ItemTags.create(net.minecraft.resources.ResourceLocation.parse("minecraft:wool")))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "flag"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("FLAG"), 1)
                .pattern("www")
                .pattern("wfw")
                .pattern("www")
                .define('f', getItem("FLAG"))
                .define('w', net.minecraft.tags.ItemTags.create(net.minecraft.resources.ResourceLocation.parse("minecraft:wool")))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "flag_upgrade"));

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("BLAZE_POWDER")),
                RecipeCategory.COMBAT,
                getItem("FLAME_DIAL")
        ).unlocks("has_item", has(net.minecraft.world.item.Items.AIR))
         .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "flame_dial_smithing"));

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("GLOWSTONE_DUST")),
                RecipeCategory.COMBAT,
                getItem("FLASH_DIAL")
        ).unlocks("has_item", has(net.minecraft.world.item.Items.AIR))
         .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "flash_dial_smithing"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("HANDCUFFS"), 1)
                .pattern("   ")
                .pattern("ici")
                .pattern("   ")
                .define('c', getItem("CHAIN"))
                .define('i', getItem("IRON_INGOT"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "handcuffs"));

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("GUNPOWDER")),
                RecipeCategory.COMBAT,
                getItem("IMPACT_DIAL")
        ).unlocks("has_item", has(net.minecraft.world.item.Items.AIR))
         .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "impact_dial_smithing"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("JITTE"), 1)
                .pattern(" k ")
                .pattern(" i ")
                .pattern(" i ")
                .define('i', getItem("IRON_INGOT"))
                .define('k', getItem("DENSE_KAIROSEKI"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "jitte"));

        net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, getItem("KAIROSEKI"), 9)
                .requires(getItem("KAIROSEKI_BLOCK"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "kairoseki"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("KAIROSEKI_BARS"), 3)
                .pattern("kkk")
                .pattern("kkk")
                .pattern("   ")
                .define('k', getItem("DENSE_KAIROSEKI"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "kairoseki_bars"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("KAIROSEKI_BLOCK"), 1)
                .pattern("kkk")
                .pattern("kkk")
                .pattern("kkk")
                .define('k', getItem("KAIROSEKI"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "kairoseki_block"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("KAIROSEKI_BULLET"), 8)
                .pattern("kc ")
                .pattern("ck ")
                .pattern("   ")
                .define('c', getItem("COBBLESTONE"))
                .define('k', getItem("KAIROSEKI"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "kairoseki_bullet"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("KAIROSEKI_HANDCUFFS"), 1)
                .pattern("   ")
                .pattern("kck")
                .pattern("   ")
                .define('c', getItem("CHAIN"))
                .define('k', getItem("DENSE_KAIROSEKI"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "kairoseki_handcuffs"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("KEY"), 1)
                .pattern(" g ")
                .pattern(" g ")
                .pattern(" g ")
                .define('g', getItem("GOLD_INGOT"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "key"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("MACE"), 1)
                .pattern(" b ")
                .pattern(" i ")
                .pattern(" s ")
                .define('b', getItem("IRON_BLOCK"))
                .define('i', getItem("IRON_INGOT"))
                .define('s', getItem("STICK"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "mace"));

        net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, getItem("MANGROVE_PLANKS"), 4)
                .requires(Ingredient.of(net.minecraft.tags.ItemTags.create(net.minecraft.resources.ResourceLocation.parse("mineminenomi:mangrove_logs"))))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "mangrove_planks"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("MANGROVE_WOOD"), 3)
                .pattern("##")
                .pattern("##")
                .define('#', getItem("MANGROVE_LOG"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "mangrove_wood"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("MEDIC_BAG"), 1)
                .pattern("s s")
                .pattern("lbl")
                .pattern("lll")
                .define('b', getItem("LAPIS_LAZULI"))
                .define('l', getItem("LEATHER"))
                .define('s', getItem("STRING"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "medic_bag"));

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("NAUTILUS_SHELL")),
                Ingredient.of(getItem("SKY_BLOCK")),
                RecipeCategory.COMBAT,
                getItem("MILKY_DIAL")
        ).unlocks("has_item", has(net.minecraft.world.item.Items.AIR))
         .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "milky_dial_smithing"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("PERFECT_CLIMA_TACT"), 1)
                .pattern("eme")
                .pattern("bcf")
                .pattern("ele")
                .define('b', getItem("BREATH_DIAL"))
                .define('c', getItem("CLIMA_TACT"))
                .define('e', getItem("EISEN_DIAL"))
                .define('f', getItem("FLAME_DIAL"))
                .define('l', getItem("FLASH_DIAL"))
                .define('m', getItem("MILKY_DIAL"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "perfect_clima_tact"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("PIPE"), 1)
                .pattern(" i ")
                .pattern(" i ")
                .pattern(" i ")
                .define('i', getItem("IRON_INGOT"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "pipe"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("SAKE_CUP"), 1)
                .pattern("   ")
                .pattern("c c")
                .pattern(" c ")
                .define('c', getItem("CLAY_BALL"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "sake_cup"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("SCISSORS"), 1)
                .pattern(" ii")
                .pattern("cii")
                .pattern("cc ")
                .define('c', getItem("COBBLESTONE"))
                .define('i', getItem("IRON_INGOT"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "scissors"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("SORCERY_CLIMA_TACT"), 1)
                .pattern("ggg")
                .pattern("gcg")
                .pattern("ggg")
                .define('c', getItem("PERFECT_CLIMA_TACT"))
                .define('g', getItem("GOLD_INGOT"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "sorcery_clima_tact"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("STRIPPED_MANGROVE_WOOD"), 3)
                .pattern("##")
                .pattern("##")
                .define('#', getItem("STRIPPED_MANGROVE_LOG"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "stripped_mangrove_wood"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("ULTRA_COLA"), 1)
                .pattern("sss")
                .pattern("sss")
                .pattern("scs")
                .define('c', getItem("COLA"))
                .define('s', getItem("SUGAR"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "ultra_cola"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("ULTRA_COLA"), 1)
                .pattern("sss")
                .pattern("sss")
                .pattern("sbs")
                .define('b', getItem("EMPTY_ULTRA_COLA"))
                .define('s', getItem("SUGAR"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "ultra_cola_from_empty_bottle"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("UMBRELLA"), 1)
                .pattern("www")
                .pattern(" s ")
                .pattern(" s ")
                .define('s', getItem("STICK"))
                .define('w', net.minecraft.tags.ItemTags.create(net.minecraft.resources.ResourceLocation.parse("minecraft:wool")))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "umbrella"));

        net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, getItem("VIVRE_CARD"), 1)
                .requires(getItem("PAPER"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "vivre_card"));

        net.minecraft.data.recipes.ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getItem("WANTED_POSTER"), 1)
                .pattern("www")
                .pattern("wfw")
                .pattern("www")
                .define('f', getItem("WANTED_POSTER"))
                .define('w', getItem("PAPER"))
                .unlockedBy("has_item", has(net.minecraft.world.item.Items.AIR))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(xyz.pixelatedw.mineminenomi.ModMain.PROJECT_ID, "wanted_poster_upgrade"));


    }

    private net.minecraft.world.item.Item getItem(String name) {
        net.minecraft.world.item.Item item = xyz.pixelatedw.mineminenomi.init.ModRegistry.ITEMS.getEntries().stream()
                .filter(i -> i.getId().getPath().equalsIgnoreCase(name))
                .map(i -> (net.minecraft.world.item.Item) i.get())
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(net.minecraft.resources.ResourceLocation.withDefaultNamespace(name.toLowerCase()));
        }

        if (item == net.minecraft.world.item.Items.AIR) {
            return net.minecraft.world.item.Items.STONE;
        }
        return item;
    }
}
