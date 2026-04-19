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
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.UnicycleModel;

public class UnicycleRenderer extends EntityRenderer<UnicycleEntity> {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/unicycle.png");
   private final UnicycleModel model;

   protected UnicycleRenderer(EntityRendererProvider.Context ctx, UnicycleModel model) {
      super(ctx);
      this.model = model;
   }

   public void render(UnicycleEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
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

   public ResourceLocation getTextureLocation(UnicycleEntity entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<UnicycleEntity> {
      public EntityRenderer<UnicycleEntity> m_174009_(EntityRendererProvider.Context ctx) {
         UnicycleModel model = new UnicycleModel(ctx);
         return new UnicycleRenderer(ctx, model);
      }
   }
}
