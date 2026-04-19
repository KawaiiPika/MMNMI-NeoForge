package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.HanaHandsEntity;

public class HanaHandsRenderer<T extends HanaHandsEntity> extends EntityRenderer<T> {
   private EntityRenderer<? super Entity> ownerRenderer;
   private EntityRenderer<? super Entity> targetRenderer;

   private HanaHandsRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.0F;
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      HanaHandsEntity.HandsKind type = entity.getHandsType();
      float progress = entity.getAnimationProgress(partialTicks);
      LivingEntity target = entity.getTarget();
      if (target != null && entity.m_19749_() != null) {
         float animation = 1.0F;
         if (progress > 0.5F && progress < 0.6F) {
            animation += progress / 2.0F;
         } else if (progress > 0.6F) {
            animation = (float)((double)animation * (((double)1.0F - (double)progress) / (double)0.4F));
         }

         matrixStack.m_85836_();
         if (this.ownerRenderer == null) {
            this.ownerRenderer = Minecraft.m_91087_().m_91290_().m_114382_(entity.m_19749_());
         }

         if (this.targetRenderer == null) {
            this.targetRenderer = Minecraft.m_91087_().m_91290_().m_114382_(target);
         }

         if (this.ownerRenderer != null) {
            EntityRenderer var12 = this.ownerRenderer;
            if (var12 instanceof LivingEntityRenderer) {
               LivingEntityRenderer livingRenderer = (LivingEntityRenderer)var12;
               ResourceLocation texture = DefaultPlayerSkin.m_118627_(entity.m_20148_());
               if (entity.m_19749_() != null) {
                  Entity var14 = entity.m_19749_();
                  if (var14 instanceof AbstractClientPlayer) {
                     AbstractClientPlayer player = (AbstractClientPlayer)var14;
                     texture = player.m_108560_();
                  }
               }

               VertexConsumer vertex = buffer.m_6299_(RenderType.m_110452_(texture));
               ModelPart pivotModel = null;
               float armsPos = -1.0F;
               EntityRenderer var17 = this.targetRenderer;
               if (var17 instanceof LivingEntityRenderer) {
                  LivingEntityRenderer renderer = (LivingEntityRenderer)var17;
                  EntityModel var19 = renderer.m_7200_();
                  if (var19 instanceof HumanoidModel) {
                     HumanoidModel humanoidModel = (HumanoidModel)var19;
                     pivotModel = humanoidModel.f_102810_;
                  } else {
                     var19 = renderer.m_7200_();
                     if (var19 instanceof HeadedModel) {
                        HeadedModel headedModel = (HeadedModel)var19;
                        pivotModel = headedModel.m_5585_();
                     }
                  }
               } else {
                  armsPos = entity.m_20206_() / 8.0F;
               }

               if (pivotModel != null) {
                  pivotModel.m_104299_(matrixStack);
               } else {
                  matrixStack.m_252880_(0.0F, -armsPos, 0.0F);
               }

               List<ModelPart> arms = RendererHelper.getArmPartsFrom(livingRenderer.m_7200_());
               if (arms.isEmpty()) {
                  return;
               }

               float targetBodyYaw = Mth.m_14189_(partialTicks, target.f_20884_, target.f_20883_);

               for(ModelPart arm : arms) {
                  matrixStack.m_85836_();
                  arm.m_233569_();
                  if (type == HanaHandsEntity.HandsKind.CLUTCH) {
                     matrixStack.m_85841_(animation, animation, animation);
                     matrixStack.m_252781_(Axis.f_252393_.m_252977_(90.0F));
                     matrixStack.m_252781_(Axis.f_252495_.m_252977_(-targetBodyYaw));
                     matrixStack.m_85837_((double)-0.5F, -0.4, 0.05);
                     arm.m_104306_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
                     matrixStack.m_85837_((double)-0.5F, (double)0.0F, 0.05);
                     arm.m_104306_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
                  } else if (type == HanaHandsEntity.HandsKind.SLAP) {
                     matrixStack.m_85837_((double)0.0F, (double)1.0F, (double)0.0F);
                     matrixStack.m_252781_(Axis.f_252436_.m_252977_(-targetBodyYaw + 90.0F));
                     matrixStack.m_252781_(Axis.f_252529_.m_252977_(90.0F * animation - 45.0F));
                     matrixStack.m_85837_(0.2, (double)0.0F, (double)0.0F);
                     arm.m_104306_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
                  }

                  matrixStack.m_85849_();
               }
            }
         }

         matrixStack.m_85849_();
      }
   }

   public ResourceLocation getTextureLocation(HanaHandsEntity entity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<HanaHandsEntity> {
      public EntityRenderer<HanaHandsEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new HanaHandsRenderer<HanaHandsEntity>(ctx);
      }
   }
}
