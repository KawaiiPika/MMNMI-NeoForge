package xyz.pixelatedw.mineminenomi.morphs.kage;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.kage.ShadowsAsgardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;

public class ShadowsAsgardMorphInfo extends MorphInfo {
   private int shadows;

   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      this.shadows = (Integer)AbilityCapability.get(entity).map((props) -> (ShadowsAsgardAbility)props.getEquippedAbility((AbilityCore)ShadowsAsgardAbility.INSTANCE.get())).map((abl) -> abl.getShadows()).orElse(0);
      float size = 1.0F + (float)this.shadows * 5.0F / 180.0F;
      poseStack.m_85841_(size, size, size);
   }

   public Component getDisplayName() {
      return ((AbilityCore)ShadowsAsgardAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 1.8F + (float)this.shadows * 6.7F / 180.0F;
   }

   public float getShadowSize() {
      return 0.5F + (float)this.shadows * 6.0F / 180.0F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraHeight(LivingEntity entity) {
      boolean isFirstPerson = Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.FIRST_PERSON;
      boolean shouldSit = entity.m_20159_() && entity.m_20202_() != null && entity.m_20202_().shouldRiderSit();
      return isFirstPerson && shouldSit ? (double)0.5F : (double)0.0F;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, EntityDimensions.m_20395_(0.6F + (float)this.shadows / 30.0F, 1.65F + (float)this.shadows / 15.0F)).put(Pose.CROUCHING, EntityDimensions.m_20395_(0.6F + (float)this.shadows / 30.0F, 1.45F + (float)this.shadows / 15.0F)).build();
   }
}
