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
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.hair.Mr3HairModel;

public class Mr3HairLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/armor/mr3_hair.png");
   private final Mr3HairModel<T> model;

   public Mr3HairLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new Mr3HairModel<T>(ctx.m_174023_(Mr3HairModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      RenderType renderType = RenderType.m_110452_(TEXTURE);
      ((HumanoidModel)this.m_117386_()).m_102872_(this.model);
      this.model.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.model.m_7695_(matrixStack, buffer.m_6299_(renderType), packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
