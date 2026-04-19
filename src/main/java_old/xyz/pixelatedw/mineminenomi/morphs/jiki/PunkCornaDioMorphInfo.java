package xyz.pixelatedw.mineminenomi.morphs.jiki;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.jiki.PunkCornaDioAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class PunkCornaDioMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(3.0F, 3.1F);
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/corna_dio_bull.png");

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_252880_(0.0F, 2.0F, 0.0F);
   }

   public Component getDisplayName() {
      return ((AbilityCore)PunkCornaDioAbility.INSTANCE.get()).getLocalizedName();
   }

   @Nullable
   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public float getEyeHeight(LivingEntity entity) {
      return 3.7F;
   }

   public float getShadowSize() {
      return 2.0F;
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
