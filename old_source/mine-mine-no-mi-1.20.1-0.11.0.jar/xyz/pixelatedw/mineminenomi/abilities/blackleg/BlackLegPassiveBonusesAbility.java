package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class BlackLegPassiveBonusesAbility extends PassiveStatBonusAbility {
   private static final UUID BLACK_LEG_ATTACK_BONUS_UUID = UUID.fromString("be5937cc-c1da-4891-9583-cebbab2134d3");
   private static final AttributeModifier BLACK_LEG_ATTACK_RANGE_MODIFIER;
   private static final Predicate<LivingEntity> BLACK_LEG_CHECK;
   private static final AbilityDescriptionLine.IDescriptionLine TOOLTIP;
   public static final RegistryObject<AbilityCore<BlackLegPassiveBonusesAbility>> INSTANCE;

   public BlackLegPassiveBonusesAbility(AbilityCore<BlackLegPassiveBonusesAbility> core) {
      super(core);
      this.pushDynamicAttribute((Attribute)ModAttributes.PUNCH_DAMAGE.get(), (entity) -> this.getModifier(entity));
      this.pushStaticAttribute((Attribute)ForgeMod.ENTITY_REACH.get(), BLACK_LEG_ATTACK_RANGE_MODIFIER);
   }

   private AttributeModifier getModifier(LivingEntity entity) {
      double bonus = this.getBonus(entity);
      return new AttributeModifier(BLACK_LEG_ATTACK_BONUS_UUID, "Black Leg Attack Bonus", bonus, Operation.ADDITION);
   }

   private double getBonus(LivingEntity entity) {
      double doriki = (Double)EntityStatsCapability.get(entity).map((props) -> props.getDoriki()).orElse((double)0.0F);
      return (double)1.0F + doriki * 4.0E-4;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isBlackLeg()).orElse(false);
   }

   public Predicate<LivingEntity> getCheck() {
      return BLACK_LEG_CHECK;
   }

   static {
      BLACK_LEG_ATTACK_RANGE_MODIFIER = new AttributeModifier(UUID.fromString("cd7d0526-005b-4ef2-a61f-0e941b0d6e1a"), "Black Leg Range Multiplier", (double)0.5F, Operation.ADDITION);
      BLACK_LEG_CHECK = (entity) -> {
         ItemStack heldItem = entity.m_21205_();
         return heldItem.m_41619_();
      };
      TOOLTIP = (entity, ability) -> {
         Component[] lines = new Component[]{Component.m_237113_("§a" + ModI18nAbilities.DESCRIPTION_STAT_NAME_STATS.getString() + "§r"), null, null};
         Component attrName = Component.m_237115_(((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22087_());
         float value = (float)((BlackLegPassiveBonusesAbility)ability).getBonus(entity);
         Component punchStatText = (new AbilityStat.Builder(attrName, value)).withSign("+").build().getStatDescription(2);
         lines[1] = punchStatText;
         Component var14 = Component.m_237115_(((Attribute)ForgeMod.ENTITY_REACH.get()).m_22087_());
         value = (float)BLACK_LEG_ATTACK_RANGE_MODIFIER.m_22218_();
         Component rangeStatText = (new AbilityStat.Builder(var14, value)).withSign("+").build().getStatDescription(2);
         lines[2] = rangeStatText;
         StringBuilder sb = new StringBuilder();
         int lineId = 0;

         for(Component text : lines) {
            boolean hasFollowingLine = lineId++ < lines.length - 1;
            String var10001 = text.getString();
            sb.append(var10001 + (hasFollowingLine ? "\n" : ""));
         }

         return Component.m_237113_(sb.toString());
      };
      INSTANCE = ModRegistry.registerAbility("black_leg_passive_bonus", "Black Leg Passive Bonuses", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, BlackLegPassiveBonusesAbility::new)).setIcon(ModResources.PERK_ICON).addDescriptionLine(TOOLTIP).setUnlockCheck(BlackLegPassiveBonusesAbility::canUnlock).build("mineminenomi"));
   }
}
