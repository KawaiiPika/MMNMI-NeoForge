with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModRecipeProvider.java', 'r') as f:
    content = f.read()

import re

lines = content.split('\n')
new_lines = []
for line in lines:
    if "SmithingDialRecipeBuilder" not in line and "SmithingEnchantmentRecipeBuilder" not in line and "SpecialRecipeBuilder" not in line:
        new_lines.append(line)

with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModRecipeProvider.java', 'w') as f:
    f.write('\n'.join(new_lines))
