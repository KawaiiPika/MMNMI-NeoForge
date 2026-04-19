package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.ArtOfWeatherTrainerEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.WizardBeardLayer;

public class ArtOfWeatherTrainerRenderer extends OPHumanoidRenderer<ArtOfWeatherTrainerEntity, HumanoidModel<ArtOfWeatherTrainerEntity>> {
   public ArtOfWeatherTrainerRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, () -> new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_)), new float[]{1.0F, 1.0F, 1.0F});
      this.m_115326_(new WizardBeardLayer(ctx, this));
   }

   public static class Factory implements EntityRendererProvider<ArtOfWeatherTrainerEntity> {
      public EntityRenderer<ArtOfWeatherTrainerEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new ArtOfWeatherTrainerRenderer(ctx);
      }
   }
}
