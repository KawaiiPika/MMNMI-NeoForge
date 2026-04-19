package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.CelestialDragonSlaveRideModel;

public class CelestialDragonSlaveRideLayer<E extends CelestialDragonEntity, M extends HumanoidModel<E>> extends RenderLayer<E, M> {
   private static final ResourceLocation VILLAGER_BASE_SKIN = ResourceLocation.parse("textures/entity/villager/villager.png");
   private CelestialDragonSlaveRideModel<LivingEntity> ride;

   public CelestialDragonSlaveRideLayer(EntityRendererProvider.Context ctx, RenderLayerParent<E, M> parent) {
      super(parent);
      this.ride = new CelestialDragonSlaveRideModel<LivingEntity>(ctx.m_174023_(CelestialDragonSlaveRideModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, E entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.isRidingSlave()) {
         matrixStack.m_85836_();
         RenderType renderType = RenderType.m_110452_(VILLAGER_BASE_SKIN);
         this.ride.m_6973_((LivingEntity)entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.ride.m_7695_(matrixStack, buffer.m_6299_(renderType), packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStack.m_85849_();
      }
   }
}
