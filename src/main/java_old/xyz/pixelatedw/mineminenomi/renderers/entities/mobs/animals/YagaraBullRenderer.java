package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.YagaraBullEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.YagaraBullModel;

public class YagaraBullRenderer<T extends YagaraBullEntity, M extends YagaraBullModel<T>> extends MobRenderer<T, M> {
   private static final float SCALE = 1.5F;

   public YagaraBullRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new YagaraBullModel(ctx.m_174023_(YagaraBullModel.LAYER_LOCATION)), 1.3F);
   }

   public void scale(T entity, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(1.5F, 1.5F, 1.5F);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.isSaddled()) {
         ((YagaraBullModel)this.f_115290_).renderSaddle();
      }

      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return MobsHelper.YAGARA_BULL_TEXTURES[entity.getTextureId()];
   }

   public static class Factory implements EntityRendererProvider<YagaraBullEntity> {
      public EntityRenderer<YagaraBullEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new YagaraBullRenderer(ctx);
      }
   }
}
