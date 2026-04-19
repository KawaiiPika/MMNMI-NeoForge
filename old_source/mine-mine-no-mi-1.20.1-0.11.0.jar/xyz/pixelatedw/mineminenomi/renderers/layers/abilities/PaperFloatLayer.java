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
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.abilities.mini.MiniMiniAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.CubeModel;

public class PaperFloatLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final CubeModel<T> model;

   public PaperFloatLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new CubeModel<T>(ctx.m_174023_(CubeModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      boolean hasPaper = entity.m_21205_().m_41720_() == Items.f_42516_ || entity.m_21206_().m_41720_() == Items.f_42516_;
      boolean inAir = !entity.m_20096_() && entity.m_20184_().f_82480_ < (double)0.0F;
      if (hasPaper && inAir) {
         boolean isMiniActive = (Boolean)AbilityCapability.get(entity).map((props) -> (MiniMiniAbility)props.getEquippedAbility((AbilityCore)MiniMiniAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
         if (isMiniActive) {
            matrixStack.m_85836_();
            matrixStack.m_85837_((double)0.0F, -0.7, (double)0.0F);
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(netHeadYaw));
            matrixStack.m_85841_(2.4F, 0.5F, 2.5F);
            RenderType type = ModRenderTypes.TRANSPARENT_COLOR;
            VertexConsumer ivertexbuilder = buffer.m_6299_(type);
            this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.m_85849_();
         }

      }
   }
}
