package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.projectiles.PopGreenProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.SphereModel;

public class PopGreenRenderer<T extends PopGreenProjectile> extends EntityRenderer<T> {
   private static final Color COLOR = new Color(6924891);
   private SphereModel<PopGreenProjectile> model;

   protected PopGreenRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.05F;
      this.model = new SphereModel<PopGreenProjectile>(ctx.m_174023_(SphereModel.LAYER_LOCATION));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.f_19797_ >= 2) {
         matrixStack.m_85836_();
         if (!entity.isOnGround()) {
            float rotation = (float)entity.f_19797_ * 20.0F % 360.0F;
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(rotation));
            matrixStack.m_252781_(Axis.f_252529_.m_252977_(-rotation));
         }

         matrixStack.m_85841_(0.5F, 0.5F, 0.5F);
         VertexConsumer vertex = buffer.m_6299_(ModRenderTypes.TRANSPARENT_COLOR);
         this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, (float)COLOR.getRed() / 255.0F, (float)COLOR.getGreen() / 255.0F, (float)COLOR.getBlue() / 255.0F, 1.0F);
         matrixStack.m_85849_();
      }
   }

   public ResourceLocation getTextureLocation(T entity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<PopGreenProjectile> {
      public EntityRenderer<PopGreenProjectile> m_174009_(EntityRendererProvider.Context ctx) {
         return new PopGreenRenderer<PopGreenProjectile>(ctx);
      }
   }
}
