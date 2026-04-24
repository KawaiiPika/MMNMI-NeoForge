with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModItems.java", "r") as f:
    content = f.read()

content = content.replace("package xyz.pixelatedw.mineminenomi.init;", "package xyz.pixelatedw.mineminenomi.init;\nimport net.neoforged.neoforge.registries.DeferredHolder;\nimport xyz.pixelatedw.mineminenomi.blocks.DialBlock;")
content = content.replace("xyz.pixelatedw.mineminenomi.items.dials.AxeDialItem", "net.minecraft.world.item.Item")
content = content.replace("xyz.pixelatedw.mineminenomi.items.dials.MilkyDialItem", "net.minecraft.world.item.Item")

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModItems.java", "w") as f:
    f.write(content)

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModBlocks.java", "r") as f:
    content = f.read()

content = content.replace("package xyz.pixelatedw.mineminenomi.init;", "package xyz.pixelatedw.mineminenomi.init;\nimport net.neoforged.neoforge.registries.DeferredHolder;\nimport xyz.pixelatedw.mineminenomi.blocks.DialBlock;\nimport net.minecraft.world.level.block.Block;")

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModBlocks.java", "w") as f:
    f.write(content)
