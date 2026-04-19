package xyz.pixelatedw.mineminenomi.morphs.gomu;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFourthAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GearFourthMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(2.8F, 4.0F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(2.8F, 3.9F);

   public ResourceLocation getTexture(LivingEntity entity) {
      if (entity instanceof AbstractClientPlayer player) {
         return player.m_108560_();
      } else {
         return null;
      }
   }

   public Component getDisplayName() {
      return ((AbilityCore)GearFourthAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 3.0F;
   }

   public float getShadowSize() {
      return 1.2F;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
