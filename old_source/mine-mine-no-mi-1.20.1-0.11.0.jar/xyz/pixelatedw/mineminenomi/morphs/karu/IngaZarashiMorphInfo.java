package xyz.pixelatedw.mineminenomi.morphs.karu;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.karu.IngaZarashiAbility;
import xyz.pixelatedw.mineminenomi.abilities.karu.KarmaAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;

public class IngaZarashiMorphInfo extends MorphInfo {
   private float karma = 0.0F;

   public @Nullable ResourceLocation getTexture(LivingEntity entity) {
      if (entity instanceof AbstractClientPlayer player) {
         return player.m_108560_();
      } else {
         return null;
      }
   }

   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      this.karma = (Float)AbilityCapability.get(entity).map((props) -> (KarmaAbility)props.getEquippedOrPassiveAbility((AbilityCore)KarmaAbility.INSTANCE.get())).map((abl) -> abl.getKarma()).orElse(0.0F);
      float size = 1.0F + this.karma / 300.0F;
      poseStack.m_85841_(size, size, size);
   }

   public Component getDisplayName() {
      return ((AbilityCore)IngaZarashiAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      float eyeHeight = 1.95F + this.karma / 100.0F;
      if (entity.m_6047_()) {
         eyeHeight *= 0.84F;
      }

      return eyeHeight;
   }

   public float getShadowSize() {
      return 0.5F + this.karma / 100.0F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraHeight(LivingEntity entity) {
      boolean isFirstPerson = Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.FIRST_PERSON;
      boolean shouldSit = entity.m_20159_() && entity.m_20202_() != null && entity.m_20202_().shouldRiderSit();
      return isFirstPerson && shouldSit ? (double)0.5F : (double)0.0F;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      float width = 1.0F + this.karma / 50.0F;
      float height = 1.0F + this.karma / 100.0F;
      return ImmutableMap.builder().put(Pose.STANDING, Player.f_36088_.m_20390_(width, height)).put(Pose.CROUCHING, Player.f_36088_.m_20390_(width, height * 0.84F)).build();
   }
}
