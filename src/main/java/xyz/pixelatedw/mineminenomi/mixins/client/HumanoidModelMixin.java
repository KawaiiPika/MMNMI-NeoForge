package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.client.render.animations.ModAnimations;
import xyz.pixelatedw.mineminenomi.client.render.animations.PlayerAnimationWrapper;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationStateData;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import java.util.Optional;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {

    @Unique
    private PlayerAnimationWrapper mmnm$wrapper;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("RETURN"))
    public void onSetupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (!entity.hasData(ModDataAttachments.ANIMATION_STATE)) return;

        AnimationStateData animData = entity.getData(ModDataAttachments.ANIMATION_STATE);
        Optional<ResourceLocation> animIdOpt = animData.activeAnimation();

        if (animIdOpt.isPresent()) {
            String animName = animIdOpt.get().getPath();
            AnimationDefinition definition = ModAnimations.REGISTRY.get(animName);

            if (definition != null) {
                HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;

                if (this.mmnm$wrapper == null) {
                    this.mmnm$wrapper = new PlayerAnimationWrapper(model);
                }

                // Calculate smooth animation time based on the Data Attachment states
                long timePassedTicks = entity.level().getGameTime() - animData.startTimeTicks();
                // extract partial ticks from ageInTicks which is (tickCount + partialTicks)
                float partialTicks = ageInTicks - entity.tickCount;
                float animTime = timePassedTicks + partialTicks;

                KeyframeAnimations.animate(this.mmnm$wrapper, definition, (long)(animTime * 50f), 1.0f, new org.joml.Vector3f());
            }
        }
    }
}
