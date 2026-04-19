package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.events.entity.LivingHealByEvent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class HealComponent extends AbilityComponent<IAbility> {
   private static final UUID HEAL_BONUS_MANAGER_UUID = UUID.fromString("c778872e-258a-4672-8cd6-0f930ad9adba");
   private final BonusManager bonusManager;

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float heal) {
      return getTooltip(heal, heal);
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float min, float max) {
      return (e, a) -> {
         AbilityStat.Builder statBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_HEAL, min, max);
         a.getComponent((AbilityComponentKey)ModAbilityComponents.HEAL.get()).ifPresent((comp) -> {
            float minBonus = comp.getBonusManager().applyBonus(min) - min;
            float maxBonus = comp.getBonusManager().applyBonus(max) - max;
            float diffBonus = minBonus + maxBonus;
            AbilityStat.AbilityStatType bonusType = diffBonus > 0.0F ? AbilityStat.AbilityStatType.BUFF : (diffBonus < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
            statBuilder.withBonus(minBonus, maxBonus, bonusType);
         });
         return statBuilder.build().getStatDescription();
      };
   }

   public HealComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.HEAL.get(), ability);
      this.bonusManager = new BonusManager(HEAL_BONUS_MANAGER_UUID);
      this.addBonusManager(this.bonusManager);
   }

   public boolean healTarget(LivingEntity healer, LivingEntity target, float baseHeal) {
      this.ensureIsRegistered();
      float finalHeal = this.bonusManager.applyBonus(baseHeal);
      LivingHealByEvent event = new LivingHealByEvent(healer, target, finalHeal);
      if (!MinecraftForge.EVENT_BUS.post(event)) {
         target.m_5634_(finalHeal);
         return true;
      } else {
         return false;
      }
   }

   public BonusManager getBonusManager() {
      return this.bonusManager;
   }
}
