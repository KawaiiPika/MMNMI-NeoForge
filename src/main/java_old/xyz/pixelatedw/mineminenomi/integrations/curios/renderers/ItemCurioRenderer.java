package xyz.pixelatedw.mineminenomi.integrations.curios.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ItemCurioRenderer implements ICurioRenderer {
   public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext ctx, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_85836_();
      EntityRenderer<? super LivingEntity> render = Minecraft.m_91087_().m_91290_().m_114382_(ctx.entity());
      if (render instanceof LivingEntityRenderer livingRenderer) {
         EntityModel<LivingEntity> model = livingRenderer.m_7200_();
         if (model instanceof HumanoidModel humanoidModel) {
            humanoidModel.m_5585_().m_104299_(matrixStack);
         }
      }

      matrixStack.m_85837_((double)0.0F, (double)-0.25F, (double)0.0F);
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0F));
      matrixStack.m_85841_(0.625F, -0.625F, -0.625F);
      Minecraft.m_91087_().m_91290_().m_234586_().m_269530_(ctx.entity(), stack, ItemDisplayContext.HEAD, false, matrixStack, buffer, light);
      matrixStack.m_85849_();
   }
}
