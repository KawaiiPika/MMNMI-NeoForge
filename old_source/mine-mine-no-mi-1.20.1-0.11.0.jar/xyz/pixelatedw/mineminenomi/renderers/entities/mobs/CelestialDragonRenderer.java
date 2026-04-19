package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.CelestialDragonSlaveRideLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.armors.CelestialDragonWigLayer;

public class CelestialDragonRenderer extends OPHumanoidRenderer<CelestialDragonEntity, HumanoidModel<CelestialDragonEntity>> {
   public CelestialDragonRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, () -> new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_)), new float[]{1.0F, 1.0F, 1.0F});
      this.m_115326_(new CelestialDragonWigLayer(ctx, this));
      this.m_115326_(new CelestialDragonSlaveRideLayer(ctx, this));
   }

   public void render(CelestialDragonEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   protected void setupRotations(CelestialDragonEntity entity, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
      ((HumanoidModel)this.f_115290_).f_102609_ = entity.isRidingSlave();
      super.m_7523_(entity, matrixStack, ageInTicks, rotationYaw, partialTicks);
   }

   public static class Factory extends OPHumanoidRenderer.Factory<CelestialDragonEntity> {
      public Factory(EntityRendererProvider.Context ctx) {
         super(ctx);
      }

      public EntityRenderer<CelestialDragonEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new CelestialDragonRenderer(ctx);
      }
   }
}
