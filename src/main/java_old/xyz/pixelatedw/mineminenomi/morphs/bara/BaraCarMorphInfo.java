package xyz.pixelatedw.mineminenomi.morphs.bara;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraCarAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class BaraCarMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(1.3F, 0.7F);
   public static final ResourceLocation BARA_CAR_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/bara_car_wheels.png");

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      poseStack.m_85837_((double)0.0F, 0.2, (double)0.0F);
   }

   public Component getDisplayName() {
      return ((AbilityCore)BaraBaraCarAbility.INSTANCE.get()).getLocalizedName();
   }

   public @Nullable ResourceLocation getTexture(LivingEntity entity) {
      return BARA_CAR_TEXTURE;
   }

   public float getEyeHeight(LivingEntity entity) {
      return 0.7F;
   }

   public float getShadowSize() {
      return 1.1F;
   }

   public boolean canMount() {
      return false;
   }

   public boolean isPartial() {
      return true;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).build();
   }
}
