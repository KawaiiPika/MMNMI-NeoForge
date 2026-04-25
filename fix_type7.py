with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'r') as f:
    content = f.read()

content = content.replace("xyz.pixelatedw.mineminenomi.init.ModRecipes.SMITHING_DIAL.get()", "(RecipeSerializer<?>) net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"smithing_dial\"))")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'w') as f:
    f.write(content)

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java', 'r') as f:
    content2 = f.read()

content2 = content2.replace("xyz.pixelatedw.mineminenomi.init.ModRecipes.SMITHING_ENCHANTMENT.get()", "(RecipeSerializer<?>) net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"smithing_enchantment\"))")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java', 'w') as f:
    f.write(content2)

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/MultiChannelDyeRecipe.java', 'r') as f:
    content3 = f.read()

content3 = content3.replace("xyz.pixelatedw.mineminenomi.init.ModRecipes.MULTI_CHANNEL_ARMOR_DYE.get()", "(RecipeSerializer<?>) net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"crafting_special_multi_channel_dye\"))")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/MultiChannelDyeRecipe.java', 'w') as f:
    f.write(content3)

# Datagen hack for custom serializers: we must instantiate them directly if they are not in the registry yet.
# But `getSerializer()` must return the generic `RecipeSerializer<?>`. Since NeoForge has `DeferredRegister`,
# they are actually inside the DeferredHolder. We can just return the actual created serializer inside the class, or use a cached one in ModRecipes.

# Let's change ModRecipes.java to have static instances for datagen maybe? No.
