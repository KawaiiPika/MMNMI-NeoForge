package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.models.morphs.PhoenixFlyModel;

public class PhoenixFlyFlamesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final int MAX_FRAMES = 8;
   private static final float FRAME_TIME_MS = 1000.0F;
   private static final ResourceLocation[] TEXTURES = generateTextureArray();

   private static ResourceLocation[] generateTextureArray() {
      ResourceLocation[] arr = new ResourceLocation[8];

      for(int i = 0; i < 8; ++i) {
         arr[i] = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/phoenix_flames/fly_" + i + ".png");
      }

      return arr;
   }

   public PhoenixFlyFlamesLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      EntityModel var12 = this.m_117386_();
      if (var12 instanceof PhoenixFlyModel flyModel) {
         VertexConsumer vertex = buffer.m_6299_(RenderType.m_110473_(this.getFrameTexture()));
         flyModel.renderFlames(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.9F);
      }

   }

   private ResourceLocation getFrameTexture() {
      int frame = Math.round((float)Util.m_137550_() % 1000.0F / (1000.0F / (float)TEXTURES.length));
      return frame >= 8 ? TEXTURES[0] : TEXTURES[frame];
   }
}
