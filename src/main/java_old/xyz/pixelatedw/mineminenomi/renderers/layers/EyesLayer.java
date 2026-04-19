package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Predicate;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;

public class EyesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final RenderType eyesRenderer;
   private final Predicate<T> predicate;

   public EyesLayer(RenderLayerParent<T, M> renderer, RenderType eyesRenderer, Predicate<T> predicate) {
      super(renderer);
      this.eyesRenderer = eyesRenderer;
      this.predicate = predicate;
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      if (this.predicate.test(entity)) {
         VertexConsumer ivertexbuilder = buffer.m_6299_(this.eyesRenderer);
         this.m_117386_().m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

   }
}
