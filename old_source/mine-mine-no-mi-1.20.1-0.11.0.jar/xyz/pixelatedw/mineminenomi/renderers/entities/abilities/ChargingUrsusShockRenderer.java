package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.ChargingUrsusShockEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.PawModel;

public class ChargingUrsusShockRenderer<T extends ChargingUrsusShockEntity> extends EntityRenderer<T> {
   private PawModel<T> model;

   protected ChargingUrsusShockRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.model = new PawModel<T>(ctx.m_174023_(PawModel.LAYER_LOCATION));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.0F, (double)1.5F, (double)0.0F);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(180.0F));
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(entity.f_19859_ + (entity.m_146908_() - entity.f_19859_) * partialTicks - 180.0F));
      float size = 1.0F + entity.getCharge() / 2.0F;
      matrixStack.m_85841_(size, size, size);
      VertexConsumer ivertexbuilder = buffer.m_6299_(ModRenderTypes.TRANSPARENT_COLOR);
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.6F);
      matrixStack.m_85836_();
      double t = (double)(entity.f_19797_ * 3 % 100);
      double mirageSize = t >= (double)50.0F ? (double)2.0F - t / (double)100.0F : (double)1.0F + t / (double)100.0F;
      float scale = (float)mirageSize;
      matrixStack.m_85841_(scale, scale, scale);
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.2F);
      matrixStack.m_85849_();
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(ChargingUrsusShockEntity entity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<ChargingUrsusShockEntity> {
      public EntityRenderer<ChargingUrsusShockEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new ChargingUrsusShockRenderer<ChargingUrsusShockEntity>(ctx);
      }
   }
}
