package xyz.pixelatedw.mineminenomi.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record SizedBlockStateFeatureConfig(BlockState state, int size) implements FeatureConfiguration {
    public static final Codec<SizedBlockStateFeatureConfig> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockState.CODEC.fieldOf("state").forGetter(SizedBlockStateFeatureConfig::state),
            Codec.intRange(0, 10).fieldOf("size").orElse(2).forGetter(SizedBlockStateFeatureConfig::size)
    ).apply(inst, SizedBlockStateFeatureConfig::new));
}
