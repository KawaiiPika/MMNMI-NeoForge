package xyz.pixelatedw.mineminenomi.morphs.sai;

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
import xyz.pixelatedw.mineminenomi.abilities.sai.SaiWalkPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class SaiWalkMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(1.7F, 2.3F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(1.7F, 2.2F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/sai_walk.png");

   public @Nullable ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      poseStack.m_85841_(2.0F, 2.0F, 2.0F);
   }

   public Component getDisplayName() {
      return ((AbilityCore)SaiWalkPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 2.1F;
   }

   public float getShadowSize() {
      return 1.5F;
   }

   public boolean canMount() {
      return false;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
