package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.function.Supplier;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WanderingDugongEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.WanderingDugongScarsLayer;

public class WanderingDugongRenderer<T extends WanderingDugongEntity, M extends DugongModel<T>> extends MobRenderer<T, M> {
   private static final ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/kung_fu_dugong.png");

   public WanderingDugongRenderer(EntityRendererProvider.Context ctx, Supplier<M> model) {
      super(ctx, (DugongModel)model.get(), 0.4F);
      this.m_115326_(new ItemInHandLayer(this, ctx.m_234598_()));
      this.m_115326_(new WanderingDugongScarsLayer(this));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   protected void setupRotations(T entity, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
      super.m_7523_(entity, matrixStack, ageInTicks, rotationYaw, partialTicks);
      if (entity.isTraining()) {
         switch (entity.getTrainingMode()) {
            case PUSHUPS:
               matrixStack.m_85837_((double)0.0F, (double)0.25F, (double)0.5F);
               matrixStack.m_252781_(Axis.f_252529_.m_252977_(-75.0F));
            case SHADOW_BOXING:
         }
      }

   }

   public ResourceLocation getTextureLocation(T entity) {
      return BASE_TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<WanderingDugongEntity> {
      protected Supplier<DugongModel<WanderingDugongEntity>> model;

      public Factory(EntityRendererProvider.Context ctx) {
         this.model = () -> new DugongModel(ctx.m_174023_(DugongModel.LAYER_LOCATION));
      }

      public EntityRenderer<WanderingDugongEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new WanderingDugongRenderer(ctx, this.model);
      }
   }
}
