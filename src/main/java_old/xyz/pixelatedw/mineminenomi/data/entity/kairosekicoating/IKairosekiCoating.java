package xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IKairosekiCoating extends INBTSerializable<CompoundTag> {
   boolean isFullyCoated();

   int getCoatingLevel();

   boolean addCoatingLevel(int var1);

   void setCoatingLevel(int var1);
}
