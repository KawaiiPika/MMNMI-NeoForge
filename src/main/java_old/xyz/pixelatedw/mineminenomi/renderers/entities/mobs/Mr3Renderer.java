package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;
import xyz.pixelatedw.mineminenomi.renderers.layers.Mr3HairLayer;

public class Mr3Renderer extends OPHumanoidRenderer<Mr3Entity, HumanoidModel<Mr3Entity>> {
   public Mr3Renderer(EntityRendererProvider.Context ctx) {
      super(ctx, () -> new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_)), new float[]{1.0F, 1.0F, 1.0F});
      this.m_115326_(new Mr3HairLayer(ctx, this));
   }

   public ResourceLocation getTextureLocation(Mr3Entity entity) {
      return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/mr3.png");
   }

   public static class Factory implements EntityRendererProvider<Mr3Entity> {
      public EntityRenderer<Mr3Entity> m_174009_(EntityRendererProvider.Context ctx) {
         return new Mr3Renderer(ctx);
      }
   }
}
