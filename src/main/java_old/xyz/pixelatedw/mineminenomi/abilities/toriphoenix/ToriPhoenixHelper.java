package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class ToriPhoenixHelper {
   public static Result requiresEitherPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.PHOENIX_ASSAULT.get(), (MorphInfo)ModMorphs.PHOENIX_FLY.get());
   }

   public static Result requiresAssaultPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.PHOENIX_ASSAULT.get());
   }

   public static Result requiresFlyPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.PHOENIX_FLY.get());
   }
}
