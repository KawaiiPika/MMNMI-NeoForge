package xyz.pixelatedw.mineminenomi.renderers.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GearFourthPointRenderer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel & HeadedModel> extends MorphRenderer<T, M> {
   public GearFourthPointRenderer(EntityRendererProvider.Context ctx, MorphInfo info, M model) {
      super(ctx, info, model);
      this.setCullingState(true);
   }

   public void m_7392_(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
   }

   protected void renderModel(T entity, PoseStack poseStack, int packedLight, MultiBufferSource buffer, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      poseStack.m_85836_();
      boolean isMoving = Math.sqrt(entity.m_20184_().f_82479_ * entity.m_20184_().f_82479_ + entity.m_20184_().f_82481_ * entity.m_20184_().f_82481_) * (double)entity.f_20902_ > (double)0.05F;
      if (isMoving) {
         poseStack.m_252781_(Axis.f_252529_.m_252977_(entity.m_146909_() + 90.0F));
      }

      super.renderModel(entity, poseStack, packedLight, buffer, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
      poseStack.m_85849_();
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
         GearFourthPointRenderer<LivingEntity, M> renderer = new GearFourthPointRenderer<LivingEntity, M>(ctx, this.info, this.model);
         renderer.setCullingState(this.hasCulling);
         return renderer;
      }
   }
}
