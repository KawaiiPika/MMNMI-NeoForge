package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractGorillaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.GorillaModel;

public class GorillaRenderer<T extends AbstractGorillaEntity, M extends GorillaModel<T>> extends MobRenderer<T, M> {
   public static final ResourceLocation BLUGORI_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/blugori.png");
   public static final ResourceLocation BLAGORI_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/blagori.png");
   private ResourceLocation texture;

   public GorillaRenderer(EntityRendererProvider.Context ctx, Supplier<M> model, ResourceLocation texture) {
      super(ctx, (GorillaModel)model.get(), 0.8F);
      this.texture = texture;
      this.m_115326_(new ItemInHandLayer(this, ctx.m_234598_()));
   }

   public void scale(T entity, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(entity.getSize(), entity.getSize(), entity.getSize());
      this.f_114477_ = entity.getSize() / 1.9F;
   }

   public ResourceLocation getTextureLocation(T entity) {
      return this.texture;
   }

   public static class Factory implements EntityRendererProvider<AbstractGorillaEntity> {
      protected Supplier<GorillaModel<AbstractGorillaEntity>> model;
      private ResourceLocation texture;

      public Factory(EntityRendererProvider.Context ctx, ResourceLocation texture) {
         this.model = () -> new GorillaModel(ctx.m_174023_(GorillaModel.LAYER_LOCATION));
         this.texture = texture;
      }

      public EntityRenderer<AbstractGorillaEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new GorillaRenderer(ctx, this.model, this.texture);
      }
   }
}
