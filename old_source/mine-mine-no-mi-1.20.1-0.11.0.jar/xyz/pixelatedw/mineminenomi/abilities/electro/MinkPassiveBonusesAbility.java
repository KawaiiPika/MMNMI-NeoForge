package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
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
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class MinkPassiveBonusesAbility extends PassiveStatBonusAbility {
   private static final AttributeModifier SPEED_MODIFIER;
   private static final AttributeModifier JUMP_MODIFIER;
   private static final AttributeModifier JUMP_RESISTANCE_MODIFIER;
   private static final Predicate<LivingEntity> TEMPERATURE_CHECK;
   public static final RegistryObject<AbilityCore<MinkPassiveBonusesAbility>> INSTANCE;

   public MinkPassiveBonusesAbility(AbilityCore<MinkPassiveBonusesAbility> core) {
      super(core);
      this.pushStaticAttribute(Attributes.f_22279_, SPEED_MODIFIER);
      this.pushStaticAttribute((Attribute)ModAttributes.JUMP_HEIGHT.get(), JUMP_MODIFIER);
      this.pushStaticAttribute((Attribute)ModAttributes.FALL_RESISTANCE.get(), JUMP_RESISTANCE_MODIFIER);
   }

   public Predicate<LivingEntity> getCheck() {
      return TEMPERATURE_CHECK;
   }

   private static boolean canUnlock(LivingEntity entity) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      return props == null ? false : props.isMink();
   }

   static {
      SPEED_MODIFIER = new AttributeModifier(UUID.fromString("d70f4c72-ba2e-4aaf-8461-8c04d5053367"), "Mink Speed Multiplier", (double)0.5F, Operation.MULTIPLY_BASE);
      JUMP_MODIFIER = new AttributeModifier(UUID.fromString("592e8290-5c83-4467-a3ec-0ae748d9cdc4"), "Mink Jump Boost Addition", 1.6, Operation.ADDITION);
      JUMP_RESISTANCE_MODIFIER = new AttributeModifier(UUID.fromString("d8b7e977-414a-4ca7-b538-063440e503b0"), "Mink Jump Resistance", (double)2.25F, Operation.ADDITION);
      TEMPERATURE_CHECK = (entity) -> {
         BlockPos position = entity.m_20183_();
         return entity.m_9236_().m_204166_(position).m_203656_(ModTags.Biomes.MINK_DEBUFF_BIOMES);
      };
      INSTANCE = ModRegistry.registerAbility("mink_passive_bonuses", "Mink Passive Bonuses", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, AbilityType.PASSIVE, MinkPassiveBonusesAbility::new)).setIcon(ModResources.PERK_ICON).addDescriptionLine(ChangeStatsComponent.getTooltip()).setUnlockCheck(MinkPassiveBonusesAbility::canUnlock).build("mineminenomi"));
   }
}
