import os

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModItems.java", "r") as f:
    content = f.read()

dial_registrations = """
    // Dials
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> BREATH_DIAL = ModRegistry.ITEMS.register("breath_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.BreathDialItem(ModBlocks.BREATH_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> EISEN_DIAL = ModRegistry.ITEMS.register("eisen_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.EisenDialItem(ModBlocks.EISEN_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> FLAME_DIAL = ModRegistry.ITEMS.register("flame_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.FlameDialItem(ModBlocks.FLAME_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> IMPACT_DIAL = ModRegistry.ITEMS.register("impact_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.ImpactDialItem(ModBlocks.IMPACT_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> REJECT_DIAL = ModRegistry.ITEMS.register("reject_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.RejectDialItem(ModBlocks.REJECT_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> AXE_DIAL = ModRegistry.ITEMS.register("axe_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.AxeDialItem(ModBlocks.AXE_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> MILKY_DIAL = ModRegistry.ITEMS.register("milky_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.MilkyDialItem(ModBlocks.MILKY_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> FLASH_DIAL = ModRegistry.ITEMS.register("flash_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.FlashDialItem(ModBlocks.FLASH_DIAL.get()));
"""

content = content.replace("public static void init() {", dial_registrations + "\n    public static void init() {")
with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModItems.java", "w") as f:
    f.write(content)

with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModBlocks.java", "r") as f:
    content = f.read()

dial_block_registrations = """
    // Dials
    public static final DeferredHolder<Block, Block> BREATH_DIAL = ModRegistry.BLOCKS.register("breath_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> EISEN_DIAL = ModRegistry.BLOCKS.register("eisen_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> FLAME_DIAL = ModRegistry.BLOCKS.register("flame_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> IMPACT_DIAL = ModRegistry.BLOCKS.register("impact_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> REJECT_DIAL = ModRegistry.BLOCKS.register("reject_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> AXE_DIAL = ModRegistry.BLOCKS.register("axe_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> MILKY_DIAL = ModRegistry.BLOCKS.register("milky_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> FLASH_DIAL = ModRegistry.BLOCKS.register("flash_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
"""

content = content.replace("public static void init() {", dial_block_registrations + "\n    public static void init() {")
with open("src/main/java/xyz/pixelatedw/mineminenomi/init/ModBlocks.java", "w") as f:
    f.write(content)
