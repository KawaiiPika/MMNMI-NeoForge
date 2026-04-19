package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.entities.projectiles.ThrowingWeaponEntity;

public class ThrowingWeaponRenderer<T extends ThrowingWeaponEntity, M extends EntityModel<T>> extends EntityRenderer<T> {
   public ThrowingWeaponRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.f_19797_ >= 2) {
         matrixStack.m_85836_();
         matrixStack.m_252880_(0.0F, 0.5F, 0.0F);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14179_(partialTicks, entity.f_19859_, entity.m_146908_()) + 180.0F));
         matrixStack.m_252781_(Axis.f_252495_.m_252977_(Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_())));
         matrixStack.m_252781_(Axis.f_252495_.m_252977_(entity.getRotation()));
         ItemStack stack = entity.getItem();
         LivingEntity owner = entity.getOwner();
         if (stack != null && !stack.m_41619_()) {
            Minecraft.m_91087_().m_91291_().m_269491_(owner, stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, matrixStack, buffer, entity.m_9236_(), packedLight, OverlayTexture.f_118083_, entity.m_19879_() + ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal());
         }

         matrixStack.m_85849_();
      }
   }

   public ResourceLocation getTextureLocation(T pEntity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<ThrowingWeaponEntity> {
      public EntityRenderer<ThrowingWeaponEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new ThrowingWeaponRenderer(ctx);
      }
   }
}
