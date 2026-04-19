package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.KuroobiEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.KuroobiHairLayer;

public class KuroobiRenderer extends OPHumanoidRenderer<KuroobiEntity, HumanoidModel<KuroobiEntity>> {
   public KuroobiRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, () -> new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_)), new float[]{1.28F, 1.28F, 1.28F});
      this.m_115326_(new KuroobiHairLayer(ctx, this));
   }

   public ResourceLocation getTextureLocation(KuroobiEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/kuroobi.png");
   }

   public static class Factory implements EntityRendererProvider<KuroobiEntity> {
      public EntityRenderer<KuroobiEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new KuroobiRenderer(ctx);
      }
   }
}
