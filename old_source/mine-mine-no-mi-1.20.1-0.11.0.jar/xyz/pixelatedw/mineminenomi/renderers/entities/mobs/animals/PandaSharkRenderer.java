package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.PandaSharkEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.PandaSharkModel;

public class PandaSharkRenderer<T extends PandaSharkEntity, M extends PandaSharkModel<T>> extends MobRenderer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/panda_shark.png");

   public PandaSharkRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new PandaSharkModel(ctx.m_174023_(PandaSharkModel.LAYER_LOCATION)), 1.5F);
   }

   public void scale(T entity, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(entity.getSize(), entity.getSize(), entity.getSize());
      this.f_114477_ = entity.getSize() * 0.85F;
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<PandaSharkEntity> {
      public EntityRenderer<PandaSharkEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new PandaSharkRenderer(ctx);
      }
   }
}
