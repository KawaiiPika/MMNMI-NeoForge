package xyz.pixelatedw.mineminenomi.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SnowFloorFeature extends Feature<NoneFeatureConfiguration> {
   public SnowFloorFeature() {
      super(NoneFeatureConfiguration.f_67815_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      BlockPos pos = ctx.m_159777_();
      WorldGenLevel world = ctx.m_159774_();
      BlockPos.MutableBlockPos mutpos1 = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos mutpos2 = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int x = pos.m_123341_() + i;
            int z = pos.m_123343_() + j;
            int y = world.m_6924_(Types.MOTION_BLOCKING, x, z);
            mutpos1.m_122178_(x, y, z);
            mutpos2.m_122190_(mutpos1).m_122175_(Direction.DOWN, 1);
            Biome biome = (Biome)world.m_204166_(mutpos1).get();
            if (biome.m_47480_(world, mutpos2, false)) {
               world.m_7731_(mutpos2, Blocks.f_50126_.m_49966_(), 2);
            }

            if (biome.m_47519_(world, mutpos1)) {
               world.m_7731_(mutpos1, Blocks.f_50127_.m_49966_(), 2);
               world.m_7731_(mutpos1.m_7494_(), Blocks.f_50125_.m_49966_(), 2);
               BlockState blockstate = world.m_8055_(mutpos2);
               if (blockstate.m_61138_(SnowyDirtBlock.f_56637_)) {
                  world.m_7731_(mutpos2, (BlockState)blockstate.m_61124_(SnowyDirtBlock.f_56637_, true), 2);
               }
            }
         }
      }

      return true;
   }
}
