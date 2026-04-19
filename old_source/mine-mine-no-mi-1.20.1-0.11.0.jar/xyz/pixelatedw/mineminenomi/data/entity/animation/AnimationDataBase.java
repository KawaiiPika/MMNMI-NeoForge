package xyz.pixelatedw.mineminenomi.data.entity.animation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class AnimationDataBase implements IAnimationData {
   private LivingEntity owner;
   private Animation<?, ?> currentAnimation;
   private LinkedList<Animation<?, ?>> animations = new LinkedList();

   public AnimationDataBase(LivingEntity owner) {
      this.owner = owner;
   }

   public void startAnimation(AnimationId<?> animationId, int duration, boolean force) {
      if (this.currentAnimation == null || force) {
         if (this.currentAnimation != null) {
            boolean alreadyHasAnim = this.animations.stream().anyMatch((a) -> a.getId().equals(animationId));
            if (!alreadyHasAnim) {
               this.animations.push(this.currentAnimation);
            }
         }

         this.currentAnimation = AnimationId.getRegisteredAnimation(animationId);
         if (this.currentAnimation != null) {
            this.currentAnimation.start(this.owner, duration);
         }

      }
   }

   public void stopAnimation(AnimationId<?> animationId) {
      if (this.currentAnimation != null && this.currentAnimation.getId().equals(animationId)) {
         this.currentAnimation.stop(this.owner);
         this.currentAnimation = null;
      } else {
         Optional<Animation<?, ?>> opt = this.animations.stream().filter((a) -> a.getId().equals(animationId)).findFirst();
         if (opt.isPresent()) {
            ((Animation)opt.get()).stop(this.owner);
         }
      }

   }

   public boolean isAnimationPlaying(AnimationId<?> animationId) {
      return this.currentAnimation != null && this.currentAnimation.getId().equals(animationId) && this.currentAnimation.isPlaying();
   }

   public @Nullable Animation<?, ?> getAnimation() {
      return this.currentAnimation != null && this.currentAnimation.isStopped() ? null : this.currentAnimation;
   }

   public void tickAnimations() {
      if (this.owner != null) {
         if (this.currentAnimation != null) {
            this.currentAnimation.tick(this.owner);
            if (this.currentAnimation.isStopped()) {
               this.currentAnimation = null;
            }
         }

         Iterator<Animation<?, ?>> iter = this.animations.iterator();

         while(iter.hasNext()) {
            Animation<?, ?> anim = (Animation)iter.next();
            anim.tick(this.owner);
            if (anim.isStopped()) {
               iter.remove();
            }
         }

         if (this.animations.size() > 0 && (this.currentAnimation == null || this.currentAnimation.isStopped())) {
            Animation<?, ?> last = (Animation)this.animations.pollFirst();
            if (last != null) {
               this.currentAnimation = last;
            }
         }

      }
   }

   public LinkedList<Animation<?, ?>> getAnimations() {
      return new LinkedList(this.animations);
   }

   public CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      return nbt;
   }

   public void deserializeNBT(CompoundTag nbt) {
   }
}
