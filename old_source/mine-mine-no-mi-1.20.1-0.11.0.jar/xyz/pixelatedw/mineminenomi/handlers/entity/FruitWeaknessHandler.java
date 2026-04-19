package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.function.Predicate;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class FruitWeaknessHandler {
   private static final Predicate<IAbility> WATER_DISABLED_ABILITIES_PREDICATE = (abl) -> abl.getCore().getType() != AbilityType.PASSIVE || abl.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS;

   public static void tryApplyWaterWeakness(LivingEntity entity) {
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null && abilityData != null) {
         boolean hasAnyFruit = props.hasAnyDevilFruit();
         boolean hasAnyFruitAbility = abilityData.getUnlockedAbilities((a) -> a.getCategory() == AbilityCategory.DEVIL_FRUITS).size() > 0;
         if (hasAnyFruit || hasAnyFruitAbility) {
            boolean hasStrongWaterWeakness = props.hasStrongWaterWeakness();
            boolean hasKairosekiWeakness = props.hasKairosekiWeakness();
            boolean hasWeakWaterWeakness = props.hasWeakWaterWeakness();
            if (entity.m_9236_().m_46467_() % 30L == 0L) {
               hasStrongWaterWeakness = AbilityHelper.isAffectedByWater(entity);
               hasWeakWaterWeakness = AbilityHelper.isAffectedByWater(entity, 0.25F);
               hasKairosekiWeakness = AbilityHelper.isKairosekiNearby(entity);
               props.setStrongWaterWeakness(hasStrongWaterWeakness);
               props.setKairosekiWeakness(hasKairosekiWeakness);
               props.setWeakWaterWeakness(hasWeakWaterWeakness);
            }

            if (props.hasAnyDevilFruit() && hasStrongWaterWeakness) {
               boolean var10000;
               label64: {
                  if (entity instanceof Player) {
                     Player player = (Player)entity;
                     if (player.m_7500_()) {
                        var10000 = true;
                        break label64;
                     }
                  }

                  var10000 = false;
               }

               boolean isCreative = var10000;
               if (!isCreative) {
                  if (entity.m_6067_()) {
                     AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, entity.m_20184_().f_82480_ - 0.15, entity.m_20184_().f_82481_);
                  } else {
                     AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, entity.m_20184_().f_82480_ - 0.1, entity.m_20184_().f_82481_);
                  }
               }
            }

            if (!entity.m_9236_().f_46443_) {
               MobEffect curseType = null;
               if (hasKairosekiWeakness) {
                  curseType = (MobEffect)ModEffects.KAIROSEKI_WEAKNESS.get();
               } else if (hasStrongWaterWeakness) {
                  curseType = (MobEffect)ModEffects.WATER_WEAKNESS.get();
               }

               if (!hasStrongWaterWeakness && !hasKairosekiWeakness) {
                  if (hasWeakWaterWeakness) {
                     entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.SWIM_SLOWDOWN.get(), 20, 2, false, false, true));
                     entity.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 20, 1, false, false, true));
                  }
               } else {
                  if (curseType != null) {
                     entity.m_7292_(new MobEffectInstance(curseType, 40, 0));
                  }

                  if (entity instanceof Player) {
                     Player player = (Player)entity;
                     if (ServerConfig.areExtraWaterChecksEnabled()) {
                        player.m_36399_(0.015F);
                     }
                  }

                  AbilityHelper.disableAbilities(entity, 20, WATER_DISABLED_ABILITIES_PREDICATE);
               }
            }

         }
      }
   }
}
