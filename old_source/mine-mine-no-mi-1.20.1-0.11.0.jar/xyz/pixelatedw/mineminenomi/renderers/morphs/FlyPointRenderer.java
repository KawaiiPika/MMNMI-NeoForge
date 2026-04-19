package xyz.pixelatedw.mineminenomi.renderers.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class FlyPointRenderer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel & HeadedModel> extends MorphRenderer<T, M> {
   public FlyPointRenderer(EntityRendererProvider.Context ctx, MorphInfo info, M model) {
      super(ctx, info, model);
      this.setCullingState(true);
   }

   public void m_7392_(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
   }

   protected void renderModel(T entity, PoseStack matrixStack, int packedLight, MultiBufferSource buffer, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_85836_();
      if (!entity.m_20096_()) {
         matrixStack.m_85837_((double)0.0F, (double)1.501F, (double)0.0F);
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(headPitch));
         double distanceX = entity.m_20185_() - entity.f_19854_;
         double distanceZ = entity.m_20189_() - entity.f_19856_;
         float movementSpeed = Mth.m_14116_((float)(distanceX * distanceX + distanceZ * distanceZ));
         float maxRotation = movementSpeed * 20.0F;
         float rot = -netHeadYaw * 3.0F;
         rot = Mth.m_14036_(rot, -maxRotation, maxRotation);
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(rot));
         matrixStack.m_85837_((double)0.0F, (double)-1.501F, (double)0.0F);
      }

      super.renderModel(entity, matrixStack, packedLight, buffer, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
      matrixStack.m_85849_();
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
         FlyPointRenderer<LivingEntity, M> renderer = new FlyPointRenderer<LivingEntity, M>(ctx, this.info, this.model);
         renderer.setCullingState(this.hasCulling);
         return renderer;
      }
   }
}
