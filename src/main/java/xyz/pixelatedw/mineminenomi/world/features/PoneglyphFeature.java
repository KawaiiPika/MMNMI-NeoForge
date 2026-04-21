package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class PoneglyphFeature extends Feature<NoneFeatureConfiguration> {
    public PoneglyphFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        BlockPos pos = ctx.origin();
        // Default chance since ServerConfig might not be ported
        double chance = 0.5; // 0.5% chance or similar
        
        if (ctx.random().nextDouble() * 100.0 > chance) {
            return false;
        } else {
            int size = 2 + ctx.random().nextInt(3);
            int spawnChecks = 0;

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    for (int k = 0; k < size; ++k) {
                        BlockPos blockpos = pos.offset(i, j, k);
                        BlockState state = ctx.level().getBlockState(blockpos);
                        if (!isStone(state)) {
                            return false;
                        }
                        ++spawnChecks;
                    }
                }
            }

            if (spawnChecks < size * size * size) {
                return false;
            } else {
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        for (int k = 0; k < size; ++k) {
                            BlockPos pos2 = pos.offset(i, j, k);
                            ctx.level().setBlock(pos2, ModBlocks.PONEGLYPH.get().defaultBlockState(), 2);
                        }
                    }
                }
                return true;
            }
        }
    }
}
