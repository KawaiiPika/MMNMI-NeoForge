package xyz.pixelatedw.mineminenomi.api.challenges;

import net.minecraft.core.BlockPos;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;

public class ChallengesBlockGenerators {
   public static final IBlockGenerator CUBE_XZ = (placer, level, origin) -> {
      int x0 = origin.m_123341_();
      int y0 = origin.m_123342_();
      int z0 = origin.m_123343_();
      int actualXSize = placer.getSizeX();
      int actualYSize = placer.getSizeY();
      int actualZSize = placer.getSizeZ();
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int y = actualYSize; y > 0; --y) {
         for(int x = -actualXSize; x < actualXSize; ++x) {
            for(int z = -actualZSize; z < actualZSize; ++z) {
               if (placer.isHollow()) {
                  boolean isWithinDistance = x < -actualXSize + placer.getThickness() || x >= actualXSize - placer.getThickness() || y < -actualYSize + placer.getThickness() || y >= actualYSize - placer.getThickness() || z < -actualZSize + placer.getThickness() || z >= actualZSize - placer.getThickness();
                  if (!isWithinDistance) {
                     continue;
                  }
               }

               mutpos.m_122178_(x0 + x, y0 + y, z0 + z);
               placer.placeNext(level, mutpos.m_7949_(), placer.getState(), placer.getFlag(), placer.getRule());
            }
         }
      }

   };
}
