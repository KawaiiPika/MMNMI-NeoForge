package xyz.pixelatedw.mineminenomi.mixins.client;

import java.util.Optional;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@Mixin({HumanoidModel.class})
public abstract class HumanoidModelMixin<T extends LivingEntity> extends AgeableListModel<T> {
   @Shadow
   public ModelPart f_102808_;
   @Shadow
   public ModelPart f_102810_;
   @Shadow
   public ModelPart f_102811_;
   @Shadow
   public ModelPart f_102812_;
   @Shadow
   public ModelPart f_102813_;
   @Shadow
   public ModelPart f_102814_;
   @Shadow
   public ModelPart f_102809_;

   @Inject(
      method = {"setupAnim"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$preSetupAnimation(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callback) {
      Optional<IEntityStats> props = EntityStatsCapability.get(entity);
      boolean isBlackLeg = (Boolean)props.map((data) -> data.isBlackLeg()).orElse(false);
      HumanoidModel<T> model = (HumanoidModel)this;
      boolean isRotationBlocked = entity.m_21220_().stream().anyMatch((instance) -> {
         MobEffect patt1925$temp = instance.m_19544_();
         boolean var10000;
         if (patt1925$temp instanceof IBindBodyEffect bodyBindingEffect) {
            if (bodyBindingEffect.isBlockingRotation()) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      });
      if (isRotationBlocked) {
         Animation.animationAngles(entity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         callback.cancel();
      }

      if (isBlackLeg && entity.m_21205_().m_41619_()) {
         this.f_102608_ = 0.0F;
      }

   }

   @Inject(
      method = {"setupAnim"},
      at = {@At("TAIL")}
   )
   public void mineminenomi$setupAnimation(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callback) {
      Optional<IEntityStats> props = EntityStatsCapability.get(entity);
      boolean isBlackLeg = (Boolean)props.map((data) -> data.isBlackLeg()).orElse(false);
      boolean isInCombatMode = (Boolean)props.map((data) -> data.isInCombatMode()).orElse(false);
      if (isBlackLeg && (isInCombatMode || ClientConfig.isBlackLegAlwaysUp())) {
         if (entity.f_19854_ - entity.m_20185_() == (double)0.0F && entity.f_19856_ - entity.m_20189_() == (double)0.0F && !entity.m_21023_((MobEffect)ModEffects.MOVEMENT_BLOCKED.get())) {
            ModelPart var10000 = this.f_102813_;
            var10000.f_104201_ -= 4.0F;
            var10000 = this.f_102813_;
            var10000.f_104202_ -= 3.0F;
         }

         if (entity.f_20921_ > 0.0F && entity.m_21205_().m_41619_()) {
            float swingProgress = entity.f_20921_;
            ModelPart var14 = this.f_102813_;
            var14.f_104203_ -= entity.f_20921_ * 2.0F;
            var14 = this.f_102813_;
            var14.f_104205_ += entity.f_20921_ * 2.0F;
            this.f_102810_.f_104204_ = Mth.m_14031_(Mth.m_14116_(swingProgress) * ((float)Math.PI * 2F)) * 0.2F;
            this.f_102811_.f_104202_ = Mth.m_14031_(this.f_102810_.f_104204_) * 5.0F;
            this.f_102811_.f_104200_ = -Mth.m_14089_(this.f_102810_.f_104204_) * 5.0F;
            this.f_102812_.f_104202_ = -Mth.m_14031_(this.f_102810_.f_104204_) * 5.0F;
            this.f_102812_.f_104200_ = Mth.m_14089_(this.f_102810_.f_104204_) * 5.0F;
            var14 = this.f_102811_;
            var14.f_104204_ += this.f_102810_.f_104204_;
            var14 = this.f_102812_;
            var14.f_104204_ += this.f_102810_.f_104204_;
            var14 = this.f_102812_;
            var14.f_104203_ += this.f_102810_.f_104204_;
            this.f_102808_.m_104315_(this.f_102809_);
         }
      }

      HumanoidModel<T> model = (HumanoidModel)this;
      Animation.animationAngles(entity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }
}
