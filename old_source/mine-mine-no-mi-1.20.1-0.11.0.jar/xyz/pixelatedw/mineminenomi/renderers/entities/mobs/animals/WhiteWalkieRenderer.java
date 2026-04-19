package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.WhiteWalkieModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.EyesLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.WhiteWalkieSaddleLayer;

public class WhiteWalkieRenderer<T extends WhiteWalkieEntity, M extends WhiteWalkieModel<T>> extends MobRenderer<T, M> {
   private static final RenderType SLEEPING_EYES = ModRenderTypes.getEyesLayerRenderType(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/white_walkie_sleeping_eyes.png"));
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/white_walkie.png");

   public WhiteWalkieRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new WhiteWalkieModel(ctx.m_174023_(WhiteWalkieModel.LAYER_LOCATION)), 1.2F);
      this.m_115326_(new EyesLayer(this, SLEEPING_EYES, WhiteWalkieEntity::m_5803_));
      this.m_115326_(new WhiteWalkieSaddleLayer(ctx, this));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   protected void setupRotations(T entity, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
      super.m_7523_(entity, matrixStack, ageInTicks, rotationYaw, partialTicks);
      if (entity.m_5803_()) {
         matrixStack.m_85837_((double)0.0F, -0.55, (double)0.0F);
         ((WhiteWalkieModel)this.m_7200_()).setSleepPosition(true);
      } else {
         ((WhiteWalkieModel)this.m_7200_()).setSleepPosition(false);
      }

   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<WhiteWalkieEntity> {
      public EntityRenderer<WhiteWalkieEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new WhiteWalkieRenderer(ctx);
      }
   }
}
