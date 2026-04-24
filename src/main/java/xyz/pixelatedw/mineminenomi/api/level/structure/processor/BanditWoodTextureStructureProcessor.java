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

import org.jspecify.annotations.Nullable;

public class BanditWoodTextureStructureProcessor extends StructureProcessor {
    public static final MapCodec<BanditWoodTextureStructureProcessor> MAP_CODEC = MapCodec.unit(BanditWoodTextureStructureProcessor::new);
    public static final Codec<BanditWoodTextureStructureProcessor> CODEC = MAP_CODEC.codec();
    
    private static final Block[] LOG_BLOCKS = new Block[]{Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG};
    private static final Block[] FENCE_BLOCKS = new Block[]{Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE};
    private static final Block[] SLAB_BLOCKS = new Block[]{Blocks.OAK_SLAB, Blocks.SPRUCE_SLAB};


    @Override
    public StructureTemplate.@Nullable StructureBlockInfo process(LevelReader level, BlockPos pos1, BlockPos pos2, StructureTemplate.StructureBlockInfo info1, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        BlockPos pos = info2.pos();
        RandomSource rng = RandomSource.create(Mth.getSeed(pos));
        Block block = info1.state().getBlock();
        
        if (block == Blocks.OAK_LOG) {
            BlockState newState = LOG_BLOCKS[rng.nextInt(LOG_BLOCKS.length)].defaultBlockState();
            return new StructureTemplate.StructureBlockInfo(pos, newState, info2.nbt());
        } else if (block == Blocks.OAK_SLAB) {
            BlockState newState = SLAB_BLOCKS[rng.nextInt(SLAB_BLOCKS.length)].defaultBlockState();
            return new StructureTemplate.StructureBlockInfo(pos, newState, info2.nbt());
        } else if (block == Blocks.OAK_FENCE) {
            BlockState newState = FENCE_BLOCKS[rng.nextInt(FENCE_BLOCKS.length)].defaultBlockState();
            return new StructureTemplate.StructureBlockInfo(pos, newState, info2.nbt());
        }
        
        return info2;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructures.BANDIT_WOOD_TEXTURE_PROCESSOR.get();
    }
}
