package xyz.pixelatedw.mineminenomi.data.entity.gcd;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class GCDCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IGCDData> INSTANCE = CapabilityManager.get(new CapabilityToken<IGCDData>() {
   });
   private final IGCDData instance;

   public GCDCapability(LivingEntity owner) {
      this.instance = new GCDBase(owner);
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

   public static LazyOptional<IGCDData> getLazy(LivingEntity entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IGCDData> get(LivingEntity entity) {
      return getLazy(entity).resolve();
   }

   public static ImmutablePair<Integer, Integer> getGCD(LivingEntity entity) {
      return (ImmutablePair)get(entity).map((gcd) -> ImmutablePair.of(gcd.getCurrentGCD(), gcd.getDefaultGCD())).orElse(ImmutablePair.of((Object)null, (Object)null));
   }

   public static boolean isOnGCD(LivingEntity entity) {
      return (Boolean)get(entity).map((gcd) -> gcd.isOnGCD()).orElse(false);
   }

   public static void startGCD(LivingEntity entity) {
      get(entity).ifPresent(IGCDData::startGCD);
   }

   public static void tickGCD(LivingEntity entity) {
      get(entity).ifPresent(IGCDData::tickGCD);
   }
}
