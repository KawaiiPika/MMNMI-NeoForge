with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModRecipeProvider.java', 'r') as f:
    content = f.read()

import re

# `Ingredient.of` with TagKey needs to be `Ingredient.of(lookupProvider.lookupOrThrow(net.minecraft.core.registries.Registries.ITEM).getOrThrow(ModTags.Items...))`
# We did replace it previously but the git reset removed it!
content = content.replace("Ingredient.of(ModTags.Items.RANGED_ENCHANTABLE_BY_SMITHING)", "Ingredient.of(lookupProvider.lookupOrThrow(net.minecraft.core.registries.Registries.ITEM).getOrThrow(ModTags.Items.RANGED_ENCHANTABLE_BY_SMITHING))")
content = content.replace("Ingredient.of(ModTags.Items.MELEE_ENCHANTABLE_BY_SMITHING)", "Ingredient.of(lookupProvider.lookupOrThrow(net.minecraft.core.registries.Registries.ITEM).getOrThrow(ModTags.Items.MELEE_ENCHANTABLE_BY_SMITHING))")

with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModRecipeProvider.java', 'w') as f:
    f.write(content)
