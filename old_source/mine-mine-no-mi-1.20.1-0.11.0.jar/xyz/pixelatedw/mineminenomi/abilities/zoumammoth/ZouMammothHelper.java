package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class ZouMammothHelper {
   public static Result requiresEitherPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.MAMMOTH_GUARD.get(), (MorphInfo)ModMorphs.MAMMOTH_HEAVY.get());
   }

   public static Result requiresHeavyPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.MAMMOTH_HEAVY.get());
   }

   public static Result requiresGuardPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.MAMMOTH_GUARD.get());
   }
}
