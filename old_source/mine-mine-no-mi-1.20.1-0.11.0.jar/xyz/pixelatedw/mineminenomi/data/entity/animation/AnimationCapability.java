package xyz.pixelatedw.mineminenomi.data.entity.animation;

import java.util.Optional;
import net.minecraft.client.model.EntityModel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;

public class AnimationCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IAnimationData> INSTANCE = CapabilityManager.get(new CapabilityToken<IAnimationData>() {
   });
   private final IAnimationData instance;

   public AnimationCapability(LivingEntity owner) {
      this.instance = new AnimationDataBase(owner);
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

   public static LazyOptional<IAnimationData> getLazy(LivingEntity entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IAnimationData> get(LivingEntity entity) {
      return getLazy(entity).resolve();
   }

   public static void tick(LivingEntity entity) {
      get(entity).ifPresent((data) -> data.tickAnimations());
   }

   public static <E extends LivingEntity, M extends EntityModel<E>> @Nullable Animation<LivingEntity, EntityModel<LivingEntity>> getAnimation(LivingEntity entity) {
      return (Animation)get(entity).map((props) -> props.getAnimation()).orElse((Object)null);
   }
}
