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
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;
import xyz.pixelatedw.mineminenomi.models.entities.WantedPosterPackageModel;

public class WantedPosterPackageRenderer<T extends WantedPosterPackageEntity> extends EntityRenderer<T> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/wanted_posters_package.png");
   private final WantedPosterPackageModel model;

   protected WantedPosterPackageRenderer(EntityRendererProvider.Context ctx, WantedPosterPackageModel model) {
      super(ctx);
      this.model = model;
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_252880_(0.0F, 1.5F, 0.0F);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(180.0F));
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(entity.f_19860_ + (entity.m_146909_() - entity.f_19860_) * partialTicks - 180.0F));
      matrixStack.m_85841_(1.25F, 1.0F, 1.25F);
      float ageInTicks = (float)entity.f_19797_ + partialTicks;
      this.model.setupAnim(entity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
      RenderType type = RenderType.m_110458_(this.getTextureLocation(entity));
      VertexConsumer ivertexbuilder = buffer.m_6299_(type);
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(WantedPosterPackageEntity entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<WantedPosterPackageEntity> {
      public EntityRenderer<WantedPosterPackageEntity> m_174009_(EntityRendererProvider.Context ctx) {
         WantedPosterPackageModel model = new WantedPosterPackageModel(ctx);
         return new WantedPosterPackageRenderer<WantedPosterPackageEntity>(ctx, model);
      }
   }
}
