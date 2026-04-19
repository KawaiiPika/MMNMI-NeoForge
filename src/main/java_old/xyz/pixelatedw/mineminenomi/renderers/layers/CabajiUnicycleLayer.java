package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.UnicycleModel;
import xyz.pixelatedw.mineminenomi.renderers.entities.vehicles.UnicycleRenderer;

public class CabajiUnicycleLayer<E extends CabajiEntity, M extends HumanoidModel<E>> extends RenderLayer<E, M> {
   private UnicycleModel unicycle;

   public CabajiUnicycleLayer(EntityRendererProvider.Context ctx, RenderLayerParent<E, M> parent) {
      super(parent);
      this.unicycle = new UnicycleModel(ctx);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, E entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.hasUnicycle()) {
         matrixStack.m_85836_();
         matrixStack.m_85837_((double)0.0F, 0.3, (double)0.0F);
         RenderType renderType = RenderType.m_110452_(UnicycleRenderer.TEXTURE);
         this.unicycle.m_7695_(matrixStack, buffer.m_6299_(renderType), packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStack.m_85849_();
      }

   }
}
