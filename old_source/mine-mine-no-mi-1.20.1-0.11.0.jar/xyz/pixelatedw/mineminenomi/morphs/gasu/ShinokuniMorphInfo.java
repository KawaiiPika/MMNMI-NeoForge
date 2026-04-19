package xyz.pixelatedw.mineminenomi.morphs.gasu;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.gasu.ShinokuniAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.ShinokuniFaceLayer;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class ShinokuniMorphInfo extends MorphInfo {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/shinokuni.png");
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(2.8F, 6.0F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(2.8F, 5.9F);

   @OnlyIn(Dist.CLIENT)
   public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      if (renderer instanceof MorphRenderer morphRenderer) {
         renderer.m_115326_(new ShinokuniFaceLayer(morphRenderer));
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderr, PoseStack poseStack, float partialTickTime) {
      poseStack.m_85841_(3.0F, 3.0F, 3.0F);
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public Component getDisplayName() {
      return ((AbilityCore)ShinokuniAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 5.7F;
   }

   public float getShadowSize() {
      return 1.2F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraZoom(LivingEntity entity) {
      return (double)7.0F;
   }

   public boolean canMount() {
      return false;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
