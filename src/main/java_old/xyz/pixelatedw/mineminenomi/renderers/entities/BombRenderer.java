package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.SphereModel;

public class BombRenderer extends EntityRenderer<BombEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/bomb.png");
   private SphereModel<BombEntity> model;

   protected BombRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.6F;
      this.model = new SphereModel<BombEntity>(ctx.m_174023_(SphereModel.LAYER_LOCATION));
   }

   public void render(BombEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.0F, 0.8, (double)0.0F);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(180.0F));
      if (entity.f_19797_ < 4) {
         matrixStack.m_85837_((double)0.0F, (double)1.5F - (double)((float)entity.f_19797_ / 3.0F), (double)0.0F);
      } else {
         matrixStack.m_85837_((double)0.0F, 0.4, (double)0.0F);
      }

      float scale = 3.0F;
      matrixStack.m_85841_(scale, scale, scale);
      int t1 = 1;
      matrixStack.m_252781_(Axis.f_252392_.m_252977_(Mth.m_14179_(partialTicks, entity.m_146908_(), entity.f_19859_)));
      int t3 = Math.min(t1, 6) - 1;
      RenderType type = RenderType.m_110473_(this.getTextureLocation(entity));
      if (t1 > 0) {
         t1 = Math.max(3, t1);
         int t2 = Math.max(40, t1 * 10) + entity.hashCode() % 10;
         if (t2 > 0 && entity.f_19797_ % t2 > 0 && entity.f_19797_ % t2 < t1) {
            type = ModRenderTypes.TRANSPARENT_COLOR;
         }
      }

      VertexConsumer vertex = buffer.m_6299_(type);
      this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      if (entity.f_19797_ % 15 == 0) {
         for(int i = 0; i < t3; ++i) {
            entity.m_9236_().m_7106_(ParticleTypes.f_123744_, entity.m_20185_(), entity.m_20186_() + (double)3.7F + (double)((float)i / 8.0F), entity.m_20189_(), (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(BombEntity entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<BombEntity> {
      public EntityRenderer<BombEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new BombRenderer(ctx);
      }
   }
}
