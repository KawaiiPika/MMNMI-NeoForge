package xyz.pixelatedw.mineminenomi.data.entity.animation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class AnimationData {
   private Animation<?, ?> currentAnimation;
   private LinkedList<Animation<?, ?>> animations = new LinkedList<>();

   public AnimationData() {
   }

   public void startAnimation(LivingEntity owner, AnimationId<?> animationId, int duration, boolean force) {
      if (this.currentAnimation == null || force) {
         if (this.currentAnimation != null) {
            boolean alreadyHasAnim = this.animations.stream().anyMatch(a -> a.getId().equals(animationId));
            if (!alreadyHasAnim) {
               this.animations.push(this.currentAnimation);
            }
         }

         this.currentAnimation = AnimationId.getRegisteredAnimation(animationId);
         if (this.currentAnimation != null) {
            this.currentAnimation.start(owner, duration);
         }
      }
   }

   public void stopAnimation(LivingEntity owner, AnimationId<?> animationId) {
      if (this.currentAnimation != null && this.currentAnimation.getId().equals(animationId)) {
         this.currentAnimation.stop(owner);
         this.currentAnimation = null;
      } else {
         Optional<Animation<?, ?>> opt = this.animations.stream().filter(a -> a.getId().equals(animationId)).findFirst();
         if (opt.isPresent()) {
            opt.get().stop(owner);
         }
      }
   }

   public boolean isAnimationPlaying(AnimationId<?> animationId) {
      return this.currentAnimation != null && this.currentAnimation.getId().equals(animationId) && this.currentAnimation.isPlaying();
   }

   public @Nullable Animation<?, ?> getAnimation() {
      return this.currentAnimation != null && this.currentAnimation.isStopped() ? null : this.currentAnimation;
   }

   public void tickAnimations(LivingEntity owner) {
      if (owner != null) {
         if (this.currentAnimation != null) {
            this.currentAnimation.tick(owner);
            if (this.currentAnimation.isStopped()) {
               this.currentAnimation = null;
            }
         }

         Iterator<Animation<?, ?>> iter = this.animations.iterator();
         while (iter.hasNext()) {
            Animation<?, ?> anim = iter.next();
            anim.tick(owner);
            if (anim.isStopped()) {
               iter.remove();
            }
         }

         if (this.animations.size() > 0 && (this.currentAnimation == null || this.currentAnimation.isStopped())) {
            Animation<?, ?> last = this.animations.pollFirst();
            if (last != null) {
               this.currentAnimation = last;
            }
         }
      }
   }

   public LinkedList<Animation<?, ?>> getAnimations() {
      return new LinkedList<>(this.animations);
   }
}
