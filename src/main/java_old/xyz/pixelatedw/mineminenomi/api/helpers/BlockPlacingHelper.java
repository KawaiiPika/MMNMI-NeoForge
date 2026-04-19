package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import xyz.pixelatedw.mineminenomi.api.util.HashBlockPos;

/** @deprecated */
@Deprecated
public class BlockPlacingHelper {
   private Set<BlockPos> blockList = new HashSet();

   public void setBlockList(Set<BlockPos> list) {
      this.blockList = list;
   }

   public void addBlockPos(BlockPos pos, int hash) {
      this.blockList.add(new HashBlockPos(pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), hash));
   }

   public void addBlockPos(int x, int y, int z, int hash) {
      this.blockList.add(new HashBlockPos(x, y, z, hash));
   }

   public void addBlockPos(double x, double y, double z, int hash) {
      this.blockList.add(new HashBlockPos((int)x, (int)y, (int)z, hash));
   }

   public Set<BlockPos> getBlockList() {
      return this.blockList;
   }

   public void clearList() {
      this.blockList = new HashSet();
   }
}
