package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.abilities.zushi.AbareHimatsuriAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class AbareHimatsuriLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public AbareHimatsuriLayer(RenderLayerParent<T, M> parent) {
      super(parent);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.m_6084_() && !entity.m_20096_()) {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props != null) {
            AbareHimatsuriAbility abl = (AbareHimatsuriAbility)props.getEquippedAbility((AbilityCore)AbareHimatsuriAbility.INSTANCE.get());
            if (abl != null) {
               this.renderBlockPlatform(abl.blocks, matrixStack, buffer, packedLight, partialTicks, entity);
            }
         }
      }
   }

   private void renderBlockPlatform(BlockState[] blocks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, T entity) {
      if (blocks != null) {
         matrixStack.m_85836_();
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(-Mth.m_14179_(partialTicks, entity.f_20884_, entity.f_20883_)));
         int i = blocks.length - 1;

         for(int x = -1; x <= 1; ++x) {
            for(int y = 0; y <= 1; ++y) {
               for(int z = -1; z <= 1; ++z) {
                  if (i < 0) {
                     return;
                  }

                  if (i != 0 && i != 2 && i != 12 && i != 14) {
                     ItemStack stack = blocks[i].m_60734_().m_5456_().m_7968_();
                     matrixStack.m_85836_();
                     matrixStack.m_252880_((float)x, (float)(y + 2), (float)z);
                     matrixStack.m_252781_(Axis.f_252529_.m_252977_(180.0F));
                     Minecraft.m_91087_().m_91291_().m_269128_(stack, ItemDisplayContext.HEAD, packedLight, OverlayTexture.f_118083_, matrixStack, buffer, entity.m_9236_(), 0);
                     matrixStack.m_85849_();
                     --i;
                  } else {
                     --i;
                  }
               }
            }
         }

         matrixStack.m_85849_();
      }
   }
}
