package xyz.pixelatedw.mineminenomi.abilities.brawler;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
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

public class BrawlerPassiveBonusesAbility extends PassiveStatBonusAbility {
   private static final UUID BRAWLER_ATTACK_BONUS_UUID = UUID.fromString("4e4d55e7-774b-4010-8722-a15f9da99807");
   private static final AttributeModifier BRAWLER_ATTACK_SPEED_MODIFIER;
   private static final Predicate<LivingEntity> BRAWLER_CHECK;
   private static final AbilityDescriptionLine.IDescriptionLine TOOLTIP;
   public static final RegistryObject<AbilityCore<BrawlerPassiveBonusesAbility>> INSTANCE;

   public BrawlerPassiveBonusesAbility(AbilityCore<BrawlerPassiveBonusesAbility> core) {
      super(core);
      this.pushDynamicAttribute((Attribute)ModAttributes.PUNCH_DAMAGE.get(), (entity) -> this.getModifier(entity));
      this.pushStaticAttribute(Attributes.f_22283_, BRAWLER_ATTACK_SPEED_MODIFIER);
   }

   private AttributeModifier getModifier(LivingEntity entity) {
      double bonus = this.getBonus(entity);
      return new AttributeModifier(BRAWLER_ATTACK_BONUS_UUID, "Brawler Punch Bonus", bonus, Operation.ADDITION);
   }

   private double getBonus(LivingEntity entity) {
      double doriki = (Double)EntityStatsCapability.get(entity).map((props) -> props.getDoriki()).orElse((double)0.0F);
      return (double)2.0F + doriki * 5.0E-4;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isBrawler()).orElse(false);
   }

   public Predicate<LivingEntity> getCheck() {
      return BRAWLER_CHECK;
   }

   static {
      BRAWLER_ATTACK_SPEED_MODIFIER = new AttributeModifier(UUID.fromString("3c6bbee0-6f8b-473c-adfc-feb7cd73dd84"), "Brawler Speed Multiplier", (double)-0.5F, Operation.ADDITION);
      BRAWLER_CHECK = (entity) -> {
         ItemStack heldItem = entity.m_21205_();
         return heldItem.m_41619_();
      };
      TOOLTIP = (entity, ability) -> {
         Component[] lines = new Component[]{Component.m_237113_("§a" + ModI18nAbilities.DESCRIPTION_STAT_NAME_STATS.getString() + "§r"), null, null};
         Component attrName = Component.m_237115_(((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22087_());
         float value = (float)((BrawlerPassiveBonusesAbility)ability).getBonus(entity);
         Component statText = (new AbilityStat.Builder(attrName, value)).withSign("+").build().getStatDescription(2);
         lines[1] = statText;
         Component var14 = Component.m_237115_(Attributes.f_22283_.m_22087_());
         value = (float)BRAWLER_ATTACK_SPEED_MODIFIER.m_22218_();
         Component speedStatText = (new AbilityStat.Builder(var14, value)).build().getStatDescription(2);
         lines[2] = speedStatText;
         StringBuilder sb = new StringBuilder();
         int lineId = 0;

         for(Component text : lines) {
            boolean hasFollowingLine = lineId++ < lines.length - 1;
            String var10001 = text.getString();
            sb.append(var10001 + (hasFollowingLine ? "\n" : ""));
         }

         return Component.m_237113_(sb.toString());
      };
      INSTANCE = ModRegistry.registerAbility("brawler_passive_bonus", "Brawler Passive Bonuses", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, BrawlerPassiveBonusesAbility::new)).setIcon(ModResources.PERK_ICON).addDescriptionLine(TOOLTIP).setUnlockCheck(BrawlerPassiveBonusesAbility::canUnlock).build("mineminenomi"));
   }
}
