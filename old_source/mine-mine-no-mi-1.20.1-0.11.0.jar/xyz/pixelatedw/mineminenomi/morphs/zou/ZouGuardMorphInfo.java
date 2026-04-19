package xyz.pixelatedw.mineminenomi.morphs.zou;

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
import xyz.pixelatedw.mineminenomi.abilities.zou.ZouGuardPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class ZouGuardMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(1.5F, 3.0F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(1.5F, 2.9F);
   private static final EntityDimensions FLYING_SIZE = EntityDimensions.m_20395_(1.5F, 3.0F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/zou_guard.png");

   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      poseStack.m_85841_(1.8F, 1.8F, 1.8F);
   }

   public Component getDisplayName() {
      return ((AbilityCore)ZouGuardPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 2.9F;
   }

   public float getShadowSize() {
      return 1.5F;
   }

   public boolean canMount() {
      return false;
   }

   public void positionRider(LivingEntity entity, Entity passenger) {
      double height = entity.m_20186_() + entity.m_6048_() + 0.2;
      passenger.m_6034_(entity.m_20185_(), height, entity.m_20189_());
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).put(Pose.FALL_FLYING, FLYING_SIZE).build();
   }
}
