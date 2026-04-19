package xyz.pixelatedw.mineminenomi.morphs.kame;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.kame.KameWalkPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class KameWalkMorphInfo extends MorphInfo {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/kame_walk.png");

   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public Component getDisplayName() {
      return ((AbilityCore)KameWalkPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public boolean isPartial() {
      return true;
   }
}
