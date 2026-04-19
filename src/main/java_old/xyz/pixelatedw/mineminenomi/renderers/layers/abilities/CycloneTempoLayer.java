package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.TornadoModel;

public class CycloneTempoLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final TornadoModel<?> model;

   public CycloneTempoLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new TornadoModel(ctx.m_174023_(TornadoModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      MobEffectInstance instance = entity.m_21124_((MobEffect)ModEffects.CYCLONE_TEMPO.get());
      if (instance != null && instance.m_19557_() > 0) {
         matrixStack.m_85836_();
         float width = entity.m_20205_() * 6.0F;
         float height = entity.m_20206_() * 1.15F;
         matrixStack.m_85841_(width, height, width);
         matrixStack.m_252880_(0.0F, -0.3F, 0.0F);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)entity.f_19797_ * (float)Math.PI * 1.5F));
         VertexConsumer vertex = buffer.m_6299_(ModRenderTypes.TRANSPARENT_COLOR);
         this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 0.5F, 0.8F, 1.0F, 0.4F);
         float x = (float)instance.m_19557_() % 20.0F / 20.0F;
         float anim = 0.5F + (float)Math.sin((double)(x * (float)Math.PI));
         matrixStack.m_85841_(anim, anim, anim);
         matrixStack.m_252880_(0.0F, -0.1F, 0.0F);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)entity.f_19797_ * (float)Math.PI * 2.5F));
         this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 0.5F, 0.8F, 1.0F, 0.4F);
         matrixStack.m_85849_();
      }
   }
}
