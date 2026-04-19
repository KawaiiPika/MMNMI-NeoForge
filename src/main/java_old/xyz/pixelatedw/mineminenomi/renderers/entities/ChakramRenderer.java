package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.entities.ChakramEntity;

public class ChakramRenderer<T extends ChakramEntity> extends EntityRenderer<T> {
   protected ChakramRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.05F;
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.f_19797_ >= 2) {
         matrixStack.m_85836_();
         float rotation = (float)entity.f_19797_ * 20.0F % 360.0F;
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14179_(partialTicks, entity.f_19859_, entity.m_146908_()) + rotation));
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_()) - 180.0F));
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
         matrixStack.m_85837_(-0.1, 0.1, (double)0.0F);
         ItemStack stack = entity.getItemUsed();
         LivingEntity owner = (LivingEntity)entity.m_19749_();
         if (stack != null && !stack.m_41619_()) {
            Minecraft.m_91087_().m_91291_().m_269491_(owner, stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, matrixStack, buffer, entity.m_9236_(), packedLight, OverlayTexture.f_118083_, entity.m_19879_() + ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal());
         }

         matrixStack.m_85849_();
      }
   }

   public ResourceLocation getTextureLocation(T entity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<ChakramEntity> {
      public EntityRenderer<ChakramEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new ChakramRenderer<ChakramEntity>(ctx);
      }
   }
}
