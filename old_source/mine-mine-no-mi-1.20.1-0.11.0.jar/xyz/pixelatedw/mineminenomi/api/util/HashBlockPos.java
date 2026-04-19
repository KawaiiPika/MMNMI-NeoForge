package xyz.pixelatedw.mineminenomi.api.util;

import net.minecraft.core.BlockPos;

public class HashBlockPos extends BlockPos {
   private int hash;

   public HashBlockPos(int x, int y, int z) {
      this(x, y, z, y * 31 + z + x);
   }

   public HashBlockPos(int x, int y, int z, int hash) {
      super(x, y, z);
      this.hash = hash;
   }

   public HashBlockPos(BlockPos pos, int hash) {
      this(pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), hash);
   }

   public static HashBlockPos of(BlockPos pos) {
      return new HashBlockPos(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
   }

   public int hashCode() {
      return this.hash;
   }
}
