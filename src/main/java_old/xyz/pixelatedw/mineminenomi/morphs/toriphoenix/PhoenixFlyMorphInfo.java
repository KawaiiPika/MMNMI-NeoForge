package xyz.pixelatedw.mineminenomi.morphs.toriphoenix;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import javax.annotation.Nullable;
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
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixFlyPointAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.PhoenixFlyFlamesLayer;

public class PhoenixFlyMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(1.7F, 1.7F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/phoenix_fly.png");
   private static final ResourceLocation CHICKEN = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/phoenix_chicken.png");

   @Nullable
   public ResourceLocation getTexture(LivingEntity entity) {
      return WyHelper.isAprilFirst() ? CHICKEN : TEXTURE;
   }

   @OnlyIn(Dist.CLIENT)
   public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      renderer.m_115326_(new PhoenixFlyFlamesLayer(ctx, renderer));
   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      if (WyHelper.isAprilFirst()) {
         float scale = 1.8F;
         poseStack.m_85841_(scale, scale, scale);
         poseStack.m_252880_(0.0F, 0.35F, 0.0F);
      } else {
         float scale = 1.7F;
         poseStack.m_85841_(scale, scale, scale);
         poseStack.m_85837_((double)0.0F, (double)-0.25F, (double)0.0F);
      }
   }

   public Component getDisplayName() {
      return ((AbilityCore)PhoenixFlyPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 0.8F;
   }

   public float getShadowSize() {
      return 1.0F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraZoom(LivingEntity entity) {
      return (double)8.0F;
   }

   public boolean canMount() {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasEqualDepthTest() {
      return true;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).build();
   }
}
