package xyz.pixelatedw.mineminenomi.data.entity.gcd;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IGCDData extends INBTSerializable<CompoundTag> {
   void startGCD();

   boolean isOnGCD();

   void tickGCD();

   int getCurrentGCD();

   int getDefaultGCD();
}
