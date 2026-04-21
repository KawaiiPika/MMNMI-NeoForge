package xyz.pixelatedw.mineminenomi.init;

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

    private static DeferredHolder<Block, Block> registerSimpleBlockItem(String name, Supplier<Block> block) {
        DeferredHolder<Block, Block> registeredBlock = ModRegistry.BLOCKS.register(name, block);
        ModRegistry.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    public static void init() {
        // Trigger class loading
    }
}
