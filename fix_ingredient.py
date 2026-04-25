with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModRecipeProvider.java', 'r') as f:
    content = f.read()

content = content.replace("Ingredient.of(lookupProvider.lookupOrThrow(net.minecraft.core.registries.Registries.ITEM).getOrThrow(ModTags.Items.RANGED_ENCHANTABLE_BY_SMITHING))", "Ingredient.of(ModTags.Items.RANGED_ENCHANTABLE_BY_SMITHING)")
content = content.replace("Ingredient.of(lookupProvider.lookupOrThrow(net.minecraft.core.registries.Registries.ITEM).getOrThrow(ModTags.Items.MELEE_ENCHANTABLE_BY_SMITHING))", "Ingredient.of(ModTags.Items.MELEE_ENCHANTABLE_BY_SMITHING)")

with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModRecipeProvider.java', 'w') as f:
    f.write(content)
