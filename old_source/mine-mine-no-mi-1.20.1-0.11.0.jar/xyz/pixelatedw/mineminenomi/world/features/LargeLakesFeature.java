package xyz.pixelatedw.mineminenomi.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class LargeLakesFeature extends Feature<SimpleBlockConfiguration> {
   public LargeLakesFeature() {
      super(SimpleBlockConfiguration.f_68068_);
   }

   public boolean m_142674_(FeaturePlaceContext<SimpleBlockConfiguration> ctx) {
      BlockPos pos = ctx.m_159777_();

      WorldGenLevel world;
      for(world = ctx.m_159774_(); pos.m_123342_() > 9 && world.m_46859_(pos); pos = pos.m_7495_()) {
      }

      if (pos.m_123342_() <= 8) {
         return false;
      } else {
         int x0 = pos.m_123341_();
         int y0 = pos.m_123342_();
         int z0 = pos.m_123343_();
         int size = 4 + ctx.m_225041_().m_188503_(4);
         int radiusXZ = size;
         int radiusY = Math.max(1, size / 2);
         BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

         for(double y = (double)(y0 - radiusY); y < (double)(y0 + radiusY); ++y) {
            for(double x = (double)(x0 - radiusXZ); x < (double)(x0 + radiusXZ); ++x) {
               for(double z = (double)(z0 - radiusXZ); z < (double)(z0 + radiusXZ); ++z) {
                  double distance = ((double)x0 - x) * ((double)x0 - x) + ((double)z0 - z) * ((double)z0 - z) + ((double)y0 - y) * ((double)y0 - y);
                  if (distance < (double)(radiusXZ * radiusY)) {
                     mutpos.m_122169_(x, y, z);
                     if (mutpos.m_123342_() <= pos.m_123342_()) {
                        world.m_7731_(mutpos, ((SimpleBlockConfiguration)ctx.m_159778_()).f_68069_().m_213972_(ctx.m_225041_(), pos), 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
