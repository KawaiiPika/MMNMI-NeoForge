package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.sui.FreeSwimmingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class FishmanKarateHelper {
   public static boolean isInWater(LivingEntity entity) {
      boolean isInWater = entity.m_20069_();
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return isInWater;
      } else {
         FreeSwimmingAbility freeSwimming = (FreeSwimmingAbility)props.getEquippedAbility((AbilityCore)FreeSwimmingAbility.INSTANCE.get());
         return freeSwimming != null && freeSwimming.isContinuous() && freeSwimming.isSwimming() ? true : isInWater;
      }
   }

   public static AbilityDescriptionLine.IDescriptionLine getWaterBuffedProjectileDamageStat(float waterMultiplier) {
      return (entity, ability) -> {
         NuProjectileEntity proj = (NuProjectileEntity)ability.getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).map((comp) -> comp.getCachedProjectile(entity)).orElse((Object)null);
         if (proj != null && proj.getDamage() > 0.0F) {
            float waterDamage = proj.getDamage() * waterMultiplier;
            AbilityStat.Builder baseStatBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_BASE_DAMAGE, proj.getDamage());
            AbilityStat.Builder waterStatBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_WATER_DAMAGE, waterDamage);
            ability.getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).ifPresent((comp) -> {
               float baseBonus = comp.getDamageBonusManager().applyBonus(proj.getDamage()) - proj.getDamage();
               AbilityStat.AbilityStatType bonusType = baseBonus > 0.0F ? AbilityStat.AbilityStatType.BUFF : (baseBonus < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
               baseStatBuilder.withBonus(baseBonus, bonusType);
               float waterBonusDamage = comp.getDamageBonusManager().applyBonus(waterDamage) - waterDamage;
               AbilityStat.AbilityStatType waterBonusType = waterBonusDamage > 0.0F ? AbilityStat.AbilityStatType.BUFF : (waterBonusDamage < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
               waterStatBuilder.withBonus(waterBonusDamage, waterBonusType);
            });
            Component baseStat = baseStatBuilder.build().getStatDescription(2);
            Component waterStat = waterStatBuilder.build().getStatDescription(2);
            StringBuilder sb = new StringBuilder();
            sb.append("§a" + ModI18nAbilities.DESCRIPTION_STAT_NAME_PROJECTILE.getString() + "§r\n");
            sb.append(baseStat.getString() + "\n");
            sb.append(waterStat.getString());
            return Component.m_237113_(sb.toString());
         } else {
            return null;
         }
      };
   }

   public static AbilityDescriptionLine.IDescriptionLine getWaterBuffedDamageStat(float normalDamage, float waterMultiplier) {
      return (entity, ability) -> {
         float waterDamage = normalDamage * waterMultiplier;
         AbilityStat.Builder baseStatBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_BASE_DAMAGE, normalDamage, normalDamage);
         AbilityStat.Builder waterStatBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_WATER_DAMAGE, waterDamage, waterDamage);
         ability.getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).ifPresent((comp) -> {
            float baseBonusDamage = comp.getBonusManager().applyBonus(normalDamage) - normalDamage;
            AbilityStat.AbilityStatType baseBonusType = baseBonusDamage > 0.0F ? AbilityStat.AbilityStatType.BUFF : (baseBonusDamage < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
            baseStatBuilder.withBonus(baseBonusDamage, baseBonusType);
            float waterBonusDamage = comp.getBonusManager().applyBonus(waterDamage) - waterDamage;
            AbilityStat.AbilityStatType waterBonusType = waterBonusDamage > 0.0F ? AbilityStat.AbilityStatType.BUFF : (waterBonusDamage < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
            waterStatBuilder.withBonus(waterBonusDamage, waterBonusType);
         });
         Component baseStat = baseStatBuilder.build().getStatDescription();
         Component waterStat = waterStatBuilder.build().getStatDescription();
         StringBuilder sb = new StringBuilder();
         sb.append(baseStat.getString() + "\n");
         sb.append(waterStat.getString());
         return Component.m_237113_(sb.toString());
      };
   }
}
