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
import xyz.pixelatedw.mineminenomi.entities.SniperTargetEntity;
import xyz.pixelatedw.mineminenomi.models.entities.SniperTargetModel;

public class SniperTargetRenderer<T extends SniperTargetEntity> extends EntityRenderer<T> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/sniper_target.png");
   private final SniperTargetModel<T> model;

   protected SniperTargetRenderer(EntityRendererProvider.Context ctx, SniperTargetModel<T> model) {
      super(ctx);
      this.model = model;
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_252880_(0.0F, 1.5F, 0.0F);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(180.0F));
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(entity.f_19860_ + (entity.m_146909_() - entity.f_19860_) * partialTicks - 180.0F));
      float ageInTicks = (float)entity.f_19797_ + partialTicks;
      this.model.m_6973_(entity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
      RenderType type = RenderType.m_110458_(this.getTextureLocation(entity));
      VertexConsumer ivertexbuilder = buffer.m_6299_(type);
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(SniperTargetEntity entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<SniperTargetEntity> {
      public EntityRenderer<SniperTargetEntity> m_174009_(EntityRendererProvider.Context ctx) {
         SniperTargetModel<SniperTargetEntity> model = new SniperTargetModel<SniperTargetEntity>(ctx);
         return new SniperTargetRenderer<SniperTargetEntity>(ctx, model);
      }
   }
}
