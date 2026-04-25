package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.crafting.MultiChannelDyeRecipe;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingDialRecipe;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingEnchantmentRecipe;

public class ModRecipes {
   public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ModMain.PROJECT_ID);

   public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<MultiChannelDyeRecipe>> MULTI_CHANNEL_ARMOR_DYE = REGISTRY.register("crafting_special_multi_channel_dye", () -> new SimpleCraftingRecipeSerializer<>(MultiChannelDyeRecipe::new));
   public static final DeferredHolder<RecipeSerializer<?>, SmithingDialRecipe.Serializer> SMITHING_DIAL = REGISTRY.register("smithing_dial", SmithingDialRecipe.Serializer::new);
   public static final DeferredHolder<RecipeSerializer<?>, SmithingEnchantmentRecipe.Serializer> SMITHING_ENCHANTMENT = REGISTRY.register("smithing_enchantment", SmithingEnchantmentRecipe.Serializer::new);

   public static void init(IEventBus eventBus) {
       REGISTRY.register(eventBus);
   }
}
