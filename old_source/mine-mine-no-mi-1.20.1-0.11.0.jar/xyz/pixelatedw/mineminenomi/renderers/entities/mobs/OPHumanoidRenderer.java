package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModResources;

@OnlyIn(Dist.CLIENT)
public class OPHumanoidRenderer<T extends Mob, M extends HumanoidModel<T>> extends HumanoidMobRenderer<T, M> {
   protected float[] scale;
   protected ResourceLocation texture;

   public OPHumanoidRenderer(EntityRendererProvider.Context ctx, Supplier<M> model, float[] scale) {
      super(ctx, (HumanoidModel)model.get(), 0.5F);
      this.scale = scale;
      this.m_115326_(new HumanoidArmorLayer(this, (HumanoidModel)model.get(), (HumanoidModel)model.get(), ctx.m_266367_()));
   }

   public void scale(T entity, PoseStack stack, float partialTickTime) {
      stack.m_85841_(this.scale[0], this.scale[1], this.scale[2]);
   }

   public void setTexture(ResourceLocation texture) {
      this.texture = texture;
   }

   public ResourceLocation getTextureLocation(T entity) {
      if (this.texture != null) {
         return this.texture;
      } else {
         if (entity instanceof OPEntity) {
            OPEntity opEntity = (OPEntity)entity;
            ResourceLocation texture = opEntity.getCurrentTexture();
            if (texture != null) {
               return texture;
            }
         }

         return ModResources.NULL_ENTITY_TEXTURE;
      }
   }

   public static class Factory<M extends Mob> implements EntityRendererProvider<M> {
      private EntityRendererProvider.Context ctx;
      protected Supplier<HumanoidModel<M>> model;
      private float[] scale;
      private ResourceLocation texture;

      public Factory(EntityRendererProvider.Context ctx) {
         this(ctx, 1.0F);
      }

      public Factory(EntityRendererProvider.Context ctx, float scale) {
         this(ctx, scale, scale, scale);
      }

      public Factory(EntityRendererProvider.Context ctx, float scaleX, float scaleY, float scaleZ) {
         this.scale = new float[]{1.0F, 1.0F, 1.0F};
         this.ctx = ctx;
         this.model = () -> new HumanoidModel(ctx.m_174023_(ModelLayers.f_171223_));
         this.scale = new float[]{scaleX, scaleY, scaleZ};
      }

      public Factory<M> setModelLayer(ModelLayerLocation layer) {
         this.model = () -> new HumanoidModel(this.ctx.m_174023_(layer));
         return this;
      }

      public Factory<M> setTexture(ResourceLocation texture) {
         this.texture = texture;
         return this;
      }

      public EntityRenderer<M> m_174009_(EntityRendererProvider.Context ctx) {
         OPHumanoidRenderer<M, ?> renderer = new OPHumanoidRenderer(ctx, this.model, this.scale);
         renderer.setTexture(this.texture);
         return renderer;
      }
   }
}
