package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class BaraHelper {
   public static Result hasLimbs(LivingEntity entity, IAbility ability) {
      return entity.m_21023_((MobEffect)ModEffects.NO_HANDS.get()) ? Result.fail(ModI18nAbilities.MESSAGE_NO_LIMBS) : Result.success();
   }
}
