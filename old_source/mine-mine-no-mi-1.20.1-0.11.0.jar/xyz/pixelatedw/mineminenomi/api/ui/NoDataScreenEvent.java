package xyz.pixelatedw.mineminenomi.api.ui;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class NoDataScreenEvent implements INBTSerializable<CompoundTag> {
   public CompoundTag serializeNBT() {
      return null;
   }

   public void deserializeNBT(CompoundTag nbt) {
   }
}
