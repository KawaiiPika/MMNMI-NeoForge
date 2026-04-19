package xyz.pixelatedw.mineminenomi.morphs.nekoleopard;

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
import xyz.pixelatedw.mineminenomi.abilities.nekoleopard.LeopardWalkPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class LeopardWalkMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(0.8F, 1.0F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(0.8F, 0.9F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/leopard_walk.png");

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderr, PoseStack matrixStack, float partialTickTime) {
      float scale = 1.25F;
      matrixStack.m_85841_(scale, scale, scale);
   }

   public Component getDisplayName() {
      return ((AbilityCore)LeopardWalkPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public float getEyeHeight(LivingEntity entity) {
      return entity.m_6047_() ? 0.8F : 0.85F;
   }

   public float getShadowSize() {
      return 0.9F;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
