package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.util.DBlockPos;

public class NetworkHelper {
   public static FriendlyByteBuf writeDBlockPos(FriendlyByteBuf buf, DBlockPos pos) {
      buf.writeLong(pos.m_121878_());
      buf.m_236858_(pos.getDimension());
      return buf;
   }

   public static DBlockPos readDBlockPos(FriendlyByteBuf buf) {
      long xyz = buf.readLong();
      ResourceKey<Level> w = buf.m_236801_(Registries.f_256858_);
      return new DBlockPos(xyz, w);
   }
}
