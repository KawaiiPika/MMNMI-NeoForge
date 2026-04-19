package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class LargeLakesFeature extends Feature<SimpleBlockConfiguration> {
    public LargeLakesFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> ctx) {
        BlockPos pos = ctx.origin();
        WorldGenLevel world = ctx.level();

        while (pos.getY() > 9 && world.isEmptyBlock(pos)) {
            pos = pos.below();
        }

        if (pos.getY() <= 8) {
            return false;
        } else {
            int x0 = pos.getX();
            int y0 = pos.getY();
            int z0 = pos.getZ();
            int size = 4 + ctx.random().nextInt(4);
            int radiusXZ = size;
            int radiusY = Math.max(1, size / 2);
            BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

            for (double y = (double) (y0 - radiusY); y < (double) (y0 + radiusY); ++y) {
                for (double x = (double) (x0 - radiusXZ); x < (double) (x0 + radiusXZ); ++x) {
                    for (double z = (double) (z0 - radiusXZ); z < (double) (z0 + radiusXZ); ++z) {
                        double distance = ((double) x0 - x) * ((double) x0 - x) + ((double) z0 - z) * ((double) z0 - z) + ((double) y0 - y) * ((double) y0 - y);
                        if (distance < (double) (radiusXZ * radiusY)) {
                            mutpos.set(x, y, z);
                            if (mutpos.getY() <= pos.getY()) {
                                world.setBlock(mutpos, ctx.config().toPlace().getState(ctx.random(), mutpos), 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
