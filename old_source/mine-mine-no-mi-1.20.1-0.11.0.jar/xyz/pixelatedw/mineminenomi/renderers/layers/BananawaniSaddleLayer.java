package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.BananawaniSaddleModel;

public class BananawaniSaddleLayer<T extends BananawaniEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final BananawaniSaddleModel<T> model;
   private static final ResourceLocation SADDLE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/bananawani_saddle.png");
   private static final ResourceLocation SADDLE_OVERLAY_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/bananawani_saddle_overlay.png");

   public BananawaniSaddleLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new BananawaniSaddleModel<T>(ctx.m_174023_(BananawaniSaddleModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      if (entity.m_21824_() && entity.isSaddled()) {
         float[] color = entity.getSaddleColor().m_41068_();
         RenderLayer.m_117376_(this.model, SADDLE_OVERLAY_TEXTURE, matrixStack, buffer, packedLight, entity, 1.0F, 1.0F, 1.0F);
         RenderLayer.m_117376_(this.model, SADDLE_TEXTURE, matrixStack, buffer, packedLight, entity, color[0], color[1], color[2]);
      }

   }
}
