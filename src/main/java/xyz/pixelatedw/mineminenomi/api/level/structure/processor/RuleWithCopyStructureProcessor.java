package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

import org.jspecify.annotations.Nullable;
import java.util.List;
import java.util.Map;

public class RuleWithCopyStructureProcessor extends StructureProcessor {
    public static final MapCodec<RuleWithCopyStructureProcessor> MAP_CODEC = ProcessorRule.CODEC.listOf().fieldOf("rules")
            .xmap(RuleWithCopyStructureProcessor::new, processor -> processor.rules);
    public static final Codec<RuleWithCopyStructureProcessor> CODEC = MAP_CODEC.codec();

    private final List<ProcessorRule> rules;

    public RuleWithCopyStructureProcessor(List<ProcessorRule> rules) {
        this.rules = rules;
    }


    @Override
    public StructureTemplate.@Nullable StructureBlockInfo process(LevelReader level, BlockPos pos1, BlockPos pos2, StructureTemplate.StructureBlockInfo info1, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        RandomSource rng = RandomSource.create(Mth.getSeed(info2.pos()));
        BlockState worldState = level.getBlockState(info2.pos());

        for (ProcessorRule rule : this.rules) {
            if (rule.test(info2.state(), worldState, info1.pos(), info2.pos(), pos2, rng)) {
                BlockState newState = rule.getOutputState();
                
                // Copy properties from original structure block if they exist in the new state
                for (Map.Entry<Property<?>, Comparable<?>> entry : info1.state().getValues().entrySet()) {
                    if (newState.hasProperty(entry.getKey())) {
                        newState = copyProperty(info1.state(), newState, entry.getKey());
                    }
                }

                CompoundTag nbt = rule.getOutputTag(rng, info2.nbt());
                return new StructureTemplate.StructureBlockInfo(info2.pos(), newState, nbt);
            }
        }

        return info2;
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
        return to.setValue(property, from.getValue(property));
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructures.RULE_WITH_COPY_PROCESSOR.get();
    }
}
