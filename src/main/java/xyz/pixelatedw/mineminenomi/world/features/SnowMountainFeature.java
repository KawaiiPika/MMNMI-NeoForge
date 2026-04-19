package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class SnowMountainFeature extends Feature<ProbabilityFeatureConfiguration> {
    public SnowMountainFeature(Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> ctx) {
        BlockPos pos = ctx.origin();
        WorldGenLevel world = ctx.level();
        if (ctx.random().nextFloat() >= ctx.config().probability) {
            return false;
        } else {
            int x0 = pos.getX();
            int z0 = pos.getZ();
            int y0 = world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x0, z0);
            int radius = 1000;
            int check = radius;
            BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos(x0, y0, z0);
            int minY = 0;
            int maxY = 0;

            for (int x = 7; x < 64; x += 16) {
                for (int z = 7; z < 64; z += 16) {
                    mutpos.set(x0 + x, y0, z0 + z);
                    int landHeight = world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mutpos.getX(), mutpos.getZ());
                    if (minY == 0) minY = landHeight;
                    if (maxY == 0) maxY = landHeight;
                    minY = Math.min(minY, landHeight);
                    maxY = Math.max(maxY, landHeight);
                }
            }

            double heightDiff = (double) Math.abs(maxY - minY);
            mutpos.set(x0, y0, z0);

            for (int y = 0; y < 60; ++y) {
                for (int x = x0 - radius; x <= x0 + radius; ++x) {
                    for (int z = z0 - radius; z <= z0 + radius; ++z) {
                        double distance = (double) ((x0 - x) * (x0 - x) + (z0 - z) * (z0 - z));
                        if (y > 20 && y < 40) check = 950;
                        if (y > 40 && y < 60) check = 900;

                        if (distance < (double) check) {
                            mutpos.set(x, (double) y0 - heightDiff + (double) y, z);
                            BlockState blockstate = world.getBlockState(mutpos);
                            Block block = blockstate.getBlock();
                            if (blockstate.isAir() || isDirt(blockstate) || block == Blocks.DIRT || block == Blocks.SNOW || block == Blocks.GRASS_BLOCK) {
                                world.setBlock(mutpos, Blocks.DIRT.defaultBlockState(), 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
