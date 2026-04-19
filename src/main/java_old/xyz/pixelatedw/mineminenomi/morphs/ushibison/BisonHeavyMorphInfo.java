package xyz.pixelatedw.mineminenomi.morphs.ushibison;

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
import xyz.pixelatedw.mineminenomi.abilities.ushibison.BisonHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class BisonHeavyMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(0.9F, 2.75F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(0.9F, 2.5F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/bison_heavy.png");

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderr, PoseStack matrixStack, float partialTickTime) {
      float scale = 1.6F;
      matrixStack.m_85841_(scale, scale, scale);
   }

   public Component getDisplayName() {
      return ((AbilityCore)BisonHeavyPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public float getEyeHeight(LivingEntity entity) {
      return entity.m_6047_() ? 2.3F : 2.6F;
   }

   public float getShadowSize() {
      return 0.8F;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
