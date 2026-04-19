package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.effects.CaughtInNetEffect;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.PlaneModel;

public class NetRenderer<T extends NetEntity> extends EntityRenderer<T> {
   protected PlaneModel<NetEntity> model;

   public NetRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.05F;
      this.model = new PlaneModel<NetEntity>(ctx.m_174023_(PlaneModel.LAYER_LOCATION));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      entity.m_146926_(Mth.m_14036_(entity.m_146909_(), -30.0F, 50.0F));
      entity.f_19860_ = Mth.m_14036_(entity.f_19860_, -30.0F, 50.0F);
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14179_(partialTicks, entity.f_19859_, entity.m_146908_()) + 0.0F));
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_()) + -10.0F));
      matrixStack.m_85841_(6.0F, 6.0F, 6.0F);
      matrixStack.m_85837_((double)0.0F, -1.45, (double)0.0F);
      VertexConsumer ivertexbuilder = buffer.m_6299_(RenderType.m_110470_(this.getTextureLocation(entity)));
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(T entity) {
      switch (entity.getNetType()) {
         case KAIROSEKI:
            return CaughtInNetEffect.KAIROSEKI_NET_TEXTURE;
         case NORMAL:
         default:
            return CaughtInNetEffect.NET_TEXTURE;
      }
   }

   public static class Factory implements EntityRendererProvider<NetEntity> {
      public EntityRenderer<NetEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new NetRenderer<NetEntity>(ctx);
      }
   }
}
