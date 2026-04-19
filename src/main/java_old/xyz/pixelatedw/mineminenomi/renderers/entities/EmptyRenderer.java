package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class EmptyRenderer<T extends Entity> extends EntityRenderer<T> {
   public EmptyRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
   }

   public void m_7392_(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation m_5478_(T entity) {
      return null;
   }

   public static class Factory<T extends Entity> implements EntityRendererProvider<T> {
      public EntityRenderer<T> m_174009_(EntityRendererProvider.Context ctx) {
         return new EmptyRenderer<T>(ctx);
      }
   }
}
