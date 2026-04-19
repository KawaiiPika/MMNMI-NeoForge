package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WanderingDugongEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class WanderingDugongScarsLayer<T extends WanderingDugongEntity, M extends DugongModel<T>> extends RenderLayer<T, M> {
   public WanderingDugongScarsLayer(RenderLayerParent<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.getHeadScarId() >= 0) {
         ResourceLocation headScarTexture = MobsHelper.DUGONG_HEAD_SCARS_TEXTURES[entity.getHeadScarId()];
         VertexConsumer vertex = buffer.m_6299_(RenderType.m_110458_(headScarTexture));
         ((DugongModel)this.m_117386_()).m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (entity.getChestScarId() >= 0) {
         ResourceLocation chestScarTexture = MobsHelper.DUGONG_CHEST_SCARS_TEXTURES[entity.getChestScarId()];
         VertexConsumer vertex = buffer.m_6299_(RenderType.m_110458_(chestScarTexture));
         ((DugongModel)this.m_117386_()).m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (entity.getArmsScarId() >= 0) {
         ResourceLocation armsScarTexture = MobsHelper.DUGONG_ARMS_SCARS_TEXTURES[entity.getArmsScarId()];
         VertexConsumer vertex = buffer.m_6299_(RenderType.m_110458_(armsScarTexture));
         ((DugongModel)this.m_117386_()).m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (entity.getTailScarId() >= 0) {
         ResourceLocation tailScarTexture = MobsHelper.DUGONG_TAIL_SCARS_TEXTURES[entity.getTailScarId()];
         VertexConsumer vertex = buffer.m_6299_(RenderType.m_110458_(tailScarTexture));
         ((DugongModel)this.m_117386_()).m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

   }
}
