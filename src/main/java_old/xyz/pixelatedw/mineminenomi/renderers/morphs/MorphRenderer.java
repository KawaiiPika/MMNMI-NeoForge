package xyz.pixelatedw.mineminenomi.renderers.morphs;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event.Result;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModLayers;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.renderers.layers.ArrowMorphLayer;

public class MorphRenderer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel & HeadedModel> extends LivingEntityRenderer<T, M> {
   private final MorphInfo info;
   private EntityRenderer<? super T> originalRenderer;

   public MorphRenderer(EntityRendererProvider.Context ctx, MorphInfo info, M model) {
      super(ctx, model, info.getShadowSize());
      this.info = info;
      this.m_115326_(new ItemInHandLayer(this, ctx.m_234598_()));
      this.m_115326_(new ArrowMorphLayer(ctx, this));
      this.m_115326_(new ElytraLayer(this, ctx.m_174027_()));
      ModLayers.addExtraFeatureLayers(ctx, this);
      ModLayers.addGenericLayers(ctx, this);
      ModLayers.addAbilityLayers(ctx, this);
      this.info.addLayers(ctx, this);
   }

   public void m_7392_(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      this.setModelProperties(entity);
      matrixStack.m_85836_();
      this.f_115290_.f_102608_ = this.m_115342_(entity, partialTicks);
      boolean shouldSit = entity.m_20159_() && entity.m_20202_() != null && entity.m_20202_().shouldRiderSit();
      this.f_115290_.f_102609_ = shouldSit;
      this.f_115290_.f_102610_ = entity.m_6162_();
      float headYawOffset = Mth.m_14189_(partialTicks, entity.f_20884_, entity.f_20883_);
      float headYawRotation = Mth.m_14189_(partialTicks, entity.f_20886_, entity.f_20885_);
      float netHeadYaw = headYawRotation - headYawOffset;
      if (shouldSit && entity.m_20202_() instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)entity.m_20202_();
         headYawOffset = Mth.m_14189_(partialTicks, livingentity.f_20884_, livingentity.f_20883_);
         netHeadYaw = headYawRotation - headYawOffset;
         float f3 = Mth.m_14177_(netHeadYaw);
         if (f3 < -85.0F) {
            f3 = -85.0F;
         }

         if (f3 >= 85.0F) {
            f3 = 85.0F;
         }

         headYawOffset = headYawRotation - f3;
         if (f3 * f3 > 2500.0F) {
            headYawOffset += f3 * 0.2F;
         }

         netHeadYaw = headYawRotation - headYawOffset;
      }

      float headPitch = Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_());
      if (entity.m_20089_() == Pose.SLEEPING) {
         Direction direction = entity.m_21259_();
         if (direction != null) {
            float eyeHeight = entity.m_20236_(Pose.STANDING) - 0.1F;
            matrixStack.m_85837_((double)((float)(-direction.m_122429_()) * eyeHeight), (double)0.0F, (double)((float)(-direction.m_122431_()) * eyeHeight));
         }
      }

      float ageInTicks = this.m_6930_(entity, partialTicks);
      this.m_7523_(entity, matrixStack, ageInTicks, headYawOffset, partialTicks);
      matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
      this.m_7546_(entity, matrixStack, partialTicks);
      matrixStack.m_85837_((double)0.0F, (double)-1.501F, (double)0.0F);
      float limbSwingAmount = 0.0F;
      float limbSwing = 0.0F;
      if (!shouldSit && entity.m_6084_()) {
         limbSwingAmount = entity.f_267362_.m_267711_(partialTicks);
         limbSwing = entity.f_267362_.m_267590_(partialTicks);
         if (entity.m_6162_()) {
            limbSwing *= 3.0F;
         }

         if (limbSwingAmount > 1.0F) {
            limbSwingAmount = 1.0F;
         }
      }

      this.renderModel(entity, matrixStack, packedLight, buffer, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
      matrixStack.m_85849_();
      RenderNameTagEvent renderNameTagEvent = new RenderNameTagEvent(entity, entity.m_5446_(), this, matrixStack, buffer, packedLight, partialTicks);
      MinecraftForge.EVENT_BUS.post(renderNameTagEvent);
      if (renderNameTagEvent.getResult() != Result.DENY && (renderNameTagEvent.getResult() == Result.ALLOW || this.m_6512_(entity))) {
         this.m_7649_(entity, renderNameTagEvent.getContent(), matrixStack, buffer, packedLight);
      }

   }

   protected void renderModel(T entity, PoseStack matrixStack, int packedLight, MultiBufferSource buffer, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_7200_().m_6839_(entity, limbSwing, limbSwingAmount, partialTicks);
      boolean isRotationBlocked = entity.m_21220_().stream().anyMatch((instance) -> {
         MobEffect patt7104$temp = instance.m_19544_();
         boolean var10000;
         if (patt7104$temp instanceof IBindBodyEffect bindEffect) {
            if (bindEffect.isBlockingRotation()) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      });
      if (!isRotationBlocked) {
         this.m_7200_().m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         Animation.animationAngles(entity, this.f_115290_, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      }

      Animation.animationPostAngles(entity, this.f_115290_, matrixStack, buffer, packedLight, partialTicks, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      boolean isVisible = this.m_5933_(entity);
      boolean isInvisibleForClient = !isVisible && !entity.m_20177_(Minecraft.m_91087_().f_91074_);
      RenderType renderType = RenderType.m_110473_(this.getTextureLocation(entity));
      if (renderType != null && isVisible) {
         VertexConsumer ivertexbuilder = buffer.m_6299_(renderType);
         int i = m_115338_(entity, this.m_6931_(entity, partialTicks));
         this.f_115290_.m_7695_(matrixStack, ivertexbuilder, packedLight, i, 1.0F, 1.0F, 1.0F, isInvisibleForClient ? 0.15F : 1.0F);
      }

      if (!entity.m_5833_()) {
         for(RenderLayer<T, M> layer : this.f_115291_) {
            if (this.info.shouldRenderLayer(layer, entity, matrixStack, partialTicks)) {
               layer.m_6494_(matrixStack, buffer, packedLight, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
            }
         }
      }

   }

   public void renderFirstPersonLimb(T entity, ItemStack itemStack, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, HumanoidArm side, Optional<AbilityOverlay> overlay, float swingProgress, float equipProgress, boolean isLeg) {
      EntityModel arm = this.m_7200_();
      if (arm instanceof PlayerModel playerModel) {
         this.setModelProperties(entity);
         playerModel.f_102608_ = 0.0F;
         playerModel.f_102817_ = false;
         playerModel.f_102818_ = 0.0F;
         playerModel.m_6973_(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         ModelPart sleeve;
         ModelPart arm;
         if (side == HumanoidArm.RIGHT) {
            arm = playerModel.f_102811_;
            sleeve = playerModel.f_103375_;
         } else {
            arm = playerModel.f_102812_;
            sleeve = playerModel.f_103374_;
         }

         if (entity instanceof AbstractClientPlayer player) {
            arm.f_104203_ = 0.0F;
            arm.m_104301_(matrixStack, buffer.m_6299_(RenderType.m_110446_(player.m_108560_())), combinedLight, OverlayTexture.f_118083_);
            sleeve.f_104203_ = 0.0F;
            sleeve.m_104301_(matrixStack, buffer.m_6299_(RenderType.m_110473_(player.m_108560_())), combinedLight, OverlayTexture.f_118083_);
         }
      } else {
         arm = this.m_7200_();
         if (arm instanceof HumanoidMorphModel morphModel) {
            VertexConsumer vertex = null;
            float red = 1.0F;
            float green = 1.0F;
            float blue = 1.0F;
            float alpha = 1.0F;
            if (overlay.isPresent()) {
               RenderSystem.setShaderTexture(0, ((AbilityOverlay)overlay.get()).getTexture());
               vertex = buffer.m_6299_(RenderType.m_110473_(((AbilityOverlay)overlay.get()).getTexture()));
               Color color = ((AbilityOverlay)overlay.get()).getColor();
               red = (float)color.getRed() / 255.0F;
               green = (float)color.getGreen() / 255.0F;
               blue = (float)color.getBlue() / 255.0F;
               alpha = (float)color.getAlpha() / 255.0F;
            } else {
               RenderSystem.setShaderTexture(0, this.getTextureLocation(entity));
               vertex = buffer.m_6299_(RenderType.m_110473_(this.getTextureLocation(entity)));
            }

            morphModel.renderFirstPersonArm(matrixStack, vertex, combinedLight, OverlayTexture.f_118083_, red, green, blue, alpha, side, isLeg);
         }
      }

   }

   private void setModelProperties(T clientPlayer) {
      M model = this.m_7200_();
      if (model instanceof HumanoidModel humanoidModel) {
         if (clientPlayer.m_5833_()) {
            humanoidModel.m_8009_(false);
            humanoidModel.f_102808_.f_104207_ = true;
            humanoidModel.f_102809_.f_104207_ = true;
         } else {
            humanoidModel.m_8009_(true);
            humanoidModel.f_102817_ = clientPlayer.m_6047_();
         }
      }

   }

   public ResourceLocation getTextureLocation(T entity) {
      ResourceLocation texture = this.info.getTexture(entity);
      return texture != null ? texture : ModResources.NULL_ENTITY_TEXTURE;
   }

   public void removeLayer(Class<?> clz) {
      this.f_115291_.removeIf((layer) -> clz.equals(layer.getClass()));
   }

   public void setCullingState(boolean hasCulling) {
   }

   public void setOriginalRenderer(EntityRenderer<? super T> def) {
      this.originalRenderer = def;
   }

   public EntityRenderer<? super T> getOriginalRenderer() {
      return this.originalRenderer;
   }

   public static class Factory<M extends EntityModel<LivingEntity> & ArmedModel & HeadedModel> implements EntityRendererProvider<LivingEntity> {
      private MorphInfo info;
      private M model;
      private boolean hasCulling;

      public Factory(MorphInfo info, M model) {
         this.info = info;
         this.model = model;
      }

      public Factory(MorphInfo info, boolean hasCulling) {
         this.info = info;
         this.hasCulling = hasCulling;
      }

      public EntityRenderer<LivingEntity> m_174009_(EntityRendererProvider.Context ctx) {
         MorphRenderer<LivingEntity, M> renderer = new MorphRenderer<LivingEntity, M>(ctx, this.info, this.model);
         renderer.setCullingState(this.hasCulling);
         return renderer;
      }
   }
}
