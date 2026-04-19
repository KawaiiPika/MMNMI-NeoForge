package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SnowFloorFeature extends Feature<NoneFeatureConfiguration> {
    public SnowFloorFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        BlockPos pos = ctx.origin();
        WorldGenLevel world = ctx.level();
        BlockPos.MutableBlockPos mutpos1 = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutpos2 = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int x = pos.getX() + i;
                int z = pos.getZ() + j;
                int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
                mutpos1.set(x, y, z);
                mutpos2.set(mutpos1).move(Direction.DOWN, 1);
                Biome biome = world.getBiome(mutpos1).value();
                
                if (biome.shouldFreeze(world, mutpos2)) {
                    world.setBlock(mutpos2, Blocks.ICE.defaultBlockState(), 2);
                }

                if (biome.shouldSnow(world, mutpos1)) {
                    world.setBlock(mutpos1, Blocks.SNOW.defaultBlockState(), 2);
                    BlockState blockstate = world.getBlockState(mutpos2);
                    if (blockstate.hasProperty(SnowyDirtBlock.SNOWY)) {
                        world.setBlock(mutpos2, blockstate.setValue(SnowyDirtBlock.SNOWY, true), 2);
                    }
                }
            }
        }

        return true;
    }
}
