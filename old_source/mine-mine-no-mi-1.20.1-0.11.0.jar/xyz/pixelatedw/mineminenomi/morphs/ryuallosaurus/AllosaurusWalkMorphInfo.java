package xyz.pixelatedw.mineminenomi.morphs.ryuallosaurus;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AllosaurusWalkPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class AllosaurusWalkMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(3.0F, 3.7F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(3.0F, 3.6F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/allosaurus_walk.png");

   public @Nullable ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public Component getDisplayName() {
      return ((AbilityCore)AllosaurusWalkPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 3.5F;
   }

   public float getShadowSize() {
      return 1.5F;
   }

   public boolean canMount() {
      return false;
   }

   public void positionRider(LivingEntity entity, Entity passenger) {
      double height = entity.m_20186_() + entity.m_6048_() - (double)0.2F;
      passenger.m_6034_(entity.m_20185_(), height, entity.m_20189_());
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
