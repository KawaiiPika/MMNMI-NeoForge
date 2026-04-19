package xyz.pixelatedw.mineminenomi.data.entity.carry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public interface ICarryData extends INBTSerializable<CompoundTag> {
   void startCarrying(@Nullable LivingEntity var1);

   void stopCarrying();

   @Nullable LivingEntity getCarry();

   boolean isCarrying();

   void setCarrier(@Nullable LivingEntity var1);

   @Nullable LivingEntity getCarrier();

   boolean isCarried();

   void setLeashedTo(LivingEntity var1);

   void dropLeash();

   @Nullable LivingEntity getLeashHolder();

   boolean isLeashed();

   boolean canBeLeashed(LivingEntity var1);
}
