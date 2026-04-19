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
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruBallAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.SphereModel;

public class DoruBallLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final SphereModel<T> model;

   public DoruBallLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new SphereModel<T>(ctx.m_174023_(SphereModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         DoruDoruBallAbility ability = (DoruDoruBallAbility)props.getEquippedAbility((AbilityCore)DoruDoruBallAbility.INSTANCE.get());
         if (ability != null && ability.isContinuous()) {
            ability.rotateAngleX += (double)entity.f_20900_;
            ability.rotateAngleX %= (double)360.0F;
            ability.rotateAngleZ += (double)entity.f_20902_;
            ability.rotateAngleZ %= (double)360.0F;
            ability.randomizeColor(entity);
            matrixStack.m_85836_();
            matrixStack.m_85837_((double)0.0F, 0.3, (double)0.0F);
            float scale = 8.0F;
            matrixStack.m_85841_(scale, scale, scale);
            matrixStack.m_252781_(Axis.f_252529_.m_252977_((float)ability.rotateAngleZ));
            matrixStack.m_252781_(Axis.f_252403_.m_252977_((float)ability.rotateAngleX));
            VertexConsumer vertex = buffer.m_6299_(RenderType.m_110452_(ModResources.CANDLE_TEXTURE));
            this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, ability.color[0], ability.color[1], ability.color[2], 1.0F);
            matrixStack.m_85849_();
         }
      }
   }
}
