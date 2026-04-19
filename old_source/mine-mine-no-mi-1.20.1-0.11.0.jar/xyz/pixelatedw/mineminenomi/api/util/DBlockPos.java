package xyz.pixelatedw.mineminenomi.api.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class DBlockPos extends HashBlockPos {
   private ResourceKey<Level> dimension;

   public DBlockPos(int x, int y, int z, ResourceKey<Level> dimension) {
      super(x, y, z);
      this.dimension = dimension;
   }

   public DBlockPos(long xyz, ResourceKey<Level> dimension) {
      this(m_121983_(xyz), m_122008_(xyz), m_122015_(xyz), dimension);
   }

   public ResourceKey<Level> getDimension() {
      return this.dimension;
   }
}
