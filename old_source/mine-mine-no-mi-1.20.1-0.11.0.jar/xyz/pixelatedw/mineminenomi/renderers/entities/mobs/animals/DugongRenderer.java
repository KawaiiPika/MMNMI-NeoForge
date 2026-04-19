package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.EyesLayer;

public class DugongRenderer<T extends AbstractDugongEntity, M extends DugongModel<T>> extends MobRenderer<T, M> {
   private static final RenderType ANGRY_EYES = ModRenderTypes.getEyesLayerRenderType(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/dugong_angry_eyes.png"));
   private static final RenderType SLEEPING_EYES = ModRenderTypes.getEyesLayerRenderType(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/dugong_sleeping_eyes.png"));
   private final ResourceLocation texture;

   public DugongRenderer(EntityRendererProvider.Context ctx, Supplier<M> model, ResourceLocation texture) {
      super(ctx, (DugongModel)model.get(), 0.4F);
      this.texture = texture;
      this.m_115326_(new ItemInHandLayer(this, ctx.m_234598_()));
      this.m_115326_(new EyesLayer(this, ANGRY_EYES, AbstractDugongEntity::isEnraged));
      this.m_115326_(new EyesLayer(this, SLEEPING_EYES, AbstractDugongEntity::isResting));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(T pEntity) {
      return this.texture;
   }

   public static class Factory implements EntityRendererProvider<AbstractDugongEntity> {
      protected Supplier<DugongModel<AbstractDugongEntity>> model;
      private final ResourceLocation texture;

      public Factory(EntityRendererProvider.Context ctx, ResourceLocation texture) {
         this.model = () -> new DugongModel(ctx.m_174023_(DugongModel.LAYER_LOCATION));
         this.texture = texture;
      }

      public EntityRenderer<AbstractDugongEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new DugongRenderer(ctx, this.model, this.texture);
      }
   }
}
