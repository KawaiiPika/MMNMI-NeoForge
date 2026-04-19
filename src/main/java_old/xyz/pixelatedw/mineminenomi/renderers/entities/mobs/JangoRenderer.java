package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.JangoEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.armors.JangoGlassesLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.armors.JangoMushroomLayer;

public class JangoRenderer extends HumanoidMobRenderer<JangoEntity, HumanoidModel<JangoEntity>> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/jango.png");

   public JangoRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_)), 1.0F);
      this.m_115326_(new HumanoidArmorLayer(this, new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_)), new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_)), ctx.m_266367_()));
      this.m_115326_(new JangoMushroomLayer(ctx, this));
      this.m_115326_(new JangoGlassesLayer(ctx, this));
   }

   public ResourceLocation getTextureLocation(JangoEntity p_114482_) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<JangoEntity> {
      public EntityRenderer<JangoEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new JangoRenderer(ctx);
      }
   }
}
