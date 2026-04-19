package xyz.pixelatedw.mineminenomi.api.morph;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class RacialRenderFeature {
   private final HumanoidModel<? extends LivingEntity> model;
   private final ResourceLocation texture;
   private ILayerRenderExtension<? extends LivingEntity> extension;

   public RacialRenderFeature(HumanoidModel<? extends LivingEntity> model, ResourceLocation texture) {
      this(model, texture, (ILayerRenderExtension)null);
   }

   public RacialRenderFeature(HumanoidModel<? extends LivingEntity> model, ResourceLocation texture, ILayerRenderExtension<? extends LivingEntity> extension) {
      this.model = model;
      this.texture = texture;
      this.extension = extension;
   }

   public <T extends LivingEntity> HumanoidModel<T> getModel() {
      return this.model;
   }

   public ResourceLocation getTexture() {
      return this.texture;
   }

   @Nullable
   public <T extends LivingEntity> ILayerRenderExtension<T> getExtension() {
      return this.extension;
   }

   @FunctionalInterface
   public interface ILayerRenderExtension<T extends LivingEntity> {
      boolean render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10, RenderLayerParent<T, ? extends EntityModel<T>> var11);
   }
}
