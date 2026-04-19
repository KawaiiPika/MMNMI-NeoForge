package xyz.pixelatedw.mineminenomi.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class LargeIceSpikeFeature extends Feature<ProbabilityFeatureConfiguration> {
   public LargeIceSpikeFeature() {
      super(ProbabilityFeatureConfiguration.f_67858_);
   }

   public boolean m_142674_(FeaturePlaceContext<ProbabilityFeatureConfiguration> ctx) {
      BlockPos pos = ctx.m_159777_();
      WorldGenLevel world = ctx.m_159774_();
      if (ctx.m_225041_().m_188501_() >= ((ProbabilityFeatureConfiguration)ctx.m_159778_()).f_67859_) {
         return false;
      } else {
         int x = pos.m_123341_() + ctx.m_225041_().m_188503_(64);
         int z = pos.m_123343_() + ctx.m_225041_().m_188503_(64);
         int y = world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, x, z);
         pos = new BlockPos(x, y, z);
         int i = ctx.m_225041_().m_188503_(20) + 12;
         int j = i / 4 + ctx.m_225041_().m_188503_(2);

         for(int k = 0; k < i; ++k) {
            float f = (1.0F - (float)k / (float)i) * (float)j;
            int l = Mth.m_14167_(f);

            for(int i1 = -l; i1 <= l; ++i1) {
               float f1 = (float)Mth.m_14040_(i1) - 0.25F;

               for(int j1 = -l; j1 <= l; ++j1) {
                  float f2 = (float)Mth.m_14040_(j1) - 0.25F;
                  if ((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f)) && (i1 != -l && i1 != l && j1 != -l && j1 != l || !(ctx.m_225041_().m_188501_() > 0.75F))) {
                     BlockState blockstate = world.m_8055_(pos.m_7918_(i1, k, j1));
                     Block block = blockstate.m_60734_();
                     if (blockstate.m_60795_() || m_159759_(blockstate) || block == Blocks.f_50127_ || block == Blocks.f_50126_) {
                        this.m_5974_(world, pos.m_7918_(i1, k, j1), Blocks.f_50354_.m_49966_());
                     }

                     if (k != 0 && l > 1) {
                        blockstate = world.m_8055_(pos.m_7918_(i1, -k, j1));
                        block = blockstate.m_60734_();
                        if (blockstate.m_60795_() || m_159759_(blockstate) || block == Blocks.f_50127_ || block == Blocks.f_50126_) {
                           this.m_5974_(world, pos.m_7918_(i1, -k, j1), Blocks.f_50354_.m_49966_());
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
