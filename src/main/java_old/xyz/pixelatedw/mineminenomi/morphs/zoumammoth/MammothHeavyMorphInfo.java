package xyz.pixelatedw.mineminenomi.morphs.zoumammoth;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.zoumammoth.MammothHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class MammothHeavyMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(3.0F, 4.5F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(3.0F, 4.5F);

   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      for(ModelPart part : RendererHelper.getLegPartsFrom(renderer.m_7200_(), HumanoidArm.RIGHT)) {
         part.f_104207_ = false;
      }

      for(ModelPart part : RendererHelper.getLegPartsFrom(renderer.m_7200_(), HumanoidArm.LEFT)) {
         part.f_104207_ = false;
      }

      float scale = 2.0F;
      poseStack.m_85841_(scale, scale, scale);
   }

   @OnlyIn(Dist.CLIENT)
   public void postRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      EntityModel var6 = renderer.m_7200_();
      if (var6 instanceof HumanoidModel humanoidModel) {
         humanoidModel.f_102808_.f_104202_ = -18.0F;
         humanoidModel.f_102808_.f_104201_ = -8.0F;
         humanoidModel.f_102810_.f_104202_ = -17.0F;
         humanoidModel.f_102810_.f_104201_ = -9.0F;
         humanoidModel.f_102810_.f_104203_ = 0.25F;
         humanoidModel.f_102810_.f_233553_ = 1.74F;
         humanoidModel.f_102810_.f_233555_ = 2.0F;
         ModelPart var10000 = humanoidModel.f_102811_;
         var10000.f_104203_ /= 5.0F;
         humanoidModel.f_102811_.f_104200_ = -8.0F;
         humanoidModel.f_102811_.f_104201_ = -6.5F;
         humanoidModel.f_102811_.f_104202_ = -17.5F;
         humanoidModel.f_102811_.f_233554_ = 1.2F;
         var10000 = humanoidModel.f_102812_;
         var10000.f_104203_ /= 5.0F;
         humanoidModel.f_102812_.f_104200_ = 8.0F;
         humanoidModel.f_102812_.f_104201_ = -6.5F;
         humanoidModel.f_102812_.f_104202_ = -17.5F;
         humanoidModel.f_102812_.f_233554_ = 1.2F;
         if (humanoidModel instanceof PlayerModel playerModel) {
            playerModel.f_103374_.m_104315_(playerModel.f_102812_);
            playerModel.f_103375_.m_104315_(playerModel.f_102811_);
         }
      }

   }

   public Component getDisplayName() {
      return ((AbilityCore)MammothHeavyPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      return MammothGuardMorphInfo.TEXTURE;
   }

   public float getEyeHeight(LivingEntity entity) {
      return 4.2F;
   }

   public float getShadowSize() {
      return 2.0F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraZoom(LivingEntity entity) {
      return (double)6.0F;
   }

   public boolean canMount() {
      return false;
   }

   public void positionRider(LivingEntity entity, Entity passenger) {
      double height = entity.m_20186_() + (double)entity.m_6972_(entity.m_20089_()).f_20378_ * (double)0.5F;
      passenger.m_6034_(entity.m_20185_(), height, entity.m_20189_());
   }

   public boolean isPartial() {
      return true;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
