package xyz.pixelatedw.mineminenomi.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import xyz.pixelatedw.mineminenomi.api.NuWorld;

public class SnowMountainFeature extends Feature<ProbabilityFeatureConfiguration> {
   public SnowMountainFeature() {
      super(ProbabilityFeatureConfiguration.f_67858_);
   }

   public boolean m_142674_(FeaturePlaceContext<ProbabilityFeatureConfiguration> ctx) {
      BlockPos pos = ctx.m_159777_();
      WorldGenLevel world = ctx.m_159774_();
      if (ctx.m_225041_().m_188501_() >= ((ProbabilityFeatureConfiguration)ctx.m_159778_()).f_67859_) {
         return false;
      } else {
         int x0 = pos.m_123341_();
         int z0 = pos.m_123343_();
         int y0 = world.m_6924_(Types.WORLD_SURFACE_WG, x0, z0);
         int radius = 1000;
         int check = radius;
         new BlockPos(x0, y0, z0);
         BiomeSource biomeSource = ctx.m_159775_().m_62218_();
         BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos(x0, y0, z0);
         int minY = 0;
         int maxY = 0;

         for(int x = 7; x < 64; x += 16) {
            for(int z = 7; z < 64; z += 16) {
               mutpos.m_122178_(x0 + x, y0, z0 + z);
               int landHeight = world.m_6924_(Types.WORLD_SURFACE_WG, mutpos.m_123341_(), mutpos.m_123343_());
               if (minY == 0) {
                  minY = landHeight;
               }

               if (maxY == 0) {
                  maxY = landHeight;
               }

               minY = Math.min(minY, landHeight);
               maxY = Math.max(maxY, landHeight);
            }
         }

         double heightDiff = (double)Math.abs(maxY - minY);
         mutpos.m_122178_(x0, y0, z0);

         for(int y = 0; y < 60; ++y) {
            for(int x = x0 - radius; x <= x0 + radius; ++x) {
               for(int z = z0 - radius; z <= z0 + radius; ++z) {
                  double distance = (double)((x0 - x) * (x0 - x) + (z0 - z) * (z0 - z));
                  if (y > 20 && y < 40) {
                     check = 950;
                  }

                  if (y > 40 && y < 60) {
                     check = 900;
                  }

                  if (distance < (double)check) {
                     mutpos.m_122169_((double)x, (double)y0 - heightDiff + (double)y, (double)z);
                     BlockState blockstate = world.m_8055_(mutpos);
                     Block block = blockstate.m_60734_();
                     if (blockstate.m_60795_() || m_159759_(blockstate) || block == Blocks.f_50127_ || block == Blocks.f_50125_ || block == Blocks.f_50126_) {
                        NuWorld.swapBlockData(world, mutpos, Blocks.f_50127_.m_49966_());
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
