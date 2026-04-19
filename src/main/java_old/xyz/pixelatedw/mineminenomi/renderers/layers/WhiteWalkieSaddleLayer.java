package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.WhiteWalkieSaddleModel;

public class WhiteWalkieSaddleLayer<T extends WhiteWalkieEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation SADDLE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/white_walkie_saddle.png");
   private final WhiteWalkieSaddleModel<T> model;

   public WhiteWalkieSaddleLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new WhiteWalkieSaddleModel<T>(ctx.m_174023_(WhiteWalkieSaddleModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      if (entity.m_21824_() && entity.isSaddled()) {
         matrixStack.m_85836_();
         matrixStack.m_85841_(1.1F, 1.1F, 1.1F);
         this.model.prepareMobModel(entity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
         RenderLayer.m_117376_(this.model, SADDLE_TEXTURE, matrixStack, buffer, packedLight, entity, 1.0F, 1.0F, 1.0F);
         matrixStack.m_85849_();
      }

   }
}
