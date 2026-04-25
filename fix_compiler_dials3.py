with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModItems.java", "r") as f:
    content = f.read()

# I need to add DeferredHolder to ModItems
if "import net.neoforged.neoforge.registries.DeferredHolder;" not in content:
    content = content.replace("package xyz.pixelatedw.mineminenomi.init;", "package xyz.pixelatedw.mineminenomi.init;\nimport net.neoforged.neoforge.registries.DeferredHolder;\nimport xyz.pixelatedw.mineminenomi.blocks.DialBlock;")

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModItems.java", "w") as f:
    f.write(content)

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModBlocks.java", "r") as f:
    content = f.read()

if "import net.neoforged.neoforge.registries.DeferredHolder;" not in content:
    content = content.replace("package xyz.pixelatedw.mineminenomi.init;", "package xyz.pixelatedw.mineminenomi.init;\nimport net.neoforged.neoforge.registries.DeferredHolder;\nimport xyz.pixelatedw.mineminenomi.blocks.DialBlock;")

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModBlocks.java", "w") as f:
    f.write(content)
