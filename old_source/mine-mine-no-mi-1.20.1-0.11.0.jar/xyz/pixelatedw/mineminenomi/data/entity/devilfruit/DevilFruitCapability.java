package xyz.pixelatedw.mineminenomi.data.entity.devilfruit;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class DevilFruitCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IDevilFruit> INSTANCE = CapabilityManager.get(new CapabilityToken<IDevilFruit>() {
   });
   private final IDevilFruit instance;

   public DevilFruitCapability(LivingEntity owner) {
      this.instance = new DevilFruitBase(owner);
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

   public static LazyOptional<IDevilFruit> getLazy(LivingEntity entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IDevilFruit> get(LivingEntity entity) {
      return getLazy(entity).resolve();
   }

   public static boolean hasDevilFruit(LivingEntity entity, RegistryObject<AkumaNoMiItem> fruit) {
      return (Boolean)get(entity).map((props) -> props.hasDevilFruit(fruit)).orElse(false);
   }

   public static boolean hasDevilFruit(LivingEntity entity, AkumaNoMiItem fruit) {
      return (Boolean)get(entity).map((props) -> props.hasDevilFruit(fruit)).orElse(false);
   }
}
