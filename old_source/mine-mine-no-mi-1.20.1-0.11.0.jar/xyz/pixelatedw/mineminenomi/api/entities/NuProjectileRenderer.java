package xyz.pixelatedw.mineminenomi.api.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class NuProjectileRenderer<T extends NuProjectileEntity, M extends EntityModel<T>> extends EntityRenderer<T> {
   private float scaleX = 1.0F;
   private float scaleY = 1.0F;
   private float scaleZ = 1.0F;
   private float rotationX = 0.0F;
   private float rotationY = 0.0F;
   private float rotationZ = 0.0F;
   private Color colour;
   protected @Nullable M model;
   private ResourceLocation texture;
   private boolean useArmSkin = false;
   private boolean useLegSkin = false;
   private boolean isGlowing;
   private EntityRenderer<? super Entity> ownerRenderer;

   public NuProjectileRenderer(EntityRendererProvider.Context ctx, @Nullable M model) {
      super(ctx);
      this.model = model;
   }

   public void setUseArmSkin() {
      this.useArmSkin = true;
   }

   public void setUseLegSkin() {
      this.useLegSkin = true;
   }

   public void setGlowing() {
      this.isGlowing = true;
   }

   public void setTexture(ResourceLocation res) {
      this.texture = res;
   }

   public Color getColor() {
      return this.colour;
   }

   public void setColor(Color colour) {
      this.colour = colour;
   }

   public void setScale(float scaleX, float scaleY, float scaleZ) {
      this.scaleX = scaleX;
      this.scaleY = scaleY;
      this.scaleZ = scaleZ;
   }

   public void setScale(Vec3 scale) {
   }

   public void setScale(float scale) {
      this.setScale(scale, scale, scale);
   }

   public Vec3 getScale() {
      return new Vec3((double)this.scaleX, (double)this.scaleY, (double)this.scaleZ);
   }

   public void setRotation(float rotationX, float rotationY, float rotationZ) {
      this.rotationX = rotationX;
      this.rotationY = rotationY;
      this.rotationZ = rotationZ;
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.f_19797_ >= 2) {
         if (entity instanceof IFlexibleSizeProjectile) {
            IFlexibleSizeProjectile sizeableProjectile = (IFlexibleSizeProjectile)entity;
            this.setScale(sizeableProjectile.getSize());
         }

         if (Minecraft.m_91087_().m_91290_().m_114377_() && !entity.m_20145_() && !Minecraft.m_91087_().m_91299_()) {
            this.renderDebugBox(matrixStack, buffer.m_6299_(RenderType.m_110504_()), entity);
         }

         matrixStack.m_85836_();
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14179_(partialTicks, entity.f_19859_, entity.m_146908_()) + 180.0F + this.rotationX));
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_()) + this.rotationY));
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(this.rotationZ));
         matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
         matrixStack.m_85841_(this.scaleX, this.scaleY, this.scaleZ);
         float ageInTicks = (float)entity.f_19797_ + partialTicks;
         Color colour = this.getColor();
         float red = (float)colour.getRed() / 255.0F;
         float green = (float)colour.getGreen() / 255.0F;
         float blue = (float)colour.getBlue() / 255.0F;
         float alpha = (float)colour.getAlpha() / 255.0F;
         if (this.model != null) {
            this.model.m_6973_(entity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
            RenderType type = this.getRenderType(entity);
            VertexConsumer vertex = buffer.m_6299_(type);
            this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, red, green, blue, alpha);
            this.applyModelOverlay(entity, this.model, matrixStack, buffer, packedLight);
         } else if ((this.useArmSkin || this.useLegSkin) && entity.getOwner() != null) {
            if (this.ownerRenderer == null) {
               this.ownerRenderer = Minecraft.m_91087_().m_91290_().m_114382_(entity.getOwner());
            }

            if (this.useArmSkin) {
               matrixStack.m_85837_(0.3, (double)0.0F, (double)0.5F);
            } else if (this.useLegSkin) {
               matrixStack.m_85837_(0.1, (double)0.0F, (double)1.0F);
            }

            matrixStack.m_252781_(Axis.f_252495_.m_252977_(90.0F));
            if (this.ownerRenderer != null) {
               EntityRenderer vertex = this.ownerRenderer;
               if (vertex instanceof LivingEntityRenderer) {
                  LivingEntityRenderer livingRenderer = (LivingEntityRenderer)vertex;
                  List<ModelPart> limbs = null;
                  if (this.useArmSkin) {
                     limbs = RendererHelper.getArmPartsFrom(livingRenderer.m_7200_());
                  }

                  if (this.useLegSkin) {
                     limbs = RendererHelper.getLegPartsFrom(livingRenderer.m_7200_());
                  }

                  if (limbs == null || limbs.isEmpty()) {
                     return;
                  }

                  for(ModelPart limb : limbs) {
                     limb.m_233569_();
                     this.applySkinOverlay(entity, limb, matrixStack, buffer, packedLight, red, green, blue, alpha);
                     matrixStack.m_85836_();
                     RenderType skinType = this.getSkinRenderType(entity);
                     VertexConsumer skinVertex = buffer.m_6299_(skinType);
                     limb.m_104306_(matrixStack, skinVertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
                     matrixStack.m_85849_();
                  }
               }
            }
         }

         matrixStack.m_85849_();
         super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
      }
   }

   public void applyModelOverlay(T entity, M model, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.getOwner() != null) {
         boolean hardeningCheck = entity.isAffectedByHardening() && HakiHelper.hasHardeningActive(entity.getOwner(), false, false);
         boolean imbuingCheck = entity.isAffectedByImbuing() && HakiHelper.hasImbuingActive(entity.getOwner(), false, false);
         if (hardeningCheck || imbuingCheck) {
            matrixStack.m_85836_();
            VertexConsumer vertex = buffer.m_6299_(RenderType.m_110473_(ModResources.BUSOSHOKU_HAKI_ARM));
            model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.8F);
            matrixStack.m_85849_();
         }
      }

   }

   public void applySkinOverlay(T entity, ModelPart limb, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float red, float green, float blue, float alpha) {
      ResourceLocation texture = this.getTextureLocation(entity);
      if (texture != null) {
         matrixStack.m_85836_();
         VertexConsumer vertex = buffer.m_6299_(RenderType.m_110473_(texture));
         limb.m_104306_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, red, green, blue, alpha);
         matrixStack.m_85849_();
      }

      if (entity.getOwner() != null) {
         boolean hardeningCheck = entity.isAffectedByHardening() && HakiHelper.hasHardeningActive(entity.getOwner(), false, false);
         boolean imbuingCheck = entity.isAffectedByImbuing() && HakiHelper.hasImbuingActive(entity.getOwner(), false, false);
         if (hardeningCheck || imbuingCheck) {
            matrixStack.m_85836_();
            VertexConsumer vertex = buffer.m_6299_(RenderType.m_110473_(ModResources.BUSOSHOKU_HAKI_ARM));
            limb.m_104306_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.8F);
            matrixStack.m_85849_();
         }
      }

   }

   public ResourceLocation getTextureLocation(NuProjectileEntity entity) {
      return this.texture;
   }

   public @Nullable ResourceLocation getSkinTextureLocation(NuProjectileEntity entity) {
      if (entity.getOwner() != null) {
         LivingEntity var3 = entity.getOwner();
         if (var3 instanceof AbstractClientPlayer) {
            AbstractClientPlayer player = (AbstractClientPlayer)var3;
            return player.m_108560_();
         }
      }

      return DefaultPlayerSkin.m_118627_(entity.m_20148_());
   }

   public @Nullable RenderType getRenderType(NuProjectileEntity entity) {
      RenderType type = null;
      ResourceLocation texture = this.getTextureLocation(entity);
      if (texture == null) {
         type = this.isGlowing ? ModRenderTypes.ENERGY : ModRenderTypes.TRANSPARENT_COLOR;
      } else {
         type = RenderType.m_110473_(texture);
      }

      return type;
   }

   public @Nullable RenderType getSkinRenderType(NuProjectileEntity entity) {
      ResourceLocation texture = this.getSkinTextureLocation(entity);
      return RenderType.m_110473_(texture);
   }

   private void renderDebugBox(PoseStack matrixStack, VertexConsumer vertexBuilder, T entity) {
      AABB axisalignedbb = new AABB(-entity.getBlockCollisionBox().m_82362_() / (double)2.0F, -entity.getBlockCollisionBox().m_82376_() / (double)2.0F, -entity.getBlockCollisionBox().m_82385_() / (double)2.0F, entity.getBlockCollisionBox().m_82362_() / (double)2.0F, entity.getBlockCollisionBox().m_82376_() / (double)2.0F, entity.getBlockCollisionBox().m_82385_() / (double)2.0F);
      LevelRenderer.m_109646_(matrixStack, vertexBuilder, axisalignedbb, 1.0F, 0.0F, 0.0F, 1.0F);
   }

   public static class Factory<T extends NuProjectileEntity> implements EntityRendererProvider<T> {
      protected Supplier<? extends EntityModel<T>> rawModel;
      protected Function<EntityRendererProvider.Context, EntityModel<T>> model;
      protected float scaleX = 1.0F;
      protected float scaleY = 1.0F;
      protected float scaleZ = 1.0F;
      protected float rotationX = 0.0F;
      protected float rotationY = 0.0F;
      protected float rotationZ = 0.0F;
      protected Color colour = new Color(255, 255, 255, 255);
      protected ResourceLocation texture;
      protected boolean useArmSkin = false;
      protected boolean useLegSkin = false;
      protected boolean isGlowing = false;

      public Factory<T> setModel(Supplier<? extends EntityModel<T>> rawModel) {
         this.rawModel = rawModel;
         return this;
      }

      public Factory<T> setModel(Function<EntityRendererProvider.Context, EntityModel<T>> model) {
         this.model = model;
         return this;
      }

      public Factory<T> setUseArmSkin() {
         this.useArmSkin = true;
         return this;
      }

      public Factory<T> setUseLegSkin() {
         this.useLegSkin = true;
         return this;
      }

      public Factory<T> setTexture(ResourceLocation location) {
         this.texture = location;
         return this;
      }

      public Factory<T> setColor(float red, float green, float blue) {
         this.colour = new Color(red, green, blue, 1.0F);
         return this;
      }

      public Factory<T> setColor(float red, float green, float blue, float alpha) {
         this.colour = new Color(red, green, blue, alpha);
         return this;
      }

      public Factory<T> setColor(int hex) {
         boolean hasAlpha = hex > 16777215;
         this.colour = new Color(hex, hasAlpha);
         return this;
      }

      public Factory<T> setColor(String hex) {
         this.colour = WyHelper.hexToRGB(hex);
         return this;
      }

      public Factory<T> setAlpha(int alpha) {
         this.colour = new Color(this.colour.getRed(), this.colour.getGreen(), this.colour.getBlue(), alpha);
         return this;
      }

      public Factory<T> setScale(double scale) {
         this.scaleX = this.scaleY = this.scaleZ = (float)scale;
         return this;
      }

      public Factory<T> setScale(double scaleX, double scaleY, double scaleZ) {
         this.scaleX = (float)scaleX;
         this.scaleY = (float)scaleY;
         this.scaleZ = (float)scaleZ;
         return this;
      }

      public Factory<T> setRotation(float rotationX, float rotationY, float rotationZ) {
         this.rotationX = rotationX;
         this.rotationY = rotationY;
         this.rotationZ = rotationZ;
         return this;
      }

      public Factory<T> setGlowing() {
         this.isGlowing = true;
         return this;
      }

      public EntityRenderer<T> m_174009_(EntityRendererProvider.Context ctx) {
         EntityModel<T> model = null;
         if (this.rawModel != null) {
            model = (EntityModel)this.rawModel.get();
         } else if (this.model != null) {
            model = (EntityModel)this.model.apply(ctx);
         }

         NuProjectileRenderer<T, EntityModel<T>> renderer = new NuProjectileRenderer<T, EntityModel<T>>(ctx, model);
         renderer.setTexture(this.texture);
         renderer.setScale(this.scaleX, this.scaleY, this.scaleZ);
         renderer.setRotation(this.rotationX, this.rotationY, this.rotationZ);
         renderer.setColor(this.colour);
         if (this.useArmSkin) {
            renderer.setUseArmSkin();
         }

         if (this.useLegSkin) {
            renderer.setUseLegSkin();
         }

         if (this.isGlowing) {
            renderer.setGlowing();
         }

         return renderer;
      }
   }
}
