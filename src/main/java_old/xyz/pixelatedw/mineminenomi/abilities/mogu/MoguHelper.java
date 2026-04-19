package xyz.pixelatedw.mineminenomi.abilities.mogu;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class MoguHelper {
   public static Result requiresHeavyPoint(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.MOGU_HEAVY.get());
   }
}
