with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'r') as f:
    content = f.read()

content = content.replace("return (RecipeSerializer<?>) net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"smithing_dial\"));", "return xyz.pixelatedw.mineminenomi.init.ModRecipes.SMITHING_DIAL.get();")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'w') as f:
    f.write(content)

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java', 'r') as f:
    content2 = f.read()

content2 = content2.replace("return (RecipeSerializer<?>) net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"smithing_enchantment\"));", "return xyz.pixelatedw.mineminenomi.init.ModRecipes.SMITHING_ENCHANTMENT.get();")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java', 'w') as f:
    f.write(content2)

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/MultiChannelDyeRecipe.java', 'r') as f:
    content3 = f.read()

content3 = content3.replace("return (RecipeSerializer<?>) net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"crafting_special_multi_channel_dye\"));", "return xyz.pixelatedw.mineminenomi.init.ModRecipes.MULTI_CHANNEL_ARMOR_DYE.get();")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/MultiChannelDyeRecipe.java', 'w') as f:
    f.write(content3)
