package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class CyborgPassiveBonusesAbility extends PassiveStatBonusAbility {
   private static final AttributeModifier CYBORG_ARMOR;
   private static final AttributeModifier CYBORG_ARMOR_TOUGHNESS;
   private static final AttributeModifier CYBORG_DAMAGE;
   private static final Predicate<LivingEntity> CYBORG_CHECK;
   public static final RegistryObject<AbilityCore<CyborgPassiveBonusesAbility>> INSTANCE;

   public CyborgPassiveBonusesAbility(AbilityCore<CyborgPassiveBonusesAbility> core) {
      super(core);
      this.pushStaticAttribute(Attributes.f_22284_, CYBORG_ARMOR);
      this.pushStaticAttribute(Attributes.f_22285_, CYBORG_ARMOR_TOUGHNESS);
      this.pushStaticAttribute((Attribute)ModAttributes.PUNCH_DAMAGE.get(), CYBORG_DAMAGE);
   }

   public Predicate<LivingEntity> getCheck() {
      return CYBORG_CHECK;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return CYBORG_CHECK.test(entity);
   }

   static {
      CYBORG_ARMOR = new AttributeModifier(UUID.fromString("01344b52-e35e-44a3-9895-6fba1c10fc20"), "Cyborg Armor Bonus", (double)10.0F, Operation.ADDITION);
      CYBORG_ARMOR_TOUGHNESS = new AttributeModifier(UUID.fromString("f2443845-6f63-4916-b57e-a6805cfa47ae"), "Cyborg Armor Toughness Bonus", (double)4.0F, Operation.ADDITION);
      CYBORG_DAMAGE = new AttributeModifier(UUID.fromString("81e152e3-46e7-4b03-bf0d-f5d8a7a870df"), "Cyborg Damage Bonus", (double)2.0F, Operation.ADDITION);
      CYBORG_CHECK = (entity) -> (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isCyborg()).orElse(false);
      INSTANCE = ModRegistry.registerAbility("cyborg_passive_bonuses", "Cyborg Passive Bonuses", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, AbilityType.PASSIVE, CyborgPassiveBonusesAbility::new)).setIcon(ModResources.PERK_ICON).addDescriptionLine(ChangeStatsComponent.getTooltip()).setUnlockCheck(CyborgPassiveBonusesAbility::canUnlock).build("mineminenomi"));
   }
}
