package xyz.pixelatedw.mineminenomi.renderers.entities.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.CannonModel;

public class CannonRenderer extends EntityRenderer<CannonEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/cannon.png");
   private final CannonModel model;

   protected CannonRenderer(EntityRendererProvider.Context ctx, CannonModel model) {
      super(ctx);
      this.model = model;
   }

   public void render(CannonEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.0F, (double)1.5F, (double)0.0F);
      matrixStack.m_85841_(1.0F, 1.0F, 1.0F);
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0F - entityYaw));
      float hurtTime = (float)entity.getHurtTime() - partialTicks;
      if (hurtTime > 0.0F) {
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14031_(hurtTime) * hurtTime));
      }

      matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
      this.model.setupAnim(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer ivertexbuilder = buffer.m_6299_(this.model.m_103119_(this.getTextureLocation(entity)));
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85849_();
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(CannonEntity entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<CannonEntity> {
      public EntityRenderer<CannonEntity> m_174009_(EntityRendererProvider.Context ctx) {
         CannonModel model = new CannonModel(ctx);
         return new CannonRenderer(ctx, model);
      }
   }
}
