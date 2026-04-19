package xyz.pixelatedw.mineminenomi.api.morph;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;

public abstract class MorphInfo {
   private ResourceLocation key;

   public boolean isActive(LivingEntity entity) {
      boolean check = (Boolean)DevilFruitCapability.get(entity).map((props) -> {
         if (!this.isPartial() && props.getCurrentMorph().isPresent() && ((MorphInfo)props.getCurrentMorph().get()).equals(this)) {
            return true;
         } else {
            return this.isPartial() && props.hasMorphInQueue(this) ? true : false;
         }
      }).orElse(false);
      return check;
   }

   @OnlyIn(Dist.CLIENT)
   public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
   }

   public abstract Component getDisplayName();

   public @Nullable ResourceLocation getTexture(LivingEntity entity) {
      return null;
   }

   public boolean isPartial() {
      return false;
   }

   public boolean canRender(LivingEntity entity) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
   }

   @OnlyIn(Dist.CLIENT)
   public void postRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
   }

   @OnlyIn(Dist.CLIENT)
   public void preFirstPersonRenderCallback(LivingEntity entity, PoseStack matrixStack) {
   }

   @OnlyIn(Dist.CLIENT)
   public boolean shouldRenderLayer(RenderLayer<?, ?> layer, LivingEntity entity, PoseStack matrixStack, float partialTickTime) {
      return true;
   }

   public @Nullable Map<Pose, EntityDimensions> getSizes() {
      return null;
   }

   public EntityDimensions getSize(LivingEntity entity, Pose pose) {
      Map<Pose, EntityDimensions> sizes = this.getSizes();
      return sizes != null ? (EntityDimensions)sizes.get(pose) : entity.m_6972_(pose);
   }

   public float getEyeHeight(LivingEntity entity) {
      return -1.0F;
   }

   public float getShadowSize() {
      return -1.0F;
   }

   public boolean canMount() {
      return true;
   }

   public void positionRider(LivingEntity entity, Entity passenger) {
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasCulling() {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasEqualDepthTest() {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraZoom(LivingEntity entity) {
      return (double)0.0F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraHeight(LivingEntity entity) {
      return (double)0.0F;
   }

   public @Nullable ResourceLocation getKey() {
      if (this.key == null) {
         this.key = ((IForgeRegistry)WyRegistry.MORPHS.get()).getKey(this);
      }

      return this.key;
   }

   public void updateMorphSize(LivingEntity entity) {
      MinecraftForge.EVENT_BUS.post(new EntityEvent.Size(entity, entity.m_20089_(), entity.m_6972_(entity.m_20089_()), entity.m_20206_()));
      entity.m_6210_();
      entity.m_20153_();
   }
}
