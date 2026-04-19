package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.BananawaniModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.BananawaniSaddleLayer;

public class BananawaniRenderer<T extends BananawaniEntity, M extends BananawaniModel<T>> extends MobRenderer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/bananawani.png");

   public BananawaniRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new BananawaniModel(ctx.m_174023_(BananawaniModel.LAYER_LOCATION)), 1.5F);
      this.m_115326_(new BananawaniSaddleLayer(ctx, this));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.0F, 0.1, (double)0.0F);
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
      matrixStack.m_85849_();
   }

   public void scale(T entity, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(1.5F, 1.5F, 1.5F);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<BananawaniEntity> {
      public EntityRenderer<BananawaniEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new BananawaniRenderer(ctx);
      }
   }
}
