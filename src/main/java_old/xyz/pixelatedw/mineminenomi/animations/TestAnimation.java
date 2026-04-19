package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class TestAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private FrameManger manager;

   public TestAnimation(AnimationId<TestAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void start(LivingEntity entity, int duration) {
      super.start(entity, duration);
      this.manager = new FrameManger();
      this.manager.createFrame("rightArm", 20).setRotation(0.0F, 0.0F, 90.0F);
      this.manager.createFrame("rightArm", 20).setRotation(0.0F, 0.0F, 180.0F);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.manager.apply("rightArm", model.f_102811_);
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
   }

   class FrameManger {
      private Map<String, List<AnimationFrame>> frames = new HashMap();
      private int currentIndex;
      private int time;
      private int maxDuration;

      public AnimationFrame createFrame(String name, int duration) {
         AnimationFrame frame = TestAnimation.this.new AnimationFrame(name, duration);
         if (this.frames.containsKey(name)) {
            ((List)this.frames.get(name)).add(frame);
         } else {
            List<AnimationFrame> list = new ArrayList();
            list.add(frame);
            this.frames.putIfAbsent(name, list);
         }

         this.maxDuration += frame.duration;
         return frame;
      }

      public void apply(String name, ModelPart part) {
         List<AnimationFrame> frames = (List)this.frames.get(name);
         AnimationFrame previousFrame = null;
         if (this.currentIndex > 0) {
            previousFrame = (AnimationFrame)frames.get(this.currentIndex - 1);
         }

         AnimationFrame currentFrame = (AnimationFrame)frames.get(this.currentIndex);
         if (currentFrame != null) {
            currentFrame.setDefaults(part, previousFrame);
            float frameCompletion = (float)(this.currentIndex + 1) / (float)frames.size();
            float completion = Math.min((float)this.time / (float)this.maxDuration, 1.0F) * frameCompletion;
            ++this.time;
            boolean hasFinished = currentFrame.play(part, completion, previousFrame);
            boolean hasNext = this.currentIndex < frames.size();
            if (hasFinished && hasNext) {
               ++this.currentIndex;
            }
         }

      }
   }

   class AnimationFrame {
      private String name;
      private int duration;
      private final int maxDuration;
      private boolean markedDefaults = false;
      private float defaultX;
      private float defaultY;
      private float defaultZ;
      private float x;
      private float y;
      private float z;
      private float defaultXRot;
      private float defaultYRot;
      private float defaultZRot;
      private float xRot;
      private float yRot;
      private float zRot;

      public AnimationFrame(String name, int duration) {
         this.name = name;
         this.duration = duration;
         this.maxDuration = duration;
      }

      public AnimationFrame setPosition(float x, float y, float z) {
         this.x = x;
         this.y = y;
         this.z = z;
         return this;
      }

      public AnimationFrame setRotation(float x, float y, float z) {
         this.xRot = (float)Math.toRadians((double)x);
         this.yRot = (float)Math.toRadians((double)y);
         this.zRot = (float)Math.toRadians((double)z);
         return this;
      }

      private void setDefaults(ModelPart part, @Nullable AnimationFrame previousFrame) {
         if (!this.markedDefaults) {
            float prevXRot = previousFrame != null ? previousFrame.defaultXRot : 0.0F;
            float prevYRot = previousFrame != null ? previousFrame.defaultYRot : 0.0F;
            if (previousFrame != null) {
               float var10000 = previousFrame.defaultZRot;
            } else {
               float var6 = 0.0F;
            }

            this.defaultX = part.f_104200_;
            this.defaultY = part.f_104201_;
            this.defaultZ = part.f_104202_;
            this.defaultXRot = part.f_104203_ - prevXRot;
            this.defaultYRot = part.f_104204_ - prevYRot;
            this.defaultZRot = part.f_104205_;
            this.markedDefaults = true;
         }
      }

      public boolean play(ModelPart part, float completion, @Nullable AnimationFrame previousFrame) {
         float ease = EasingFunctionHelper.easeOutCubic(completion);
         part.f_104200_ = this.defaultX + this.x * ease;
         part.f_104201_ = this.defaultY + this.y * ease;
         part.f_104202_ = this.defaultZ + this.z * ease;
         if (previousFrame != null) {
            float var10000 = previousFrame.defaultXRot;
         } else {
            float var8 = 0.0F;
         }

         if (previousFrame != null) {
            float var9 = previousFrame.defaultYRot;
         } else {
            float var10 = 0.0F;
         }

         float prevZRot = previousFrame != null ? previousFrame.defaultZRot : 0.0F;
         part.f_104203_ = (this.defaultXRot + this.xRot) * ease;
         part.f_104204_ = (this.defaultYRot + this.yRot) * ease;
         part.f_104205_ = (this.defaultZRot + (this.zRot - prevZRot)) * ease;
         return --this.duration <= 0;
      }
   }
}
