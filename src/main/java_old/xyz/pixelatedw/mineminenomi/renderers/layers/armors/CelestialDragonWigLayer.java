package xyz.pixelatedw.mineminenomi.renderers.layers.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;

public class CelestialDragonWigLayer<T extends CelestialDragonEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/armor/celestial_dragon_wig.png");
   private final HumanoidModel<T> wigModel;

   public CelestialDragonWigLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> parent) {
      super(parent);
      this.wigModel = new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      RenderType renderType = RenderType.m_110458_(TEXTURE);
      ((HumanoidModel)this.m_117386_()).m_102872_(this.wigModel);
      this.wigModel.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float hairRed = (float)entity.getHairColor().getRed() / 255.0F;
      float hairGreen = (float)entity.getHairColor().getGreen() / 255.0F;
      float hairBlue = (float)entity.getHairColor().getBlue() / 255.0F;
      this.wigModel.m_7695_(matrixStack, buffer.m_6299_(renderType), packedLight, OverlayTexture.f_118083_, hairRed, hairGreen, hairBlue, 1.0F);
   }
}
