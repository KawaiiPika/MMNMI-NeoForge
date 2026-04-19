package xyz.pixelatedw.mineminenomi.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class SizedBlockStateFeatureConfig implements FeatureConfiguration {
   public static final Codec<SizedBlockStateFeatureConfig> CODEC = RecordCodecBuilder.create((codec) -> codec.group(BlockState.f_61039_.fieldOf("state").forGetter((config) -> config.state), Codec.intRange(0, 10).orElse(2).fieldOf("size").forGetter((config) -> config.size)).apply(codec, SizedBlockStateFeatureConfig::new));
   public final BlockState state;
   public final int size;

   public SizedBlockStateFeatureConfig(BlockState state, int size) {
      this.state = state;
      this.size = size;
   }
}
