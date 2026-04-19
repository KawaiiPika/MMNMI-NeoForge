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
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SharkOnToothAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.effects.SpiralEffectModel;

public class SpiralEffectLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private SpiralEffectModel<T> model;

   public SpiralEffectLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new SpiralEffectModel<T>(ctx.m_174023_(SpiralEffectModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      boolean isUsingAbility = (Boolean)AbilityCapability.get(entity).map((props) -> (SharkOnToothAbility)props.getEquippedAbility((AbilityCore)SharkOnToothAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      if (isUsingAbility) {
         matrixStack.m_85836_();
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(-90.0F));
         matrixStack.m_85837_((double)0.0F, 0.2, -0.3);
         matrixStack.m_85841_(2.0F, 2.0F, 2.0F);
         VertexConsumer vertex = buffer.m_6299_(RenderType.m_110473_(ModResources.PROJ_EFFECT_2));
         this.model.m_6973_(entity, 0.0F, 0.0F, (float)entity.f_19797_, 0.0F, 0.0F);
         this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.5F);
         matrixStack.m_85849_();
      }
   }
}
