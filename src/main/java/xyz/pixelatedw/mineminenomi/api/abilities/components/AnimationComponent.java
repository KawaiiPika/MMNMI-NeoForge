package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationHandler;

public class AnimationComponent {

    private ResourceLocation animationId;

    public AnimationComponent() {}

    public void start(LivingEntity entity, ResourceLocation animationId) {
        this.animationId = animationId;
        AnimationHandler.playAnimation(entity, animationId);
    }

    public void start(LivingEntity entity, String animationPath) {
        start(entity, ResourceLocation.fromNamespaceAndPath("mineminenomi", animationPath));
    }

    public void stop(LivingEntity entity) {
        if (this.animationId != null) {
            AnimationHandler.stopAnimation(entity);
            this.animationId = null;
        }
    }
}
