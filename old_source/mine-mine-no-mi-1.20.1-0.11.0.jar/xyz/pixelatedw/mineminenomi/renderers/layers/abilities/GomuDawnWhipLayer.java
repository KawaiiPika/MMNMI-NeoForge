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
import xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoDawnWhipAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.effects.SpiralEffectModel;

public class GomuDawnWhipLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private SpiralEffectModel<T> model;

   public GomuDawnWhipLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new SpiralEffectModel<T>(ctx.m_174023_(SpiralEffectModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      boolean hasGearFifthActive = (Boolean)AbilityCapability.get(entity).map((props) -> (GomuGomuNoDawnWhipAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoDawnWhipAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      if (hasGearFifthActive) {
         matrixStack.m_85836_();
         matrixStack.m_85837_((double)0.0F, 1.55, (double)0.25F);
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(90.0F));
         float scale = 2.5F;
         matrixStack.m_85841_(scale, scale, scale);
         VertexConsumer ivb = buffer.m_6299_(RenderType.m_110473_(ModResources.PROJ_EFFECT_2));
         this.model.m_6973_(entity, 0.0F, 0.0F, ageInTicks * 1.2F, 0.0F, 0.0F);
         this.model.m_7695_(matrixStack, ivb, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.5F);
         matrixStack.m_85849_();
         matrixStack.m_85836_();
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(45.0F));
         ivb = buffer.m_6299_(ModRenderTypes.ENERGY);
         this.m_117386_().m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.m_117386_().m_7695_(matrixStack, ivb, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.1F);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(90.0F));
         this.m_117386_().m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.m_117386_().m_7695_(matrixStack, ivb, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.1F);
         matrixStack.m_85849_();
      }
   }
}
