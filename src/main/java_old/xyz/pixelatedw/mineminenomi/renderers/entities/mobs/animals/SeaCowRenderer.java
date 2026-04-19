package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.SeaCowEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.SeaCowModel;

public class SeaCowRenderer<T extends SeaCowEntity, M extends SeaCowModel<T>> extends MobRenderer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/sea_cow.png");

   public SeaCowRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new SeaCowModel(ctx.m_174023_(SeaCowModel.LAYER_LOCATION)), 0.8F);
   }

   public void scale(T entity, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(entity.getSize(), entity.getSize(), entity.getSize());
      this.f_114477_ = entity.getSize();
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<SeaCowEntity> {
      public EntityRenderer<SeaCowEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new SeaCowRenderer(ctx);
      }
   }
}
