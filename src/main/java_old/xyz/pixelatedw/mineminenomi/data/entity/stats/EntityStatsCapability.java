package xyz.pixelatedw.mineminenomi.data.entity.stats;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class EntityStatsCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IEntityStats> INSTANCE = CapabilityManager.get(new CapabilityToken<IEntityStats>() {
   });
   private final IEntityStats instance;

   public EntityStatsCapability(LivingEntity owner) {
      this.instance = new EntityStatsBase(owner);
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

   public static LazyOptional<IEntityStats> getLazy(LivingEntity entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IEntityStats> get(LivingEntity entity) {
      return getLazy(entity).resolve();
   }

   public static boolean canReceiveBounty(LivingEntity entity) {
      return (Boolean)get(entity).flatMap((props) -> props.getFaction()).map((fac) -> fac.canReceiveBounty(entity)).orElse(false);
   }
}
