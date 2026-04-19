package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class LargeIceSpikeFeature extends Feature<ProbabilityFeatureConfiguration> {
    public LargeIceSpikeFeature(Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> ctx) {
        BlockPos pos = ctx.origin();
        WorldGenLevel world = ctx.level();
        RandomSource random = ctx.random();
        ProbabilityFeatureConfiguration config = ctx.config();

        if (random.nextFloat() >= config.probability) {
            return false;
        } else {
            int x = pos.getX() + random.nextInt(64);
            int z = pos.getZ() + random.nextInt(64);
            int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            pos = new BlockPos(x, y, z);
            int i = random.nextInt(20) + 12;
            int j = i / 4 + random.nextInt(2);

            for (int k = 0; k < i; ++k) {
                float f = (1.0F - (float) k / (float) i) * (float) j;
                int l = Mth.ceil(f);

                for (int i1 = -l; i1 <= l; ++i1) {
                    float f1 = (float) Mth.abs(i1) - 0.25F;

                    for (int j1 = -l; j1 <= l; ++j1) {
                        float f2 = (float) Mth.abs(j1) - 0.25F;
                        if ((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f)) && (i1 != -l && i1 != l && j1 != -l && j1 != l || !(random.nextFloat() > 0.75F))) {
                            BlockState blockstate = world.getBlockState(pos.offset(i1, k, j1));
                            Block block = blockstate.getBlock();
                            if (blockstate.isAir() || isDirt(blockstate) || block == Blocks.DIRT || block == Blocks.GRASS_BLOCK) {
                                this.setBlock(world, pos.offset(i1, k, j1), Blocks.PACKED_ICE.defaultBlockState());
                            }

                            if (k != 0 && l > 1) {
                                blockstate = world.getBlockState(pos.offset(i1, -k, j1));
                                block = blockstate.getBlock();
                                if (blockstate.isAir() || isDirt(blockstate) || block == Blocks.DIRT || block == Blocks.GRASS_BLOCK) {
                                    this.setBlock(world, pos.offset(i1, -k, j1), Blocks.PACKED_ICE.defaultBlockState());
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
