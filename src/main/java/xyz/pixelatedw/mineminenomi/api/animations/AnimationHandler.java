package xyz.pixelatedw.mineminenomi.api.animations;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationStateData;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import java.util.Optional;

public class AnimationHandler {

    public static void playAnimation(LivingEntity entity, ResourceLocation animationId) {
        if (!entity.level().isClientSide) {
            entity.setData(ModDataAttachments.ANIMATION_STATE, new AnimationStateData(Optional.of(animationId), entity.level().getGameTime()));
        }
    }

    public static void stopAnimation(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.setData(ModDataAttachments.ANIMATION_STATE, new AnimationStateData());
        }
    }

    public static Optional<ResourceLocation> getActiveAnimation(LivingEntity entity) {
        if (entity.hasData(ModDataAttachments.ANIMATION_STATE)) {
            return entity.getData(ModDataAttachments.ANIMATION_STATE).activeAnimation();
        }
        return Optional.empty();
    }
}
