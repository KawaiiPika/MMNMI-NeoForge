package xyz.pixelatedw.mineminenomi.data.entity.ability;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IAbilityData> INSTANCE = CapabilityManager.get(new CapabilityToken<IAbilityData>() {
   });
   private final IAbilityData instance;

   public AbilityCapability(LivingEntity owner) {
      this.instance = new AbilityDataBase(owner);
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

   public static LazyOptional<IAbilityData> getLazy(LivingEntity entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IAbilityData> get(LivingEntity entity) {
      return getLazy(entity).resolve();
   }

   public static void tick(LivingEntity entity) {
      get(entity).ifPresent((data) -> {
         for(IAbility ability : data.getEquippedAndPassiveAbilities()) {
            try {
               ability.tick(entity);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }

      });
   }

   public static boolean hasUnlockedAbility(LivingEntity entity, AbilityCore<?> core) {
      return (Boolean)get(entity).map((props) -> props.hasUnlockedAbility(core)).orElse(false);
   }

   @Nullable
   public static <T extends IAbility> T getEquippedAbility(LivingEntity entity, AbilityCore<T> core) {
      return (T)(get(entity).map((props) -> props.getEquippedAbility(core)).orElse((Object)null));
   }
}
