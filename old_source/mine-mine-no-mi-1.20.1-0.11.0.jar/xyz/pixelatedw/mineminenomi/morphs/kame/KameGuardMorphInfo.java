package xyz.pixelatedw.mineminenomi.morphs.kame;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.kame.KameGuardPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class KameGuardMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(1.7F, 0.7F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(1.7F, 0.6F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/kame_guard.png");

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      poseStack.m_85841_(2.0F, 2.0F, 2.0F);
   }

   public float getEyeHeight(LivingEntity entity) {
      return 0.3F;
   }

   public float getShadowSize() {
      return 1.1F;
   }

   public void positionRider(LivingEntity entity, Entity passenger) {
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public Component getDisplayName() {
      return ((AbilityCore)KameGuardPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
