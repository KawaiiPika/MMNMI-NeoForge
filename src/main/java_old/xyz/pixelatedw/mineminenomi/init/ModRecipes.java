package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.crafting.MultiChannelDyeRecipe;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingDialRecipe;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingEnchantmentRecipe;

public class ModRecipes {
   public static final RegistryObject<RecipeSerializer<MultiChannelDyeRecipe>> MULTI_CHANNEL_ARMOR_DYE = ModRegistry.registerRecipeSerializer("crafting_special_multi_channel_dye", () -> new SimpleCraftingRecipeSerializer(MultiChannelDyeRecipe::new));
   public static final RegistryObject<RecipeSerializer<SmithingDialRecipe>> SMITHING_DIAL = ModRegistry.registerRecipeSerializer("smithing_dial", SmithingDialRecipe.Serializer::new);
   public static final RegistryObject<RecipeSerializer<SmithingEnchantmentRecipe>> SMITHING_ENCHANTMENT = ModRegistry.registerRecipeSerializer("smithing_enchantment", SmithingEnchantmentRecipe.Serializer::new);

   public static void init() {
   }
}
