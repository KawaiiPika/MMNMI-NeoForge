package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class NekoLeopardHelper {
   public static Result requiresEitherPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.LEOPARD_HEAVY.get(), (MorphInfo)ModMorphs.LEOPARD_WALK.get());
   }

   public static Result requiresHeavyPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.LEOPARD_HEAVY.get());
   }

   public static Result requiresWalkPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.LEOPARD_WALK.get());
   }
}
