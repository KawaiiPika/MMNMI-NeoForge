package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BigDuckEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.BigDuckModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.BigDuckSaddleLayer;

public class BigDuckRenderer<T extends BigDuckEntity, M extends BigDuckModel<T>> extends MobRenderer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/big_duck.png");

   public BigDuckRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new BigDuckModel(ctx.m_174023_(BigDuckModel.LAYER_LOCATION)), 0.7F);
      this.m_115326_(new BigDuckSaddleLayer(this));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<BigDuckEntity> {
      public EntityRenderer<BigDuckEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new BigDuckRenderer(ctx);
      }
   }
}
