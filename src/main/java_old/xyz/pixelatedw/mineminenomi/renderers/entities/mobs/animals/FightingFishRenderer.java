package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FightingFishEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.FightingFishModel;

public class FightingFishRenderer<T extends FightingFishEntity, M extends FightingFishModel<T>> extends MobRenderer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/fighting_fish.png");

   public FightingFishRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new FightingFishModel(ctx.m_174023_(FightingFishModel.LAYER_LOCATION)), 1.5F);
   }

   public void scale(T entity, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(entity.getSize(), entity.getSize(), entity.getSize());
      this.f_114477_ = entity.getSize() * 2.5F;
      matrixStack.m_252880_(0.0F, 1.0F, 0.0F);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<FightingFishEntity> {
      public EntityRenderer<FightingFishEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new FightingFishRenderer(ctx);
      }
   }
}
