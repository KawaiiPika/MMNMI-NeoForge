package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.entities.VivreCardEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.CubeModel;

@OnlyIn(Dist.CLIENT)
public class VivreCardRenderer extends EntityRenderer<VivreCardEntity> {
   private final CubeModel<VivreCardEntity> model;

   protected VivreCardRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.model = new CubeModel<VivreCardEntity>(ctx.m_174023_(CubeModel.LAYER_LOCATION));
   }

   public ResourceLocation getTextureLocation(VivreCardEntity entity) {
      return null;
   }

   public void render(VivreCardEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.0F, 0.05, (double)0.0F);
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(-entity.m_146908_()));
      matrixStack.m_85841_(0.4F, 0.1F, 0.5F);
      VertexConsumer vertex = buffer.m_6299_(ModRenderTypes.TRANSPARENT_COLOR);
      this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85849_();
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public static class Factory implements EntityRendererProvider<VivreCardEntity> {
      public EntityRenderer<VivreCardEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new VivreCardRenderer(ctx);
      }
   }
}
