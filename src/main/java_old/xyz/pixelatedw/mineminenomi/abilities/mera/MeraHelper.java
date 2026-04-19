package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class MeraHelper {
   public static Result canUseMeraAbilities(LivingEntity entity, IAbility ability) {
      DaiEnkaiAbility daiEnkaiAbility = (DaiEnkaiAbility)AbilityCapability.get(entity).map((props) -> (DaiEnkaiAbility)props.getEquippedAbility((AbilityCore)DaiEnkaiAbility.INSTANCE.get())).orElse((Object)null);
      return daiEnkaiAbility == null || !daiEnkaiAbility.isCharging() && !daiEnkaiAbility.isContinuous() ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_NEED_NOT_DAI_ENKAI);
   }
}
