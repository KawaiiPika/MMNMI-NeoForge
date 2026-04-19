package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.KuroobiEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.hair.KuroobiHairModel;

public class KuroobiHairLayer<E extends KuroobiEntity, M extends HumanoidModel<E>> extends RenderLayer<E, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/armor/kuroobi_hair.png");
   private KuroobiHairModel<E> hair;

   public KuroobiHairLayer(EntityRendererProvider.Context ctx, RenderLayerParent<E, M> parent) {
      super(parent);
      this.hair = new KuroobiHairModel<E>(ctx.m_174023_(KuroobiHairModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, E entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_85836_();
      RenderType renderType = RenderType.m_110452_(TEXTURE);
      this.hair.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.hair.m_7695_(matrixStack, buffer.m_6299_(renderType), packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85849_();
   }
}
