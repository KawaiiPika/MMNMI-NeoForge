package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.entities.CandleLockModel;

public class CandleLockLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final CandleLockModel<T> model;

   public CandleLockLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new CandleLockModel<T>(ctx.m_174023_(CandleLockModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      MobEffectInstance instance = entity.m_21124_((MobEffect)ModEffects.CANDLE_LOCK.get());
      if (instance != null && instance.m_19557_() > 0) {
         matrixStack.m_85836_();
         matrixStack.m_252781_(Axis.f_252392_.m_252977_(entity.f_19860_ + (entity.m_146909_() - entity.f_19860_) * partialTicks + 180.0F));
         VertexConsumer vertex = buffer.m_6299_(RenderType.m_110452_(ModResources.CANDLE_TEXTURE));
         this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStack.m_85849_();
      }
   }
}
