package xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class KairosekiCoatingCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IKairosekiCoating> INSTANCE = CapabilityManager.get(new CapabilityToken<IKairosekiCoating>() {
   });
   private final IKairosekiCoating instance;

   public KairosekiCoatingCapability(Boat owner) {
      this.instance = new KairosekiCoatingBase(owner);
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

   public static LazyOptional<IKairosekiCoating> getLazy(Entity entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IKairosekiCoating> get(Entity entity) {
      return getLazy(entity).resolve();
   }
}
