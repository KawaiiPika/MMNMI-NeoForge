package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;

@Mixin({HumanoidArmorLayer.class})
public class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
   @Inject(
      method = {"renderArmorPiece"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;usesInnerModel(Lnet/minecraft/world/entity/EquipmentSlot;)Z",
   shift = Shift.AFTER
)},
      locals = LocalCapture.CAPTURE_FAILHARD,
      cancellable = true
   )
   private void mineminenomi$renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, T entity, EquipmentSlot slot, int packedLight, A originalModel, CallbackInfo ci, ItemStack itemstack, Item _item, ArmorItem armorItem, Model newModel) {
      HumanoidArmorLayer<T, M, A> layer = (HumanoidArmorLayer)this;
      Item var14 = itemstack.m_41720_();
      if (var14 instanceof IMultiChannelColorItem colorItem) {
         if (colorItem.canBeDyed()) {
            boolean isInner = slot == EquipmentSlot.LEGS;

            for(int i = 0; i < colorItem.getMaxLayers(); ++i) {
               int color = colorItem.getLayerColor(itemstack, i);
               float red = 1.0F;
               float green = 1.0F;
               float blue = 1.0F;
               if (color >= 0) {
                  red = (float)(color >> 16 & 255) / 255.0F;
                  green = (float)(color >> 8 & 255) / 255.0F;
                  blue = (float)(color & 255) / 255.0F;
               }

               this.renderMulticolorModel(poseStack, buffer, packedLight, armorItem, newModel, isInner, red, green, blue, layer.getArmorResource(entity, itemstack, slot, "" + i));
            }

            ResourceLocation mainTexture = layer.getArmorResource(entity, itemstack, slot, (String)null);
            boolean hasMainTexture = true;

            try {
               Minecraft.m_91087_().m_91098_().m_215593_(mainTexture);
            } catch (Exception var20) {
               hasMainTexture = false;
            }

            if (hasMainTexture) {
               this.renderMulticolorModel(poseStack, buffer, packedLight, armorItem, newModel, isInner, 1.0F, 1.0F, 1.0F, mainTexture);
            }

            if (itemstack.m_41790_()) {
               this.renderMulticolorGlint(poseStack, buffer, packedLight, newModel);
            }

            ci.cancel();
         }
      }

   }

   private void renderMulticolorModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ArmorItem item, Model model, boolean isInner, float red, float green, float blue, ResourceLocation armorResource) {
      VertexConsumer vertexconsumer = buffer.m_6299_(RenderType.m_110431_(armorResource));
      model.m_7695_(poseStack, vertexconsumer, packedLight, OverlayTexture.f_118083_, red, green, blue, 1.0F);
   }

   private void renderMulticolorGlint(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Model model) {
      model.m_7695_(poseStack, buffer.m_6299_(RenderType.m_110484_()), packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
