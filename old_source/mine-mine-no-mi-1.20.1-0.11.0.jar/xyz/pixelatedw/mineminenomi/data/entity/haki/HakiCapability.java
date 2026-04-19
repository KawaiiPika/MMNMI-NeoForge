package xyz.pixelatedw.mineminenomi.data.entity.haki;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class HakiCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IHakiData> INSTANCE = CapabilityManager.get(new CapabilityToken<IHakiData>() {
   });
   private final IHakiData instance;

   public HakiCapability(LivingEntity owner) {
      this.instance = new HakiDataBase(owner);
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

   public static LazyOptional<IHakiData> getLazy(LivingEntity entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IHakiData> get(LivingEntity entity) {
      return getLazy(entity).resolve();
   }
}
