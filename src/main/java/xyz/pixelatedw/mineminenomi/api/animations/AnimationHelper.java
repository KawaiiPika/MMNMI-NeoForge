package xyz.pixelatedw.mineminenomi.api.animations;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationStateData;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import java.util.Optional;

public class AnimationHelper {

    public static void playAnimation(LivingEntity entity, ResourceLocation animationId) {
        AnimationStateData data = new AnimationStateData(Optional.of(animationId), entity.level().getGameTime());
        entity.setData(ModDataAttachments.ANIMATION_STATE, data);
        // Sync is handled automatically by the attachment registry using its STREAM_CODEC
    }

    public static void stopAnimation(LivingEntity entity) {
        AnimationStateData data = new AnimationStateData(Optional.empty(), 0L);
        entity.setData(ModDataAttachments.ANIMATION_STATE, data);
        // Sync is handled automatically by the attachment registry using its STREAM_CODEC
    }
}
