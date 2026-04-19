package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntityRenderer;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.HydraProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.HydraModel;

public class HydraRenderer extends NuLightningEntityRenderer {
   private static final ResourceLocation HYDRA_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/projectiles/hydra.png");
   private static final ResourceLocation HYDRA_HEAD_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/projectiles/hydra_head.png");
   protected HydraModel<HydraProjectile> model;

   protected HydraRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.setTexture(HYDRA_TEXTURE);
      this.model = new HydraModel<HydraProjectile>(ctx.m_174023_(HydraModel.LAYER_LOCATION));
   }

   public void render(NuLightningEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
      matrixStack.m_85836_();
      double x = entity.m_20182_().m_7096_() - entity.getPreciseCurrentX();
      double y = entity.m_20182_().m_7098_() - entity.getPreciseCurrentY();
      double z = entity.m_20182_().m_7094_() - entity.getPreciseCurrentZ();
      matrixStack.m_85837_(-x, -y, -z);
      matrixStack.m_252781_(Axis.f_252392_.m_252977_(entity.m_146908_()));
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(entity.m_146909_()));
      matrixStack.m_85841_(-1.0F, -1.0F, -1.0F);
      float size = 3.0F;
      matrixStack.m_85841_(size, size, size);
      float ageInTicks = (float)entity.f_19797_ + partialTicks;
      this.model.m_6973_(entity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
      RenderType type = ModRenderTypes.getPosColorTexLightmap(HYDRA_HEAD_TEXTURE);
      VertexConsumer vertex = buffer.m_6299_(type);
      this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.5F);
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(NuLightningEntity entity) {
      return super.getTextureLocation(entity);
   }

   public static class Factory implements EntityRendererProvider<NuLightningEntity> {
      public EntityRenderer<NuLightningEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new HydraRenderer(ctx);
      }
   }
}
