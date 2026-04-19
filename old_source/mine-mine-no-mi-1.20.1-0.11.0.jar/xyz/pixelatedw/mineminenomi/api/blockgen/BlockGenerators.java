package xyz.pixelatedw.mineminenomi.api.blockgen;

import net.minecraft.core.BlockPos;

public class BlockGenerators {
   public static final IBlockGenerator CUBE = (placer, level, origin) -> {
      int x0 = origin.m_123341_();
      int y0 = origin.m_123342_();
      int z0 = origin.m_123343_();
      int actualXSize = placer.getSizeX();
      int actualYSize = placer.getSizeY();
      int actualZSize = placer.getSizeZ();
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int y = actualYSize; y > -actualYSize; --y) {
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
   public static final IBlockGenerator SPHERE = (placer, level, origin) -> {
      int x0 = origin.m_123341_();
      int y0 = origin.m_123342_();
      int z0 = origin.m_123343_();
      int actualXZSize = placer.getSizeX();
      int actualYSize = placer.getSizeY();
      int maxDistance = (actualXZSize - placer.getThickness()) * (actualXZSize - placer.getThickness());
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(double y = (double)actualYSize; y >= (double)(-actualYSize); --y) {
         for(double x = (double)(-actualXZSize); x <= (double)actualXZSize; ++x) {
            for(double z = (double)(-actualXZSize); z <= (double)actualXZSize; ++z) {
               double distance = x * x + z * z + y * y;
               if ((!placer.isHollow() || !(distance < (double)maxDistance)) && !(distance >= (double)(actualXZSize * actualYSize))) {
                  mutpos.m_122169_((double)x0 + x, (double)y0 + y, (double)z0 + z);
                  placer.placeNext(level, mutpos.m_7949_(), placer.getState(), placer.getFlag(), placer.getRule());
               }
            }
         }
      }

   };
   public static final IBlockGenerator SPHERE_WITH_PLATFORM = (placer, level, origin) -> {
      int x0 = origin.m_123341_();
      int y0 = origin.m_123342_();
      int z0 = origin.m_123343_();
      int actualXZSize = placer.getSizeX();
      int actualYSize = placer.getSizeY();
      int maxDistance = (actualXZSize - placer.getThickness()) * (actualXZSize - placer.getThickness());
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(double y = (double)actualYSize; y >= (double)(-actualYSize); --y) {
         for(double x = (double)(-actualXZSize); x <= (double)actualXZSize; ++x) {
            for(double z = (double)(-actualXZSize); z <= (double)actualXZSize; ++z) {
               double distance = x * x + z * z + y * y;
               if ((!placer.isHollow() || !(distance < (double)maxDistance)) && !(distance >= (double)(actualXZSize * actualYSize))) {
                  mutpos.m_122169_((double)x0 + x, (double)y0 + y, (double)z0 + z);
                  int posDifference = origin.m_123342_() - mutpos.m_123342_();
                  boolean fallingProtection = Math.sqrt(mutpos.m_123331_(origin.m_6625_(posDifference))) > (double)2.5F;
                  if (fallingProtection) {
                     placer.placeNext(level, mutpos.m_7949_(), placer.getState(), placer.getFlag(), placer.getRule());
                  }
               }
            }
         }
      }

   };
}
