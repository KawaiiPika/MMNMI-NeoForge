package xyz.pixelatedw.mineminenomi.abilities.sui;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class SuiHelper {
   public static Result requiresFreeSwimming(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Result.fail((Component)null);
      } else {
         FreeSwimmingAbility freeSwimming = (FreeSwimmingAbility)props.getEquippedAbility((AbilityCore)FreeSwimmingAbility.INSTANCE.get());
         return freeSwimming != null && freeSwimming.isContinuous() && freeSwimming.isSwimming() ? Result.success() : Result.fail(Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{((AbilityCore)FreeSwimmingAbility.INSTANCE.get()).getLocalizedName().getString()}));
      }
   }
}
