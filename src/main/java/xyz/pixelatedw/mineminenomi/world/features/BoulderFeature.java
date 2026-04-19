package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import xyz.pixelatedw.mineminenomi.world.features.configs.SizedBlockStateFeatureConfig;

public class BoulderFeature extends Feature<SizedBlockStateFeatureConfig> {
    public BoulderFeature(Codec<SizedBlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SizedBlockStateFeatureConfig> ctx) {
        BlockPos pos = ctx.origin();
        WorldGenLevel world = ctx.level();
        RandomSource random = ctx.random();
        SizedBlockStateFeatureConfig config = ctx.config();

        while (pos.getY() > world.getMinBuildHeight() + 3) {
            if (!world.isEmptyBlock(pos.below())) {
                BlockState blockstate = world.getBlockState(pos.below());
                if (isDirt(blockstate) || isStone(blockstate)) {
                    break;
                }
            }
            pos = pos.below();
        }

        if (pos.getY() <= world.getMinBuildHeight() + 3) {
            return false;
        } else {
            for (int l = 0; l < 3; ++l) {
                int i = random.nextInt(config.size());
                int j = random.nextInt(config.size());
                int k = random.nextInt(config.size());
                float f = (float) (i + j + k) * 0.333F + 0.5F;

                for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-i, -j, -k), pos.offset(i, j, k))) {
                    if (blockpos.distSqr(pos) <= (double) (f * f)) {
                        world.setBlock(blockpos, config.state(), 4);
                    }
                }

                pos = pos.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
            }

            return true;
        }
    }
}
