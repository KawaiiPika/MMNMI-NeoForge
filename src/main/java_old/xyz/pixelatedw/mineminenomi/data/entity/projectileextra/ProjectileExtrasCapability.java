package xyz.pixelatedw.mineminenomi.data.entity.projectileextra;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ProjectileExtrasCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IProjectileExtras> INSTANCE = CapabilityManager.get(new CapabilityToken<IProjectileExtras>() {
   });
   private final IProjectileExtras instance;

   public ProjectileExtrasCapability(Projectile owner) {
      this.instance = new ProjectileExtrasBase(owner);
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

   public static LazyOptional<IProjectileExtras> getLazy(Projectile entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IProjectileExtras> get(Projectile entity) {
      return getLazy(entity).resolve();
   }
}
