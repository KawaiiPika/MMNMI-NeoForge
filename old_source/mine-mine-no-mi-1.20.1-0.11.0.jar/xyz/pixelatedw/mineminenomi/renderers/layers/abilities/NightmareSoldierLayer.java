package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class NightmareSoldierLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public NightmareSoldierLayer(RenderLayerParent<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      EntityModel var12 = this.m_117386_();
      if (var12 instanceof HumanoidModel humanoidModel) {
         humanoidModel.f_102809_.f_104207_ = false;
      }

      VertexConsumer vertex = buffer.m_6299_(ModRenderTypes.getAbilityBody(ModResources.BUSOSHOKU_HAKI_ARM));
      this.m_117386_().m_7695_(poseStack, vertex, packedLight, OverlayTexture.f_118083_, 0.2F, 0.2F, 0.2F, 0.8F);
   }
}
