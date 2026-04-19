package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeAnimationStatePacket;

public class AnimationComponent extends AbilityComponent<IAbility> {
   private Function<LivingEntity, Boolean> stopCondition;
   private Function<LivingEntity, Boolean> pauseCondition;
   private AnimationId<?> animationId;
   private long startTime;
   private long animTick;
   private int animDuration;
   private boolean isInfinite;
   private Animation.State state;

   public AnimationComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.ANIMATION.get(), ability);
      this.state = Animation.State.STOP;
   }

   public AnimationComponent setPauseCondition(Function<LivingEntity, Boolean> condition) {
      this.pauseCondition = condition;
      return this;
   }

   public AnimationComponent setStopCondition(Function<LivingEntity, Boolean> condition) {
      this.stopCondition = condition;
      return this;
   }

   public <A extends Animation<?, ?>> void start(LivingEntity entity, AnimationId<A> animationId) {
      this.start(entity, animationId, -1);
   }

   public <A extends Animation<?, ?>> void start(LivingEntity entity, AnimationId<A> animationId, int animationDuration) {
      this.ensureIsRegistered();
      if (this.animDuration <= 0) {
         if (animationDuration > 0 || !this.isPlaying()) {
            this.animationId = animationId;
            this.startTime = entity.m_9236_().m_46467_();
            this.animTick = 0L;
            this.animDuration = animationDuration;
            this.isInfinite = this.animDuration == -1;
            this.state = Animation.State.PLAY;
            if (!entity.m_9236_().f_46443_) {
               ModNetwork.sendToAllTrackingAndSelf(new SChangeAnimationStatePacket(entity, this.getAbility(), animationId, this.state, this.animDuration), entity);
            }

         }
      }
   }

   public void stop(LivingEntity entity) {
      if (!this.isStopped()) {
         this.animTick = 0L;
         this.animDuration = 0;
         this.state = Animation.State.STOP;
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SChangeAnimationStatePacket(entity, this.getAbility(), this.animationId, this.state, 0), entity);
         }

      }
   }

   public void pause(LivingEntity entity) {
      if (this.isPlaying()) {
         this.state = Animation.State.PAUSE;
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SChangeAnimationStatePacket(entity, this.getAbility(), this.animationId, this.state, this.animDuration), entity);
         }

      }
   }

   public void resume(LivingEntity entity) {
      if (this.isPaused()) {
         this.state = Animation.State.PLAY;
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SChangeAnimationStatePacket(entity, this.getAbility(), this.animationId, this.state, this.animDuration), entity);
         }

      }
   }

   public void doTick(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         if (this.isPlaying()) {
            if (this.pauseCondition != null && (Boolean)this.pauseCondition.apply(entity)) {
               this.pause(entity);
               return;
            }

            if (this.stopCondition != null && (Boolean)this.stopCondition.apply(entity)) {
               this.stop(entity);
               return;
            }

            if (!this.isInfinite && this.animDuration <= 0) {
               this.stop(entity);
               return;
            }
         } else if (this.isPaused() && this.pauseCondition != null && !(Boolean)this.pauseCondition.apply(entity)) {
            this.resume(entity);
            return;
         }
      }

      if (this.isPlaying()) {
         ++this.animTick;
         --this.animDuration;
      }

   }

   public long getAnimationTick() {
      return this.animTick;
   }

   public boolean isPlaying() {
      return this.state == Animation.State.PLAY;
   }

   public boolean isStopped() {
      return this.state == Animation.State.STOP;
   }

   public boolean isPaused() {
      return this.state == Animation.State.PAUSE;
   }
}
