package xyz.pixelatedw.mineminenomi.client.renderers.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class OPHumanoidRenderer<T extends Mob, M extends HumanoidModel<T>> extends HumanoidMobRenderer<T, M> {

    public OPHumanoidRenderer(EntityRendererProvider.Context ctx, M model, float shadowSize) {
        super(ctx, model, shadowSize);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (entity instanceof OPEntity opEntity) {
            return opEntity.getCurrentTexture();
        }
        return ModResources.NULL_ENTITY_TEXTURE;
    }
}
