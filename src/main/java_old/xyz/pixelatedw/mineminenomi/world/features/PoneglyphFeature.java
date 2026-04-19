package xyz.pixelatedw.mineminenomi.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class PoneglyphFeature extends Feature<NoneFeatureConfiguration> {
   public PoneglyphFeature() {
      super(NoneFeatureConfiguration.f_67815_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      BlockPos pos = ctx.m_159777_();
      boolean isChance = ctx.m_159774_().m_213780_().m_188500_() * (double)100.0F < ServerConfig.getChanceForPoneglyphSpawn();
      if (!isChance) {
         return false;
      } else {
         int size = 2 + ctx.m_225041_().m_188503_(3);
         int spawnChecks = 0;

         for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
               for(int k = 0; k < size; ++k) {
                  BlockPos blockpos = pos.m_7918_(i, j, k);
                  BlockState state = ctx.m_159774_().m_8055_(blockpos);
                  if (!m_159747_(state)) {
                     return false;
                  }

                  ++spawnChecks;
               }
            }
         }

         if (spawnChecks < size * size * size) {
            return false;
         } else {
            for(int i = 0; i < size; ++i) {
               for(int j = 0; j < size; ++j) {
                  for(int k = 0; k < size; ++k) {
                     BlockPos pos2 = pos.m_7918_(i, j, k);
                     ctx.m_159774_().m_7731_(pos2, ((Block)ModBlocks.PONEGLYPH.get()).m_49966_(), 2);
                  }
               }
            }

            return true;
         }
      }
   }
}
