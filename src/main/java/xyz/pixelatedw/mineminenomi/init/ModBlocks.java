package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.blocks.*;
import xyz.pixelatedw.mineminenomi.items.*;
import xyz.pixelatedw.mineminenomi.items.dials.*;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.AirBlock;

import java.util.function.Supplier;
import java.util.function.Function;

public class ModBlocks {

    public static final DeferredHolder<Block, Block> OPE = registerSimpleBlockItem("ope", OpeBlock::new);
    public static final DeferredHolder<Block, Block> SUNA_SAND = registerSimpleBlockItem("suna_sand", SunaSandBlock::new);
    public static final DeferredHolder<Block, Block> WAX = registerSimpleBlockItem("wax_block", WaxBlock::new);
    public static final DeferredHolder<Block, Block> CANDY = registerSimpleBlockItem("candy_block", CandyBlock::new);
    public static final DeferredHolder<Block, Block> POISON = registerSimpleBlockItem("poison", PoisonBlock::new);
    public static final DeferredHolder<Block, Block> DEMON_POISON = registerSimpleBlockItem("demon_poison", DemonPoisonBlock::new);
    public static final DeferredHolder<Block, Block> STRING_WALL = registerSimpleBlockItem("string_wall", StringWallBlock::new);
    public static final DeferredHolder<Block, Block> BARRIER = registerSimpleBlockItem("barrier", BarrierBlock::new);
    public static final DeferredHolder<Block, Block> DARKNESS = registerSimpleBlockItem("darkness", DarknessBlock::new);
    public static final DeferredHolder<Block, Block> ORI_BARS = registerSimpleBlockItem("ori_bars", OriBarsBlock::new);
    public static final DeferredHolder<Block, Block> MUCUS = registerSimpleBlockItem("mucus", MucusBlock::new);
    public static final DeferredHolder<Block, Block> SPONGE_CAKE = registerSimpleBlockItem("sponge_cake", SpongeCakeBlock::new);
    public static final DeferredHolder<Block, Block> HARDENED_SNOW = registerSimpleBlockItem("hardened_snow", HardenedSnowBlock::new);
    public static final DeferredHolder<Block, Block> KAIROSEKI = registerSimpleBlockItem("kairoseki_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(net.minecraft.world.level.material.MapColor.METAL).strength(50.0F, 600.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> KAIROSEKI_ORE = registerSimpleBlockItem("kairoseki_ore", () -> new DropExperienceBlock(UniformInt.of(3, 5), BlockBehaviour.Properties.of().mapColor(net.minecraft.world.level.material.MapColor.METAL).instrument(net.minecraft.world.level.block.state.properties.NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 3.0F)));
    public static final DeferredHolder<Block, Block> DEEPSLATE_KAIROSEKI_ORE = registerSimpleBlockItem("deepslate_kairoseki_ore", () -> new DropExperienceBlock(UniformInt.of(3, 5), BlockBehaviour.Properties.ofFullCopy((BlockBehaviour)KAIROSEKI_ORE.get()).mapColor(net.minecraft.world.level.material.MapColor.DEEPSLATE).strength(6.0F, 3.0F).sound(net.minecraft.world.level.block.SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, Block> KAIROSEKI_BARS = registerSimpleBlockItem("kairoseki_bars", KairosekiBarsBlock::new);
    public static final DeferredHolder<Block, Block> SKY_BLOCK = registerSimpleBlockItem("sky_block", SkyBlockBlock::new);
    public static final DeferredHolder<Block, Block> WANTED_POSTER = registerBlockWithItem("wanted_poster", WantedPosterBlock::new, block -> new WantedPosterItem());
    public static final DeferredHolder<Block, Block> CUSTOM_SPAWNER = registerSimpleBlockItem("custom_spawner", CustomSpawnerBlock::new);
    public static final DeferredHolder<Block, Block> DEN_DEN_MUSHI = registerSimpleBlockItem("den_den_mushi", DenDenMushiBlock::new);
    public static final DeferredHolder<Block, Block> OIL_SPILL = registerSimpleBlockItem("oil_spill", OilSpillBlock::new);
    public static final DeferredHolder<Block, Block> DESIGN_BARREL = registerSimpleBlockItem("design_barrel", DesignBarrelBlock::new);
    public static final DeferredHolder<Block, Block> STRUCTURE_AIR = registerSimpleBlockItem("structure_air", () -> new AirBlock(BlockBehaviour.Properties.of().replaceable().noCollission().noOcclusion().noLootTable()));
    public static final DeferredHolder<Block, Block> CHALLENGE_AIR = registerSimpleBlockItem("challenge_air", () -> new AirBlock(BlockBehaviour.Properties.of().replaceable().noCollission().noOcclusion().lightLevel((state) -> 15).noLootTable()));
    public static final DeferredHolder<Block, Block> FLAG = registerBlockWithItem("flag", FlagBlock::new, block -> new FlagItem());
    public static final DeferredHolder<Block, Block> AXE_DIAL = registerBlockWithItem("axe_dial", DialBlock::new, AxeDialItem::new);
    public static final DeferredHolder<Block, Block> IMPACT_DIAL = registerBlockWithItem("impact_dial", DialBlock::new, ImpactDialItem::new);
    public static final DeferredHolder<Block, Block> FLASH_DIAL = registerBlockWithItem("flash_dial", () -> new DialBlock(BlockBehaviour.Properties.of().instabreak().lightLevel((state) -> 10)), FlashDialItem::new);
    public static final DeferredHolder<Block, Block> BREATH_DIAL = registerBlockWithItem("breath_dial", DialBlock::new, BreathDialItem::new);
    public static final DeferredHolder<Block, Block> EISEN_DIAL = registerBlockWithItem("eisen_dial", DialBlock::new, EisenDialItem::new);
    public static final DeferredHolder<Block, Block> MILKY_DIAL = registerBlockWithItem("milky_dial", DialBlock::new, MilkyDialItem::new);
    public static final DeferredHolder<Block, Block> FLAME_DIAL = registerBlockWithItem("flame_dial", DialBlock::new, FlameDialItem::new);
    public static final DeferredHolder<Block, Block> REJECT_DIAL = registerBlockWithItem("reject_dial", DialBlock::new, RejectDialItem::new);
    public static final DeferredHolder<Block, Block> MANGROVE_LOG = registerSimpleBlockItem("mangrove_log", MangroveLogBlock::new);
    public static final DeferredHolder<Block, Block> MANGROVE_WOOD = registerSimpleBlockItem("mangrove_wood", MangroveWoodBlock::new);
    public static final DeferredHolder<Block, Block> STRIPPED_MANGROVE_LOG = registerSimpleBlockItem("stripped_mangrove_log", StrippedMangroveLog::new);
    public static final DeferredHolder<Block, Block> STRIPPED_MANGROVE_WOOD = registerSimpleBlockItem("stripped_mangrove_wood", StrippedMangroveWood::new);
    public static final DeferredHolder<Block, Block> MANGROVE_LEAVES = registerSimpleBlockItem("mangrove_leaves", MangroveLeavesBlock::new);
    public static final DeferredHolder<Block, Block> MANGROVE_PLANKS = registerSimpleBlockItem("mangrove_planks", MangrovePlanksBlock::new);
    public static final DeferredHolder<Block, Block> PONEGLYPH = registerSimpleBlockItem("poneglyph", PoneglyphBlock::new);
    public static final DeferredHolder<Block, Block> TANGERINE_CROP = registerSimpleBlockItem("tangerine_crop", TangerineCropsBlock::new);

    public static DeferredHolder<Block, Block> registerSimpleBlockItem(String name, Supplier<Block> block) {
        DeferredHolder<Block, Block> registeredBlock = ModRegistry.BLOCKS.register(name, block);
        ModRegistry.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    public static DeferredHolder<Block, Block> registerBlockWithItem(String name, Supplier<Block> blockSupplier, Function<Block, Item> itemFactory) {
        DeferredHolder<Block, Block> registeredBlock = ModRegistry.BLOCKS.register(name, blockSupplier);
        ModRegistry.ITEMS.register(name, () -> itemFactory.apply(registeredBlock.get()));
        return registeredBlock;
    }

    public static void init() {
    }
}
