package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

import javax.annotation.Nullable;

public class IgnoreWaterloggingStructureProcessor extends StructureProcessor {
    public static final MapCodec<IgnoreWaterloggingStructureProcessor> MAP_CODEC = MapCodec.unit(IgnoreWaterloggingStructureProcessor::new);
    public static final Codec<IgnoreWaterloggingStructureProcessor> CODEC = MAP_CODEC.codec();

    public IgnoreWaterloggingStructureProcessor() {}

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos pos1, BlockPos pos2, StructureTemplate.StructureBlockInfo info1, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        BlockState state = info2.state();
        if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, false);
        }
        return new StructureTemplate.StructureBlockInfo(info2.pos(), state, info2.nbt());
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructures.IGNORE_WATERLOGGING_PROCESSOR.get();
    }
}
