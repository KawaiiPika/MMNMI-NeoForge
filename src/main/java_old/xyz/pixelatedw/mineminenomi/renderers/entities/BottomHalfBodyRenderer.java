package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.entities.BottomHalfBodyEntity;
import xyz.pixelatedw.mineminenomi.models.entities.BottomHalfModel;

@OnlyIn(Dist.CLIENT)
public class BottomHalfBodyRenderer extends EntityRenderer<BottomHalfBodyEntity> {
   private BottomHalfModel<LivingEntity> model;

   public BottomHalfBodyRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.model = new BottomHalfModel<LivingEntity>(ctx.m_174023_(BottomHalfModel.LAYER_LOCATION));
   }

   public void render(BottomHalfBodyEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.0F, (double)1.5F, (double)0.0F);
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(180.0F));
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(entity.m_146908_() + 180.0F));
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      if (entity.f_20916_ > 0) {
         matrixStack.m_85836_();
         matrixStack.m_85849_();
      }

      float headYawOffset = Mth.m_14189_(partialTicks, entity.f_20884_, entity.f_20883_);
      float headYawRotation = Mth.m_14189_(partialTicks, entity.f_20886_, entity.f_20885_);
      float netHeadYaw = headYawRotation - headYawOffset;
      float headPitch = Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_());
      float f7 = (float)entity.f_19797_ + partialTicks;
      float limbSwingAmount = 0.0F;
      float limbSwing = 0.0F;
      if (entity.m_6084_()) {
         limbSwingAmount = Mth.m_14179_(partialTicks, entity.f_267362_.m_267711_(0.0F), entity.f_267362_.m_267711_(1.0F));
         limbSwing = entity.f_267362_.m_267756_() - entity.f_267362_.m_267711_(1.0F) * (1.0F - partialTicks);
         if (entity.m_6162_()) {
            limbSwing *= 3.0F;
         }

         if (limbSwingAmount > 1.0F) {
            limbSwingAmount = 1.0F;
         }
      }

      ResourceLocation res = this.getTextureLocation(entity);
      VertexConsumer ivertexbuilder = buffer.m_6299_(this.model.m_103119_(res));
      this.model.f_102610_ = false;
      this.model.m_6839_(entity, limbSwing, limbSwingAmount, partialTicks);
      this.model.m_6973_(entity, limbSwing, limbSwingAmount, f7, netHeadYaw, headPitch);
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      this.model.m_8009_(false);
      this.model.f_102814_.f_104207_ = true;
      this.model.f_102813_.f_104207_ = true;
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(BottomHalfBodyEntity entity) {
      LivingEntity owner = entity.getOwner();
      if (owner != null) {
         if (owner instanceof AbstractClientPlayer) {
            AbstractClientPlayer player = (AbstractClientPlayer)owner;
            return player.m_108560_();
         } else {
            return DefaultPlayerSkin.m_118627_(owner.m_20148_());
         }
      } else {
         return DefaultPlayerSkin.m_118626_();
      }
   }

   public static class Factory implements EntityRendererProvider<BottomHalfBodyEntity> {
      public EntityRenderer<BottomHalfBodyEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new BottomHalfBodyRenderer(ctx);
      }
   }
}
