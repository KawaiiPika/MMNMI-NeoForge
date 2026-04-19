package xyz.pixelatedw.mineminenomi.api.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.animation.AnimationCapability;

public abstract class Animation<E extends LivingEntity, M extends EntityModel<E>> {
   private long startTime;
   private long runTime;
   private int initialDuration;
   private int animDuration;
   private AnimationId<? extends Animation<E, M>> animId;
   private State state;
   private IAnimationPostAngles<E, M> animationPostAngles;
   private IAnimationAngles<E, M> animationAngles;
   private IAnimationHeldItem<E> animationHeldItem;
   private @Nullable M model;

   public <A extends Animation<E, M>> Animation(AnimationId<A> animId) {
      this.state = Animation.State.STOP;
      this.animId = animId;
   }

   public void setAnimationPostAngles(IAnimationPostAngles<E, M> func) {
      this.animationPostAngles = func;
   }

   public void setAnimationAngles(IAnimationAngles<E, M> func) {
      this.animationAngles = func;
   }

   public void setAnimationHeldItem(IAnimationHeldItem<E> func) {
      this.animationHeldItem = func;
   }

   public static <E extends LivingEntity, M extends EntityModel<E>> void animationPostAngles(E entity, M model, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      Animation<LivingEntity, EntityModel<LivingEntity>> animation = AnimationCapability.getAnimation(entity);
      if (animation != null && animation.isPlaying()) {
         animation.model = model;
         if (animation.animationPostAngles != null) {
            try {
               animation.animationPostAngles.setAnimationPostAngles(entity, model, matrixStack, buffer, packedLight, partialTicks, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            } catch (Exception var13) {
            }
         }
      }

   }

   public static <E extends LivingEntity, M extends EntityModel<E>> void animationAngles(E entity, M model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      Animation<LivingEntity, EntityModel<LivingEntity>> animation = AnimationCapability.getAnimation(entity);
      if (animation != null && animation.isPlaying()) {
         animation.model = model;
         if (animation.animationAngles != null) {
            try {
               animation.animationAngles.setAnimationAngles(entity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            } catch (Exception var9) {
            }
         }
      }

      if (model instanceof HumanoidModel bipedModel) {
         bipedModel.f_102809_.m_104315_(bipedModel.f_102808_);
      }

   }

   public static <E extends LivingEntity> void animationHeldItem(E entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      Animation<LivingEntity, EntityModel<LivingEntity>> animation = AnimationCapability.getAnimation(entity);
      if (animation != null && animation.isPlaying() && animation.animationHeldItem != null) {
         try {
            animation.animationHeldItem.setupHeldItem(entity, stack, transformType, handSide, matrixStack, renderBuffer, packedLight);
         } catch (Exception var9) {
         }
      }

   }

   public void start(LivingEntity entity, int animDuration) {
      if (this.model != null) {
         RendererHelper.resetModelToDefaultPivots(this.model);
      }

      this.startTime = (long)entity.f_19797_;
      this.runTime = 0L;
      this.initialDuration = animDuration;
      this.animDuration = animDuration;
      this.state = Animation.State.PLAY;
   }

   public void stop(LivingEntity entity) {
      this.runTime = 0L;
      this.state = Animation.State.STOP;
      if (this.model != null) {
         RendererHelper.resetModelToDefaultPivots(this.model);
      }

   }

   public void tick(LivingEntity entity) {
      if (!Minecraft.m_91087_().m_91104_()) {
         if (this.isPlaying()) {
            this.runTime = (long)entity.f_19797_ - this.startTime;
            if (this.animDuration != -1 && this.animDuration <= 0) {
               if (!entity.m_9236_().f_46443_) {
                  this.stop(entity);
               }
            } else if (this.animDuration != -1) {
               --this.animDuration;
            }
         }

      }
   }

   public M getModel() {
      return this.model;
   }

   public long getStartTime() {
      return this.startTime;
   }

   public long getTime() {
      return this.runTime;
   }

   public int getAnimationInitialDuration() {
      return this.initialDuration;
   }

   public int getAnimationDuration() {
      return this.animDuration;
   }

   public float getAnimationCompletion() {
      return Math.min((float)this.runTime / (float)this.initialDuration, 1.0F);
   }

   public State getState() {
      return this.state;
   }

   public boolean isPlaying() {
      return this.state == Animation.State.PLAY;
   }

   public boolean isStopped() {
      return this.state == Animation.State.STOP;
   }

   public AnimationId<? extends Animation<E, M>> getId() {
      return this.animId;
   }

   public static enum State {
      PLAY,
      STOP,
      PAUSE;

      // $FF: synthetic method
      private static State[] $values() {
         return new State[]{PLAY, STOP, PAUSE};
      }
   }

   @FunctionalInterface
   public interface IAnimationAngles<E extends LivingEntity, M extends EntityModel<E>> {
      void setAnimationAngles(E var1, M var2, float var3, float var4, float var5, float var6, float var7);
   }

   @FunctionalInterface
   public interface IAnimationHeldItem<E extends LivingEntity> {
      void setupHeldItem(E var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7);
   }

   @FunctionalInterface
   public interface IAnimationPostAngles<E extends LivingEntity, M extends EntityModel<E>> {
      void setAnimationPostAngles(E var1, M var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11);
   }
}
