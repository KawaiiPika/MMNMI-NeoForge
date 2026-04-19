package xyz.pixelatedw.mineminenomi.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import xyz.pixelatedw.mineminenomi.world.features.configs.SizedBlockStateFeatureConfig;

public class BoulderFeature extends Feature<SizedBlockStateFeatureConfig> {
   public BoulderFeature() {
      super(SizedBlockStateFeatureConfig.CODEC);
   }

   public boolean m_142674_(FeaturePlaceContext<SizedBlockStateFeatureConfig> ctx) {
      BlockPos pos = ctx.m_159777_();

      WorldGenLevel world;
      for(world = ctx.m_159774_(); pos.m_123342_() > 3; pos = pos.m_7495_()) {
         if (!world.m_46859_(pos.m_7495_())) {
            BlockState blockstate = world.m_8055_(pos.m_7495_());
            if (m_159759_(blockstate) || m_159747_(blockstate)) {
               break;
            }
         }
      }

      if (pos.m_123342_() <= 3) {
         return false;
      } else {
         for(int l = 0; l < 3; ++l) {
            int i = ctx.m_225041_().m_188503_(((SizedBlockStateFeatureConfig)ctx.m_159778_()).size);
            int j = ctx.m_225041_().m_188503_(((SizedBlockStateFeatureConfig)ctx.m_159778_()).size);
            int k = ctx.m_225041_().m_188503_(((SizedBlockStateFeatureConfig)ctx.m_159778_()).size);
            float f = (float)(i + j + k) * 0.333F + 0.5F;

            for(BlockPos blockpos : BlockPos.m_121940_(pos.m_7918_(-i, -j, -k), pos.m_7918_(i, j, k))) {
               if (blockpos.m_123331_(pos) <= (double)(f * f)) {
                  world.m_7731_(blockpos, ((SizedBlockStateFeatureConfig)ctx.m_159778_()).state, 4);
               }
            }

            pos = pos.m_7918_(-1 + ctx.m_225041_().m_188503_(2), -ctx.m_225041_().m_188503_(2), -1 + ctx.m_225041_().m_188503_(2));
         }

         return true;
      }
   }
}
