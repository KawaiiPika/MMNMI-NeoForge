package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.entities.IRandomizedTexture;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.CloneEntity;
import xyz.pixelatedw.mineminenomi.init.ModResources;

@OnlyIn(Dist.CLIENT)
public class CloneHumanoidRenderer<T extends CloneEntity, M extends HumanoidModel<T>> extends HumanoidMobRenderer<T, M> {
   protected ResourceLocation texture;
   protected boolean useSimpleModel;
   private EntityRendererProvider.Context ctx;

   public CloneHumanoidRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, (HumanoidModel)null, 0.5F);
      this.ctx = ctx;
   }

   public void init(T entity) {
      if (this.f_115290_ == null) {
         boolean isSlim = false;
         if (entity.getOwner() != null) {
            LivingEntity var4 = entity.getOwner();
            if (var4 instanceof AbstractClientPlayer) {
               AbstractClientPlayer player = (AbstractClientPlayer)var4;
               if (player.m_108564_().equals("slim")) {
                  isSlim = true;
               }
            }
         }

         this.f_115290_ = (EntityModel)(this.useSimpleModel ? new HumanoidModel(this.ctx.m_174023_(ModelLayers.f_171223_)) : new PlayerModel(this.ctx.m_174023_(isSlim ? ModelLayers.f_171166_ : ModelLayers.f_171162_), isSlim));
         if (this.useSimpleModel) {
            this.m_115326_(new HumanoidArmorLayer(this, new HumanoidArmorModel(this.ctx.m_174023_(ModelLayers.f_171226_)), new HumanoidArmorModel(this.ctx.m_174023_(ModelLayers.f_171227_)), this.ctx.m_266367_()));
         } else {
            this.m_115326_(new HumanoidArmorLayer(this, new HumanoidArmorModel(this.ctx.m_174023_(isSlim ? ModelLayers.f_171167_ : ModelLayers.f_171164_)), new HumanoidArmorModel(this.ctx.m_174023_(isSlim ? ModelLayers.f_171168_ : ModelLayers.f_171165_)), this.ctx.m_266367_()));
         }
      }

   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      this.init(entity);
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public void setTexture(ResourceLocation texture) {
      this.texture = texture;
   }

   public void setSimpleModel() {
      this.useSimpleModel = true;
   }

   public ResourceLocation getTextureLocation(T entity) {
      if (entity.isUsingOwnerTexture()) {
         LivingEntity var4 = entity.getOwner();
         if (var4 instanceof AbstractClientPlayer) {
            AbstractClientPlayer player = (AbstractClientPlayer)var4;
            ResourceLocation texture = player.m_108560_();
            if (texture != null) {
               return texture;
            }

            return ModResources.NULL_ENTITY_TEXTURE;
         }
      }

      if (this.texture != null) {
         return this.texture;
      } else if (entity instanceof IRandomizedTexture) {
         IRandomizedTexture randTexture = (IRandomizedTexture)entity;
         return randTexture.getTexture();
      } else {
         return ModResources.NULL_ENTITY_TEXTURE;
      }
   }

   public static class Factory implements EntityRendererProvider<CloneEntity> {
      private ResourceLocation texture;
      private boolean useSimpleModel;

      public Factory setTexture(ResourceLocation texture) {
         this.texture = texture;
         return this;
      }

      public Factory setSimpleModel() {
         this.useSimpleModel = true;
         return this;
      }

      public EntityRenderer<CloneEntity> m_174009_(EntityRendererProvider.Context ctx) {
         CloneHumanoidRenderer<CloneEntity, ?> renderer = new CloneHumanoidRenderer(ctx);
         renderer.setTexture(this.texture);
         if (this.useSimpleModel) {
            renderer.setSimpleModel();
         }

         return renderer;
      }
   }
}
