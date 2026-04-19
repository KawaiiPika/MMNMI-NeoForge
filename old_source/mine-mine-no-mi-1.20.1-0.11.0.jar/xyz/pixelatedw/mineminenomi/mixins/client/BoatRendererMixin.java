package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.IKairosekiCoating;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.KairosekiCoatingCapability;
import xyz.pixelatedw.mineminenomi.init.ModResources;

@Mixin({BoatRenderer.class})
public class BoatRendererMixin {
   @Inject(
      method = {"render"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/ListModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V",
   shift = Shift.AFTER
)}
   )
   public void mineminenomi$render(Boat entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
      BoatRenderer renderer = (BoatRenderer)this;
      IKairosekiCoating coatingData = (IKairosekiCoating)KairosekiCoatingCapability.get(entity).orElse((Object)null);
      int coatingLevel = coatingData.getCoatingLevel();
      if (coatingLevel > 0) {
         Pair<ResourceLocation, ListModel<Boat>> pair = renderer.getModelWithLocation(entity);
         ListModel<Boat> model = (ListModel)pair.getSecond();
         float coatingVisibility = (float)coatingLevel / 5.0F * 0.8F;
         VertexConsumer vertexBuilder = buffer.m_6299_(RenderType.m_110473_(ModResources.KAIROSEKI_BLOCK));
         model.m_7695_(matrixStack, vertexBuilder, packedLight, OverlayTexture.f_118083_, 0.8F, 0.8F, 0.8F, coatingVisibility);
      }

   }
}
