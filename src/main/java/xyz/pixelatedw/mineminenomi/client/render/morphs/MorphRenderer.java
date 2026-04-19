package xyz.pixelatedw.mineminenomi.client.render.morphs;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class MorphRenderer<T extends LivingEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {
    
    private final ResourceLocation texture;

    public MorphRenderer(EntityRendererProvider.Context ctx, M model, float shadowRadius, ResourceLocation texture) {
        super(ctx, model, shadowRadius);
        this.texture = texture;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return this.texture;
    }
}
