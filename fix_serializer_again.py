with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'r') as f:
    content = f.read()

content = content.replace("(RecipeSerializer<?>) net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"smithing_dial\"))", "new SmithingDialRecipe.Serializer()")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'w') as f:
    f.write(content)

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java', 'r') as f:
    content2 = f.read()

content2 = content2.replace("(RecipeSerializer<?>) net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"smithing_enchantment\"))", "new SmithingEnchantmentRecipe.Serializer()")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java', 'w') as f:
    f.write(content2)

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/MultiChannelDyeRecipe.java', 'r') as f:
    content3 = f.read()

content3 = content3.replace("(RecipeSerializer<?>) net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"crafting_special_multi_channel_dye\"))", "new net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer<>(MultiChannelDyeRecipe::new)")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/MultiChannelDyeRecipe.java', 'w') as f:
    f.write(content3)
