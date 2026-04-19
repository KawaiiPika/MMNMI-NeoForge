package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraFestivalEntity;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.BaraFestivalModel;

@OnlyIn(Dist.CLIENT)
public class BaraFestivalRenderer<T extends BaraFestivalEntity> extends EntityRenderer<T> {
   private final BaraFestivalModel model;

   protected BaraFestivalRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.model = new BaraFestivalModel(ctx.m_174023_(BaraFestivalModel.LAYER_LOCATION));
      this.f_114477_ = 0.0F;
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      RenderType type = RenderType.m_110473_(this.getTextureLocation(entity));
      VertexConsumer ivertexbuilder = buffer.m_6299_(type);
      this.model.setupAnim(entity, 0.0F, 0.0F, (float)entity.f_19797_, 0.0F, 0.0F);
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(T entity) {
      LivingEntity owner = entity.getOwner();
      if (owner instanceof AbstractClientPlayer player) {
         return player.m_108560_();
      } else {
         return DefaultPlayerSkin.m_118627_(owner != null ? owner.m_20148_() : entity.m_20148_());
      }
   }

   public static class Factory implements EntityRendererProvider<BaraFestivalEntity> {
      public EntityRenderer<BaraFestivalEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new BaraFestivalRenderer<BaraFestivalEntity>(ctx);
      }
   }
}
