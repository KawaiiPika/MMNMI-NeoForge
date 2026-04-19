package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.HumandrillEntity;
import xyz.pixelatedw.mineminenomi.models.armors.HumandrillArmorModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.HumandrillModel;

public class HumandrillRenderer<T extends HumandrillEntity, M extends HumandrillModel<T>> extends MobRenderer<T, M> {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/humandrill.png");

   public HumandrillRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new HumandrillModel(ctx.m_174023_(HumandrillModel.LAYER_LOCATION)), 0.8F);
      this.m_115326_(new ItemInHandLayer(this, ctx.m_234598_()));
      this.m_115326_(new HumanoidArmorLayer(this, new HumandrillArmorModel(ctx.m_174023_(HumandrillArmorModel.LAYER_LOCATION)), new HumandrillArmorModel(ctx.m_174023_(HumandrillArmorModel.LAYER_LOCATION)), ctx.m_266367_()));
   }

   public void scale(T entity, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(entity.getSize(), entity.getSize(), entity.getSize());
      this.f_114477_ = entity.getSize() / 1.2F;
   }

   public ResourceLocation getTextureLocation(T pEntity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<HumandrillEntity> {
      public EntityRenderer<HumandrillEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new HumandrillRenderer(ctx);
      }
   }
}
