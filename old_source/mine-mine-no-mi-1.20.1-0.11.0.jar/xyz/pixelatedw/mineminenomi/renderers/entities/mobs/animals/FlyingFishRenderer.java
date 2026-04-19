package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FlyingFishEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.FlyingFishModel;

public class FlyingFishRenderer<T extends FlyingFishEntity, M extends FlyingFishModel<T>> extends MobRenderer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/flying_fish.png");

   public FlyingFishRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new FlyingFishModel(ctx.m_174023_(FlyingFishModel.LAYER_LOCATION)), 1.5F);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.isSaddled()) {
         ((FlyingFishModel)this.f_115290_).renderSaddle();
      }

      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<FlyingFishEntity> {
      public EntityRenderer<FlyingFishEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new FlyingFishRenderer(ctx);
      }
   }
}
