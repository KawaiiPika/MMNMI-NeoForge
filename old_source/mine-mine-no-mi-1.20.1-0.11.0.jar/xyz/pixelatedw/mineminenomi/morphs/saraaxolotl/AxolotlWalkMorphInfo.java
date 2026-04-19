package xyz.pixelatedw.mineminenomi.morphs.saraaxolotl;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.AxolotlWalkPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class AxolotlWalkMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(0.6F, 0.5F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(0.6F, 0.4F);
   private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/axolotl_walk_0.png"), ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/axolotl_walk_1.png"), ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/axolotl_walk_2.png"), ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/axolotl_walk_3.png"), ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/axolotl_walk_4.png")};

   @Nullable
   public ResourceLocation getTexture(LivingEntity entity) {
      String[] bits = ("" + entity.m_20148_().getMostSignificantBits()).split("");
      int scheme = 0;

      for(String bit : bits) {
         if (!bit.equalsIgnoreCase("-")) {
            scheme += Integer.parseInt(bit);
         }
      }

      int len = 4;
      scheme = Mth.m_14045_(scheme & len, 0, len);
      return TEXTURES[scheme];
   }

   public Component getDisplayName() {
      return ((AbilityCore)AxolotlWalkPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 0.4F;
   }

   public float getShadowSize() {
      return 0.5F;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasEqualDepthTest() {
      return true;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
