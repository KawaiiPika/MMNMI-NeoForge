package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.GenocideRaidEffectEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class GenocideRaidRenderer<T extends GenocideRaidEffectEntity> extends EntityRenderer<T> {
   protected GenocideRaidRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      float speed = 10.0F;
      float spread = 0.75F;
      matrixStack.m_85836_();
      matrixStack.m_252781_(Axis.f_252495_.m_252977_(180.0F));
      matrixStack.m_85837_((double)0.75F, 0.35, (double)0.75F);
      matrixStack.m_85841_(1.0F, 1.0F, 1.0F);
      if (entity.getTarget() != null && entity.getTarget().m_21023_((MobEffect)ModEffects.GENOCIDE_RAID.get())) {
         int amount = 5;
         float rotAmount = 72.0F;

         for(int i = 0; i < 5; ++i) {
            float rot1 = (float)i * 72.0F;

            for(float j = 0.0F; j < 5.0F; ++j) {
               float rot2 = j * 72.0F;
               matrixStack.m_85836_();
               matrixStack.m_252880_(-0.75F, -0.75F, -0.75F);
               matrixStack.m_252781_(Axis.f_252392_.m_252977_(rot1 + (float)entity.f_19797_ * 10.0F));
               matrixStack.m_252781_(Axis.f_252393_.m_252977_(rot2 + (float)entity.f_19797_ * 10.0F));
               matrixStack.m_252880_(0.75F, 0.75F, 0.75F);
               int seed = (int)(((float)i + j) % (float)entity.getItemsUsed().size());
               ItemStack stack = (ItemStack)entity.getItemsUsed().get(seed);
               Minecraft.m_91087_().m_91291_().m_269491_(entity.getTarget(), stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, matrixStack, buffer, entity.m_9236_(), packedLight, OverlayTexture.f_118083_, 0);
               matrixStack.m_85849_();
            }
         }

         matrixStack.m_85849_();
      } else {
         matrixStack.m_85849_();
      }
   }

   public ResourceLocation getTextureLocation(T pEntity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<GenocideRaidEffectEntity> {
      public EntityRenderer<GenocideRaidEffectEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new GenocideRaidRenderer<GenocideRaidEffectEntity>(ctx);
      }
   }
}
