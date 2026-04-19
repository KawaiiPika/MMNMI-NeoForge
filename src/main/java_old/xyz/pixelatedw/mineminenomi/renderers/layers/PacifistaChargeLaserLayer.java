package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.PacifistaModel;

public class PacifistaChargeLaserLayer<T extends PacifistaEntity, M extends PacifistaModel<T>> extends RenderLayer<T, M> {
   private static final RenderType RENDER_TYPE = RenderType.m_110452_(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/pacifista_charging.png"));

   public PacifistaChargeLaserLayer(RenderLayerParent<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      if (entity.isChargingLaser()) {
         VertexConsumer vertex = buffer.m_6299_(RENDER_TYPE);
         ((PacifistaModel)this.m_117386_()).m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

   }
}
