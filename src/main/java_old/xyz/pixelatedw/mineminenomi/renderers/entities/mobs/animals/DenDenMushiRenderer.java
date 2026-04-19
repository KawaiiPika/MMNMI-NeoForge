package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.DenDenMushiEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DenDenMushiModel;

public class DenDenMushiRenderer<T extends DenDenMushiEntity, M extends DenDenMushiModel<T>> extends MobRenderer<T, M> {
   public DenDenMushiRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new DenDenMushiModel(ctx.m_174023_(DenDenMushiModel.LAYER_LOCATION)), 0.3F);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return entity.getCurrentTexture();
   }

   public static class Factory implements EntityRendererProvider<DenDenMushiEntity> {
      public EntityRenderer<DenDenMushiEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new DenDenMushiRenderer(ctx);
      }
   }
}
