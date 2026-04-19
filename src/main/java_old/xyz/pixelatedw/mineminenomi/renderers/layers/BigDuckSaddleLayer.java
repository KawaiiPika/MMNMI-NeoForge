package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BigDuckEntity;

public class BigDuckSaddleLayer<T extends BigDuckEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation SADDLE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/big_duck_saddle.png");

   public BigDuckSaddleLayer(RenderLayerParent<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      if (entity.m_21824_() && entity.m_6254_()) {
         matrixStack.m_85836_();
         matrixStack.m_85841_(1.1F, 1.1F, 1.1F);
         matrixStack.m_85837_((double)0.0F, -0.01, (double)0.0F);
         RenderLayer.m_117376_(this.m_117386_(), SADDLE_TEXTURE, matrixStack, buffer, packedLight, entity, 1.0F, 1.0F, 1.0F);
         matrixStack.m_85849_();
      }

   }
}
