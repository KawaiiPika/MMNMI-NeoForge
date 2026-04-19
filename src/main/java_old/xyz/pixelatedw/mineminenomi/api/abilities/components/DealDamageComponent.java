package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.LinkedList;
import java.util.UUID;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class DealDamageComponent extends AbilityComponent<IAbility> {
   private static final UUID DAMAGE_BONUS_MANAGER_UUID = UUID.fromString("f4ac25c4-ef9f-4537-8471-406a02737308");
   private static final UUID STYLE_BONUS = UUID.fromString("dd55f409-9924-4cca-afbe-522462884d71");
   private static final int TARGETS_SIZE = 10;
   private LinkedList<LivingEntity> lastTargets = new LinkedList();
   private final BonusManager bonusManager;

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float damage) {
      return getTooltip(damage, damage);
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float min, float max) {
      return (e, a) -> {
         AbilityStat.Builder statBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE, min, max);
         a.getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).ifPresent((comp) -> {
            float minBonusDamage = comp.getBonusManager().applyBonus(min) - min;
            float maxBonusDamage = comp.getBonusManager().applyBonus(max) - max;
            float diffBonus = minBonusDamage + maxBonusDamage;
            AbilityStat.AbilityStatType bonusType = diffBonus > 0.0F ? AbilityStat.AbilityStatType.BUFF : (diffBonus < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
            statBuilder.withBonus(minBonusDamage, maxBonusDamage, bonusType);
         });
         return statBuilder.build().getStatDescription();
      };
   }

   public DealDamageComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.DAMAGE.get(), ability);
      this.bonusManager = new BonusManager(DAMAGE_BONUS_MANAGER_UUID);
      this.addBonusManager(this.bonusManager);
   }

   public boolean hurtTarget(LivingEntity entity, LivingEntity target, float damage) {
      return this.hurtTarget(entity, target, damage, this.getDamageSource(entity));
   }

   public boolean hurtTarget(LivingEntity entity, LivingEntity target, float damage, DamageSource damageSource) {
      this.ensureIsRegistered();
      float finalDamage = damage * this.getDamageMultiplier(entity);
      if (target.m_6469_(damageSource, finalDamage)) {
         if (target.m_6084_()) {
            if (this.lastTargets.size() >= 0 && this.lastTargets.size() == 10) {
               this.lastTargets.remove();
            }

            this.lastTargets.add(target);
         }

         return true;
      } else {
         return false;
      }
   }

   public DamageSource getDamageSource(LivingEntity source) {
      return ModDamageSources.getInstance().ability(source, this.getAbility().getCore());
   }

   private float getDamageMultiplier(LivingEntity entity) {
      return ((Double)EntityStatsCapability.get(entity).map((props) -> props.getDamageMultiplier()).orElse((double)1.0F)).floatValue();
   }

   public @Nullable LivingEntity getLastTarget() {
      try {
         return (LivingEntity)this.lastTargets.getLast();
      } catch (Exception var2) {
         return null;
      }
   }

   public @Nullable LivingEntity getLastNTarget(int nth) {
      try {
         return (LivingEntity)this.lastTargets.get(nth);
      } catch (Exception var3) {
         return null;
      }
   }

   public BonusManager getBonusManager() {
      return this.bonusManager;
   }
}
