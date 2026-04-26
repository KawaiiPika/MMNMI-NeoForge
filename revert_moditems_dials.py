import re

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModItems.java", "r") as f:
    content = f.read()

content = content.replace('ModRegistry.ITEMS.register("axe_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.AxeDialItem(ModBlocks.AXE_DIAL.get()));', 'ModRegistry.ITEMS.register("axe_dial", () -> new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties()));')
content = content.replace('ModRegistry.ITEMS.register("milky_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.MilkyDialItem(ModBlocks.MILKY_DIAL.get()));', 'ModRegistry.ITEMS.register("milky_dial", () -> new net.minecraft.world.item.Item(new net.minecraft.world.item.Item.Properties()));')

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModItems.java", "w") as f:
    f.write(content)
