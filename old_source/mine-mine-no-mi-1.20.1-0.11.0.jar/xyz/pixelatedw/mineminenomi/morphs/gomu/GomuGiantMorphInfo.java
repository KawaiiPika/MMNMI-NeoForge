package xyz.pixelatedw.mineminenomi.morphs.gomu;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.deka.DekaDekaAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GomuGiantMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(4.0F, 8.5F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(4.0F, 7.6F);

   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderr, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(2.1F, 2.1F, 2.1F);
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      if (entity instanceof AbstractClientPlayer player) {
         return player.m_108560_();
      } else {
         return null;
      }
   }

   public Component getDisplayName() {
      return ((AbilityCore)DekaDekaAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 8.45F;
   }

   public float getShadowSize() {
      return 1.25F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraZoom(LivingEntity entity) {
      return (double)8.0F;
   }

   public boolean canMount() {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraHeight(LivingEntity entity) {
      boolean isFirstPerson = Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.FIRST_PERSON;
      boolean shouldSit = entity.m_20159_() && entity.m_20202_() != null && entity.m_20202_().shouldRiderSit();
      return isFirstPerson && shouldSit ? (double)0.5F : (double)0.0F;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
