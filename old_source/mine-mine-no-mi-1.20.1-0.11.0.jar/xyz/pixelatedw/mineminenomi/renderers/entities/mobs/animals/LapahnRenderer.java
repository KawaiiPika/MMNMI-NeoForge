package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.LapahnModel;

public class LapahnRenderer<T extends LapahnEntity, M extends LapahnModel<T>> extends MobRenderer<T, M> {
   private static final ResourceLocation NORMAL_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/lapahn.png");
   private static final ResourceLocation ENRAGED_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/lapahn_angry.png");

   public LapahnRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new LapahnModel(ctx.m_174023_(LapahnModel.LAYER_LOCATION)), 0.8F);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   protected void setupRotations(T entity, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
      super.m_7523_(entity, matrixStack, ageInTicks, rotationYaw, partialTicks);
      if (entity.isResting()) {
         matrixStack.m_85837_((double)0.0F, -0.45, (double)0.0F);
      }

   }

   public ResourceLocation getTextureLocation(T entity) {
      return entity.isEnraged() ? ENRAGED_TEXTURE : NORMAL_TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<LapahnEntity> {
      public EntityRenderer<LapahnEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new LapahnRenderer(ctx);
      }
   }
}
