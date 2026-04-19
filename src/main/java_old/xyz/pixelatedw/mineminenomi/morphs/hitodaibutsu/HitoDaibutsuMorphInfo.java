package xyz.pixelatedw.mineminenomi.morphs.hitodaibutsu;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.hitodaibutsu.HitoDaibutsuPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.renderers.layers.GlowingLayer;

public class HitoDaibutsuMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(6.2F, 13.0F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(6.2F, 12.8F);

   public @Nullable ResourceLocation getTexture(LivingEntity entity) {
      if (entity instanceof AbstractClientPlayer player) {
         return player.m_108560_();
      } else {
         return null;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      renderer.m_115326_(new GlowingLayer(renderer, 1.25F, 16776960));
   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      poseStack.m_85841_(5.25F, 5.25F, 5.25F);
   }

   public Component getDisplayName() {
      return ((AbilityCore)HitoDaibutsuPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 12.8F;
   }

   public float getShadowSize() {
      return 3.5F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraZoom(LivingEntity entity) {
      return (double)12.0F;
   }

   public boolean canMount() {
      return false;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
