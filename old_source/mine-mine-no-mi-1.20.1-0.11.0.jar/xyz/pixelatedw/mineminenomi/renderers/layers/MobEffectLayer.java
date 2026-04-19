package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.api.effects.IBlockOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public class MobEffectLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private float[] rgbaColor = new float[4];

   public MobEffectLayer(RenderLayerParent<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      for(MobEffectInstance instance : entity.m_21220_()) {
         MobEffect effect = instance.m_19544_();
         if (effect instanceof IBlockOverlayEffect blockOverlay) {
            Block overlay = blockOverlay.getBlockOverlay(instance.m_19557_(), instance.m_19564_());
            if (overlay != null) {
               this.renderBlockOverlay(overlay, matrixStack, buffer, packedLight, entity);
            }
         } else if (effect instanceof IColorOverlayEffect colorOverlay) {
            Color colors = colorOverlay.getBodyOverlayColor(instance.m_19557_(), instance.m_19564_());
            if (colors != null) {
               this.renderColorOverlay(colors, matrixStack, buffer, packedLight);
            }
         } else if (effect instanceof ITextureOverlayEffect textureOverlay) {
            ResourceLocation texture = textureOverlay.getBodyTexture(instance.m_19557_(), instance.m_19564_());
            Color colors = ((ITextureOverlayEffect)effect).getTextureOverlayColor();
            if (texture != null) {
               this.renderTextureOverlay(texture, colors, matrixStack, buffer, packedLight);
            }
         }
      }

   }

   private void renderTextureOverlay(ResourceLocation texture, Color colors, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      VertexConsumer vertex = buffer.m_6299_(RenderType.m_110473_(texture));
      colors.getRGBComponents(this.rgbaColor);
      this.m_117386_().m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, this.rgbaColor[0], this.rgbaColor[1], this.rgbaColor[2], this.rgbaColor[3]);
      matrixStack.m_85849_();
   }

   private void renderColorOverlay(Color colors, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      VertexConsumer vertex = buffer.m_6299_(ModRenderTypes.TRANSPARENT_COLOR);
      colors.getRGBComponents(this.rgbaColor);
      this.m_117386_().m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, this.rgbaColor[0], this.rgbaColor[1], this.rgbaColor[2], this.rgbaColor[3]);
      matrixStack.m_85849_();
   }

   private void renderBlockOverlay(Block overlay, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity) {
      ItemStack stack = overlay.m_5456_().m_7968_();
      matrixStack.m_85836_();
      float blocksWidth = (float)(Math.ceil((double)entity.m_20205_()) + (double)1.0F);
      float blocksHeight = (float)(Math.ceil((double)entity.m_20206_()) + (double)1.0F);
      matrixStack.m_252880_(0.4F - blocksWidth / 2.0F, 1.4F - entity.m_20206_() / 2.0F - blocksHeight / 2.0F, 0.4F - blocksWidth / 2.0F);

      for(int x = 0; (float)x < blocksWidth; ++x) {
         for(int y = 0; (float)y < blocksHeight; ++y) {
            for(int z = 0; (float)z < blocksWidth; ++z) {
               matrixStack.m_85836_();
               matrixStack.m_252880_((float)x, (float)y, (float)z);
               Minecraft.m_91087_().m_91291_().m_269128_(stack, ItemDisplayContext.HEAD, packedLight, OverlayTexture.f_118083_, matrixStack, buffer, entity.m_9236_(), 0);
               matrixStack.m_85849_();
            }
         }
      }

      matrixStack.m_85849_();
   }
}
