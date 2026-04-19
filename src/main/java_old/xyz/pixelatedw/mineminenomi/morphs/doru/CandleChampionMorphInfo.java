package xyz.pixelatedw.mineminenomi.morphs.doru;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleChampionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.CandleChampionFaceLayer;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class CandleChampionMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(2.8F, 4.0F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(2.8F, 3.9F);

   @OnlyIn(Dist.CLIENT)
   public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      if (renderer instanceof MorphRenderer morphRenderer) {
         renderer.m_115326_(new CandleChampionFaceLayer(morphRenderer));
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderr, PoseStack poseStack, float partialTickTime) {
      poseStack.m_85841_(2.0F, 2.0F, 2.0F);
      poseStack.m_85837_((double)0.0F, (double)-0.0F, (double)0.0F);
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      return ModResources.CANDLE_TEXTURE;
   }

   public Component getDisplayName() {
      return ((AbilityCore)CandleChampionAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 3.4F;
   }

   public float getShadowSize() {
      return 1.2F;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
