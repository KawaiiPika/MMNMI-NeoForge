package xyz.pixelatedw.mineminenomi.data.entity.hisolookout;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Animal;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class HisoLookoutCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IHisoLookoutData> INSTANCE = CapabilityManager.get(new CapabilityToken<IHisoLookoutData>() {
   });
   private final IHisoLookoutData instance = new HisoLookoutBase();

   public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
      return INSTANCE.orEmpty(cap, LazyOptional.of(() -> this.instance));
   }

   public CompoundTag serializeNBT() {
      return (CompoundTag)this.instance.serializeNBT();
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.instance.deserializeNBT(nbt);
   }

   public static LazyOptional<IHisoLookoutData> getLazy(Animal entity) {
      LazyOptional<IHisoLookoutData> props = entity.getCapability(INSTANCE, (Direction)null);
      props.ifPresent((data) -> data.setOwner(entity));
      return props;
   }

   public static Optional<IHisoLookoutData> get(Animal entity) {
      return getLazy(entity).resolve();
   }
}
