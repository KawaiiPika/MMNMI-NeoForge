package xyz.pixelatedw.mineminenomi.data.entity.nbtgoals;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class NBTGoalsCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<INBTGoals> INSTANCE = CapabilityManager.get(new CapabilityToken<INBTGoals>() {
   });
   private final INBTGoals instance = new NBTGoalsBase();

   public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
      return INSTANCE.orEmpty(cap, LazyOptional.of(() -> this.instance));
   }

   public CompoundTag serializeNBT() {
      return (CompoundTag)this.instance.serializeNBT();
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.instance.deserializeNBT(nbt);
   }

   public static Optional<INBTGoals> get(Mob entity) {
      return entity.getCapability(INSTANCE, (Direction)null).resolve();
   }
}
