package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.SpikeEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.CubeModel;

public class SpikeRenderer<T extends SpikeEntity> extends EntityRenderer<T> {
   private CubeModel<SpikeEntity> model;

   protected SpikeRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.05F;
      this.model = new CubeModel<SpikeEntity>(ctx.m_174023_(CubeModel.LAYER_LOCATION));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.0F, 0.02, (double)0.0F);
      float scale = 0.2F;
      matrixStack.m_85841_(scale, scale, scale);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(45.0F));
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(45.0F));
      VertexConsumer ivertexbuilder = buffer.m_6299_(ModRenderTypes.TRANSPARENT_COLOR);
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 0.2F, 0.2F, 0.2F, 1.0F);
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(T entity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<SpikeEntity> {
      public EntityRenderer<SpikeEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new SpikeRenderer<SpikeEntity>(ctx);
      }
   }
}
