package xyz.pixelatedw.mineminenomi.morphs.ryubrachiosaurus;

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
import xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachiosaurusGuardPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class BrachiosaurusGuardMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(5.5F, 10.5F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(5.5F, 10.3F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/brachiosaurus_guard.png");

   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      float scale = 3.5F;
      poseStack.m_85841_(scale, scale, scale);
   }

   public Component getDisplayName() {
      return ((AbilityCore)BrachiosaurusGuardPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public float getEyeHeight(LivingEntity entity) {
      return 10.0F;
   }

   public float getShadowSize() {
      return 3.5F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraZoom(LivingEntity entity) {
      return (double)8.0F;
   }

   public boolean canMount() {
      return false;
   }

   public void positionRider(LivingEntity entity, Entity passenger) {
      double height = entity.m_20186_() + (double)(entity.m_6972_(entity.m_20089_()).f_20378_ * 0.46F);
      passenger.m_6034_(entity.m_20185_(), height, entity.m_20189_());
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
