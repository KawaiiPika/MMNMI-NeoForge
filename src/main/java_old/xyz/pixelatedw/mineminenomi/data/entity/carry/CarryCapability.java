package xyz.pixelatedw.mineminenomi.data.entity.carry;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CarryCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<ICarryData> INSTANCE = CapabilityManager.get(new CapabilityToken<ICarryData>() {
   });
   private final ICarryData instance;

   public CarryCapability(LivingEntity owner) {
      this.instance = new CarryDataBase(owner);
   }

   public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
      return INSTANCE.orEmpty(cap, LazyOptional.of(() -> this.instance));
   }

   public CompoundTag serializeNBT() {
      return (CompoundTag)this.instance.serializeNBT();
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.instance.deserializeNBT(nbt);
   }

   public static LazyOptional<ICarryData> getLazy(LivingEntity entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<ICarryData> get(LivingEntity entity) {
      return getLazy(entity).resolve();
   }
}
