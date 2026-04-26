package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class MaguHelper {
   public static void generateLavaPool(Level level, BlockPos pos, int size) {
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                if (x*x + z*z <= size*size) {
                    BlockPos p = pos.offset(x, 0, z);
                    if (level.isEmptyBlock(p) || level.getBlockState(p).isSolidRender(level, p)) {
                        level.setBlockAndUpdate(p, Blocks.LAVA.defaultBlockState());
                    }
                }
            }
        }
   }
}
