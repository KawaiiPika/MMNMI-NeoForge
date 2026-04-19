package xyz.pixelatedw.mineminenomi.api.effects;

import net.minecraft.world.level.block.Block;

public interface IBlockOverlayEffect {
   Block getBlockOverlay(int var1, int var2);

   default float[] getOverlayColor() {
      return new float[]{1.0F, 1.0F, 1.0F, 1.0F};
   }
}
