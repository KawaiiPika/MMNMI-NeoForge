package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.PacifistaModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.PacifistaChargeLaserLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.PacifistaNumberLayer;

public class PacifistaRenderer<T extends PacifistaEntity, M extends PacifistaModel<T>> extends HumanoidMobRenderer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/pacifista.png");
   private static final float SCALE = 1.75F;

   public PacifistaRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new PacifistaModel(ctx.m_174023_(PacifistaModel.LAYER_LOCATION)), 0.8F);
      this.m_115326_(new PacifistaNumberLayer(ctx, this));
      this.m_115326_(new PacifistaChargeLaserLayer(this));
   }

   public void scale(T entity, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(1.75F, 1.75F, 1.75F);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<PacifistaEntity> {
      public EntityRenderer<PacifistaEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new PacifistaRenderer(ctx);
      }
   }
}
