package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;

@Mixin({ItemRenderer.class})
public abstract class ItemRendererMixin {
   @Accessor
   public abstract ItemColors getItemColors();

   @Inject(
      method = {"renderQuadList"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderQuadList(PoseStack matrixStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack itemStack, int lightmap, int overlay, CallbackInfo info) {
      if (!itemStack.m_41619_()) {
         if (itemStack.m_41782_() && itemStack.m_41783_().m_128471_("imbuingHakiActive")) {
            if (ItemsHelper.isImbuable(itemStack) && !ItemsHelper.hasImbuingOverride(itemStack)) {
               PoseStack.Pose entry = matrixStack.m_85850_();

               for(BakedQuad bakedquad : quads) {
                  buffer.putBulkData(entry, bakedquad, 0.4F, 0.0F, 0.4F, 1.0F, lightmap, overlay, true);
               }

               info.cancel();
            }
         }
      }
   }
}
