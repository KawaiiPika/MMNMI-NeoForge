package xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class RyuBrachioHelper {
   public static Result requiresEitherPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.BRACHIO_HEAVY.get(), (MorphInfo)ModMorphs.BRACHIO_GUARD.get());
   }

   public static Result requiresHeavyPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.BRACHIO_HEAVY.get());
   }

   public static Result requiresGuardPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.BRACHIO_GUARD.get());
   }
}
