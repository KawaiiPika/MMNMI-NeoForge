package xyz.pixelatedw.mineminenomi.init;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.blocks.DialBlock;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.blocks.DialBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredHolder<Block, Block> OPE = registerSimpleBlockItem("ope", xyz.pixelatedw.mineminenomi.blocks.OpeBlock::new);
    public static final DeferredHolder<Block, Block> SUNA_SAND = registerSimpleBlockItem("suna_sand", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> WAX = registerSimpleBlockItem("wax_block", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> CANDY = registerSimpleBlockItem("candy_block", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> POISON = registerSimpleBlockItem("poison", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> DEMON_POISON = registerSimpleBlockItem("demon_poison", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> KAIROSEKI = registerSimpleBlockItem("kairoseki_block", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> KAIROSEKI_ORE = registerSimpleBlockItem("kairoseki_ore", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> DEEPSLATE_KAIROSEKI_ORE = registerSimpleBlockItem("deepslate_kairoseki_ore", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> PONEGLYPH = registerSimpleBlockItem("poneglyph", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> DEN_DEN_MUSHI = registerSimpleBlockItem("den_den_mushi", () -> new Block(BlockBehaviour.Properties.of()));

    public static final DeferredHolder<Block, Block> TANGERINE_CROP = ModRegistry.BLOCKS.register("tangerine_crop", () -> new xyz.pixelatedw.mineminenomi.blocks.TangerineCropsBlock());

    private static DeferredHolder<Block, Block> registerSimpleBlockItem(String name, Supplier<Block> block) {
        DeferredHolder<Block, Block> registeredBlock = ModRegistry.BLOCKS.register(name, block);
        return registeredBlock;
    }




    // Dials


    // Dials
    public static final DeferredHolder<Block, Block> BREATH_DIAL = ModRegistry.BLOCKS.register("breath_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> EISEN_DIAL = ModRegistry.BLOCKS.register("eisen_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> FLAME_DIAL = ModRegistry.BLOCKS.register("flame_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> IMPACT_DIAL = ModRegistry.BLOCKS.register("impact_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> REJECT_DIAL = ModRegistry.BLOCKS.register("reject_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> AXE_DIAL = ModRegistry.BLOCKS.register("axe_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> MILKY_DIAL = ModRegistry.BLOCKS.register("milky_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());
    public static final DeferredHolder<Block, Block> FLASH_DIAL = ModRegistry.BLOCKS.register("flash_dial", () -> new xyz.pixelatedw.mineminenomi.blocks.DialBlock());

    public static void init() {
        for (DeferredHolder<Block, ? extends Block> blockEntry : ModRegistry.BLOCKS.getEntries()) {
            String name = blockEntry.getId().getPath();
            boolean hasItem = false;
            for (DeferredHolder<Item, ? extends Item> itemEntry : ModRegistry.ITEMS.getEntries()) {
                if (itemEntry.getId().getPath().equals(name)) {
                    hasItem = true;
                    break;
                }
            }
            if (!hasItem && !name.equals("tangerine_crop")) {
                ModRegistry.ITEMS.register(name, () -> new BlockItem(blockEntry.get(), new Item.Properties()));
            }
        }
    }
}
