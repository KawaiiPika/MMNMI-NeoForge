package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;

public class SphereRenderer<T extends SphereEntity> extends EntityRenderer<T> {
   private final Minecraft minecraft;
   private Camera camera;

   protected SphereRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.0F;
      this.minecraft = Minecraft.m_91087_();
      this.camera = this.minecraft.f_91063_.m_109153_();
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      entity.getSkybox().renderSphereInWorld(matrixStack, this.camera, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      matrixStack.m_85849_();
   }

   public boolean shouldRender(T entity, Frustum frustum, double camX, double camY, double camZ) {
      return true;
   }

   public ResourceLocation getTextureLocation(T entity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<SphereEntity> {
      public EntityRenderer<SphereEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new SphereRenderer<SphereEntity>(ctx);
      }
   }
}
