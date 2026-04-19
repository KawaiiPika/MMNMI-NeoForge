package xyz.pixelatedw.mineminenomi.api.blockgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface IBlockGenerator {
   void generate(IBlockPlacer var1, Level var2, BlockPos var3);
}
