package xyz.pixelatedw.mineminenomi.morphs.goro;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.goro.VoltAmaruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class VoltAmaruMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(2.4F, 6.5F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(2.4F, 6.2F);

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      poseStack.m_85841_(2.5F, 2.5F, 2.5F);
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      if (entity instanceof AbstractClientPlayer player) {
         return player.m_108560_();
      } else {
         return null;
      }
   }

   public Component getDisplayName() {
      return ((AbilityCore)VoltAmaruAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 5.5F;
   }

   public float getShadowSize() {
      return 1.9F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraZoom(LivingEntity entity) {
      return (double)10.0F;
   }

   public boolean canMount() {
      return false;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
