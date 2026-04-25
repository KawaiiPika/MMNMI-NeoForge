with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModRecipeProvider.java', 'r') as f:
    content = f.read()

content = content.replace("import xyz.pixelatedw.mineminenomi.init.ModEnchantments;", "")

with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModRecipeProvider.java', 'w') as f:
    f.write(content)
