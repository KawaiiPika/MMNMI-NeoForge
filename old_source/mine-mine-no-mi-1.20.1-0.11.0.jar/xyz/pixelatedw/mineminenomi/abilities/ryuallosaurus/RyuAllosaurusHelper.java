package xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class RyuAllosaurusHelper {
   public static Result requiresEitherPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.ALLOSAURUS_HEAVY.get(), (MorphInfo)ModMorphs.ALLOSAURUS_WALK.get());
   }

   public static Result requiresHeavyPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.ALLOSAURUS_HEAVY.get());
   }

   public static Result requiresWalkPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.ALLOSAURUS_WALK.get());
   }
}
