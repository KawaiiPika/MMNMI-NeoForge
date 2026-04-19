package xyz.pixelatedw.mineminenomi.data.entity.combat;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CombatCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<ICombatData> INSTANCE = CapabilityManager.get(new CapabilityToken<ICombatData>() {
   });
   private final ICombatData instance;

   public CombatCapability(LivingEntity owner) {
      this.instance = new CombatDataBase(owner);
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

   public static LazyOptional<ICombatData> getLazy(LivingEntity entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<ICombatData> get(LivingEntity entity) {
      return getLazy(entity).resolve();
   }

   public static void tick(LivingEntity entity) {
      getLazy(entity).ifPresent((data) -> data.tick());
   }
}
