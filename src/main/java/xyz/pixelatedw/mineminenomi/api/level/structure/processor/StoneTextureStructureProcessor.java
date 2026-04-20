package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

import javax.annotation.Nullable;

public class StoneTextureStructureProcessor extends StructureProcessor {
    public static final MapCodec<StoneTextureStructureProcessor> MAP_CODEC = MapCodec.unit(StoneTextureStructureProcessor::new);
    public static final Codec<StoneTextureStructureProcessor> CODEC = MAP_CODEC.codec();

    private static final Block[] STONE_BLOCKS = new Block[]{Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE};
    private static final Block[] COBBLESTONE_BLOCKS = new Block[]{Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE};

    public StoneTextureStructureProcessor() {}

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos pos1, BlockPos pos2, StructureTemplate.StructureBlockInfo info1, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        BlockPos pos = info2.pos();
        RandomSource rng = RandomSource.create(Mth.getSeed(pos));
        Block block = info1.state().getBlock();

        if (block == Blocks.STONE) {
            BlockState newState = STONE_BLOCKS[rng.nextInt(STONE_BLOCKS.length)].defaultBlockState();
            return new StructureTemplate.StructureBlockInfo(pos, newState, info2.nbt());
        } else if (block == Blocks.COBBLESTONE) {
            BlockState newState = COBBLESTONE_BLOCKS[rng.nextInt(COBBLESTONE_BLOCKS.length)].defaultBlockState();
            return new StructureTemplate.StructureBlockInfo(pos, newState, info2.nbt());
        }

        return info2;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructures.STONE_TEXTURE_PROCESSOR.get();
    }
}
