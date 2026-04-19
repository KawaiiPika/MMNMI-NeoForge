package xyz.pixelatedw.mineminenomi.api.blockgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public interface IBlockPlacer {
   int getSizeX();

   int getSizeY();

   int getSizeZ();

   boolean isHollow();

   int getThickness();

   BlockState getState();

   int getFlag();

   @Nullable BlockProtectionRule getRule();

   void generate(Level var1, BlockPos var2, IBlockGenerator var3);

   void placeNext(Level var1, BlockPos var2, BlockState var3, int var4, @Nullable BlockProtectionRule var5);
}
