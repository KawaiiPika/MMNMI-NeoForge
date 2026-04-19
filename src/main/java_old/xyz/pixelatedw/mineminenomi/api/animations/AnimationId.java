package xyz.pixelatedw.mineminenomi.api.animations;

import java.util.HashMap;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class AnimationId<A extends Animation<?, ?>> {
   public static final HashMap<ResourceLocation, AnimationId<?>> REGISTERED_ANIMATIONS = new HashMap();
   private ResourceLocation id;
   private IAnimationFactory<A> factory;

   public AnimationId(ResourceLocation id, IAnimationFactory<A> factory) {
      this.id = id;
      this.factory = factory;
      REGISTERED_ANIMATIONS.put(id, this);
   }

   public static <A extends Animation<?, ?>> @Nullable A getRegisteredAnimation(AnimationId<A> id) {
      return id.factory.createAnimation(id);
   }

   public static <A extends Animation<?, ?>> @Nullable AnimationId<A> getRegisteredId(ResourceLocation id) {
      return (AnimationId)REGISTERED_ANIMATIONS.get(id);
   }

   public ResourceLocation getId() {
      return this.id;
   }

   @FunctionalInterface
   public interface IAnimationFactory<A extends Animation<?, ?>> {
      A createAnimation(AnimationId<A> var1);
   }
}
