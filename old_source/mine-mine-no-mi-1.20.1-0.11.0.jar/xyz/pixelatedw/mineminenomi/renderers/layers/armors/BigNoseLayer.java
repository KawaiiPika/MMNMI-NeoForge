package xyz.pixelatedw.mineminenomi.renderers.layers.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.models.armors.BigNoseModel;

public class BigNoseLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/armor/big_nose.png");
   private final BigNoseModel<T> model;

   public BigNoseLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new BigNoseModel<T>(ctx.m_174023_(BigNoseModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      boolean flag = !entity.m_20145_();
      boolean flag1 = !flag && !entity.m_20177_(Minecraft.m_91087_().f_91074_);
      VertexConsumer ivertexbuilder = buffer.m_6299_(((HumanoidModel)this.m_117386_()).m_103119_(TEXTURE));
      int overlay = LivingEntityRenderer.m_115338_(entity, 0.0F);
      matrixStack.m_85836_();
      ((HumanoidModel)this.m_117386_()).m_102872_(this.model);
      matrixStack.m_85837_((double)0.0F, -0.03, (double)0.0F);
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, overlay, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
      matrixStack.m_85849_();
   }
}
