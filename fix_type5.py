with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'r') as f:
    content = f.read()

content = content.replace("xyz.pixelatedw.mineminenomi.init.ModRecipes.SMITHING_DIAL.get()", "null")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java', 'w') as f:
    f.write(content)

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java', 'r') as f:
    content2 = f.read()

content2 = content2.replace("xyz.pixelatedw.mineminenomi.init.ModRecipes.SMITHING_ENCHANTMENT.get()", "null")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java', 'w') as f:
    f.write(content2)

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/MultiChannelDyeRecipe.java', 'r') as f:
    content3 = f.read()

content3 = content3.replace("xyz.pixelatedw.mineminenomi.init.ModRecipes.MULTI_CHANNEL_ARMOR_DYE.get()", "null")

with open('src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/MultiChannelDyeRecipe.java', 'w') as f:
    f.write(content3)
