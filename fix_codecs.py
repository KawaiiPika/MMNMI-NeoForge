with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'r') as f:
    content = f.read()

content = content.replace("ItemStack.CODEC.fieldOf(\"result\")", "ItemStack.STRICT_CODEC.fieldOf(\"result\")")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'w') as f:
    f.write(content)

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java', 'r') as f:
    content = f.read()

# Enchantment.CODEC might also be an issue if it doesn't exist or is different?
# Actually, the exception is: `NullPointerException: Trying to access unbound value: ResourceKey[minecraft:recipe_serializer / mineminenomi:smithing_dial]`
# The issue is exactly what we discovered before: `getSerializer()` returns null during Datagen, because we used the `.getDelegate().get().value()` hack that we reverted to `null` or a hardcoded Registry.get!
