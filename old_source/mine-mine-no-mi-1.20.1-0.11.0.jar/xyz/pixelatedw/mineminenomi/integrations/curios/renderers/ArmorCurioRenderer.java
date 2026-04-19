package xyz.pixelatedw.mineminenomi.integrations.curios.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class ArmorCurioRenderer implements ICurioRenderer {
   public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext ctx, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      EquipmentSlot slot = LivingEntity.m_147233_(stack);
      HumanoidModel<LivingEntity> model = (HumanoidModel)ModArmors.Client.ARMOR_MODELS.get(stack.m_41720_());
      if (model == null) {
         boolean isSlim = ((LocalPlayer)ctx.entity()).m_108564_() == "slim";
         HumanoidModel<LivingEntity> baseModel = new HumanoidArmorModel(Minecraft.m_91087_().m_167973_().m_171103_(isSlim ? ModelLayers.f_171167_ : ModelLayers.f_171164_));
         model = (HumanoidModel)ForgeHooksClient.getArmorModel(ctx.entity(), stack, slot, baseModel);
      }

      model.m_6973_(ctx.entity(), limbSwing, limbSwingAmount, partialTicks, netHeadYaw, headPitch);
      model.m_6839_(ctx.entity(), limbSwing, limbSwingAmount, partialTicks);
      RendererHelper.setPartVisibility(model, slot);
      ICurioRenderer.followBodyRotations(ctx.entity(), new HumanoidModel[]{model});
      Item i = stack.m_41720_();
      if (i instanceof IMultiChannelColorItem colorItem) {
         if (colorItem.canBeDyed()) {
            for(int i = 0; i < colorItem.getMaxLayers(); ++i) {
               int color = colorItem.getLayerColor(stack, i);
               float red = 1.0F;
               float green = 1.0F;
               float blue = 1.0F;
               if (color >= 0) {
                  red = (float)(color >> 16 & 255) / 255.0F;
                  green = (float)(color >> 8 & 255) / 255.0F;
                  blue = (float)(color & 255) / 255.0F;
               }

               this.renderLayer(stack, model, matrixStack, buffer, ctx.entity(), red, green, blue, light, "" + i);
            }

            ResourceLocation mainTexture = ResourceLocation.parse(stack.m_41720_().getArmorTexture(stack, ctx.entity(), stack.getEquipmentSlot(), (String)null));
            boolean hasMainTexture = true;

            try {
               Minecraft.m_91087_().m_91098_().m_215593_(mainTexture);
            } catch (Exception var22) {
               hasMainTexture = false;
            }

            if (hasMainTexture) {
               this.renderLayer(stack, model, matrixStack, buffer, ctx.entity(), 1.0F, 1.0F, 1.0F, light, (String)null);
            }

            return;
         }
      }

      i = stack.m_41720_();
      if (i instanceof DyeableLeatherItem colorItem) {
         int color = colorItem.m_41121_(stack);
         float red = (float)(color >> 16 & 255) / 255.0F;
         float green = (float)(color >> 8 & 255) / 255.0F;
         float blue = (float)(color & 255) / 255.0F;
         this.renderLayer(stack, model, matrixStack, buffer, ctx.entity(), red, green, blue, light, (String)null);
         this.renderLayer(stack, model, matrixStack, buffer, ctx.entity(), 1.0F, 1.0F, 1.0F, light, "overlay");
      } else {
         this.renderLayer(stack, model, matrixStack, buffer, ctx.entity(), 1.0F, 1.0F, 1.0F, light, (String)null);
      }

   }

   private void renderLayer(ItemStack stack, HumanoidModel<LivingEntity> model, PoseStack matrixStack, MultiBufferSource buffer, LivingEntity entity, float red, float green, float blue, int light, @Nullable String layer) {
      boolean hasGlint = stack.m_41790_();
      ResourceLocation texture = ResourceLocation.parse(stack.m_41720_().getArmorTexture(stack, entity, stack.getEquipmentSlot(), layer));
      RenderType renderType = model.m_103119_(texture);
      VertexConsumer vertexBuilder = ItemRenderer.m_115184_(buffer, renderType, false, hasGlint);
      model.m_7695_(matrixStack, vertexBuilder, light, OverlayTexture.f_118083_, red, green, blue, 1.0F);
   }
}
