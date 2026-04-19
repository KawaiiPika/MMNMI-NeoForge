package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.CabajiUnicycleLayer;

public class CabajiRenderer extends HumanoidMobRenderer<CabajiEntity, HumanoidModel<CabajiEntity>> {
   public CabajiRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_)), 1.0F);
      this.m_115326_(new CabajiUnicycleLayer(ctx, this));
   }

   public void render(CabajiEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      if (entity.hasUnicycle()) {
         matrixStack.m_85837_((double)0.0F, 0.3, (double)0.0F);
      }

      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(CabajiEntity p_114482_) {
      return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/cabaji.png");
   }

   public static class Factory implements EntityRendererProvider<CabajiEntity> {
      public EntityRenderer<CabajiEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new CabajiRenderer(ctx);
      }
   }
}
