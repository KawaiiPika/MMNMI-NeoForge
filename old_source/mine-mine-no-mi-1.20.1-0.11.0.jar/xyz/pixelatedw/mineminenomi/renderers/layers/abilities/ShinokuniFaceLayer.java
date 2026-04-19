package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class ShinokuniFaceLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel & HeadedModel> extends RenderLayer<T, M> {
   private MorphRenderer<T, M> parent;

   public ShinokuniFaceLayer(MorphRenderer<T, M> parent) {
      super(parent);
      this.parent = parent;
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!entity.m_20145_()) {
         matrixStack.m_85836_();
         matrixStack.m_85837_((double)0.0F, (double)-0.25F, -0.3);
         matrixStack.m_85841_(0.5F, 0.5F, 0.5F);
         headPitch = Mth.m_14036_(headPitch, -17.0F, 60.0F);
         netHeadYaw = Mth.m_14036_(netHeadYaw, -27.0F, 27.0F);
         if (entity.f_20921_ > 0.0F) {
            netHeadYaw = (float)Math.toDegrees((double)(Mth.m_14031_(Mth.m_14116_(entity.f_20921_) * ((float)Math.PI * 2F)) * 0.2F));
         }

         LivingEntityRenderer<T, M> ogRenderer = (LivingEntityRenderer)this.parent.getOriginalRenderer();
         ResourceLocation skin = ogRenderer.m_5478_(entity);
         RenderType renderType = RenderType.m_110452_(skin);
         RendererHelper.getModelParts(ogRenderer.m_7200_()).forEach((p) -> p.f_104207_ = false);
         ogRenderer.m_7200_().f_102610_ = false;
         ((HeadedModel)ogRenderer.m_7200_()).m_5585_().f_104207_ = true;
         ogRenderer.m_7200_().m_6839_(entity, limbSwing, limbSwingAmount, partialTicks);
         ogRenderer.m_7200_().m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         ogRenderer.m_7200_().m_7695_(matrixStack, buffer.m_6299_(renderType), packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStack.m_85849_();
      }
   }
}
