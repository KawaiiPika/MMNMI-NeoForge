package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.CampoDeFloresEntity;

public class CampoDeFloresRenderer<T extends CampoDeFloresEntity> extends EntityRenderer<T> {
   private EntityRenderer<? super Entity> ownerRenderer;

   private CampoDeFloresRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.0F;
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.getOwner() != null) {
         if (this.ownerRenderer == null) {
            this.ownerRenderer = Minecraft.m_91087_().m_91290_().m_114382_(entity.getOwner());
         }

         if (this.ownerRenderer != null) {
            EntityRenderer var8 = this.ownerRenderer;
            if (var8 instanceof LivingEntityRenderer) {
               LivingEntityRenderer livingRenderer = (LivingEntityRenderer)var8;
               List<ModelPart> arms = RendererHelper.getArmPartsFrom(livingRenderer.m_7200_());
               ResourceLocation texture = DefaultPlayerSkin.m_118627_(entity.m_20148_());
               if (entity.getOwner() != null) {
                  LivingEntity var11 = entity.getOwner();
                  if (var11 instanceof AbstractClientPlayer) {
                     AbstractClientPlayer player = (AbstractClientPlayer)var11;
                     texture = player.m_108560_();
                  }
               }

               VertexConsumer vertex = buffer.m_6299_(RenderType.m_110452_(texture));
               matrixStack.m_85836_();
               matrixStack.m_85837_((double)0.0F, (double)-0.25F, (double)0.0F);
               Random random = new Random();
               random.setSeed(1L);
               int maxSize = 10;
               int minSize = maxSize - 1;

               for(int i = -minSize; i < maxSize; ++i) {
                  for(int j = -minSize; j < maxSize; ++j) {
                     if (!((double)(i * i + j * j) > Math.pow((double)maxSize, (double)2.0F))) {
                        float spread = 0.2F + random.nextFloat() / 10.0F;
                        float speed = 0.3F + random.nextFloat() / 10.0F;
                        float anim = (float)Math.sin((double)entity.f_19797_ * Math.PI * (double)speed) * spread;
                        matrixStack.m_85836_();
                        matrixStack.m_252880_((float)i / 1.0F, anim, (float)j / 1.0F);

                        for(ModelPart part : arms) {
                           part.m_233569_();
                           part.m_104306_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
                        }

                        matrixStack.m_85849_();
                     }
                  }
               }

               matrixStack.m_85849_();
            }
         }

      }
   }

   public ResourceLocation getTextureLocation(CampoDeFloresEntity entity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<CampoDeFloresEntity> {
      public EntityRenderer<CampoDeFloresEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new CampoDeFloresRenderer<CampoDeFloresEntity>(ctx);
      }
   }
}
