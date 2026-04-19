package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class FishmanPassiveBonusesAbility extends PassiveStatBonusAbility {
   private static final AttributeModifier SWIM_SPEED_MODIFIER;
   private static final Predicate<LivingEntity> FISHMAN_CHECK;
   public static final RegistryObject<AbilityCore<FishmanPassiveBonusesAbility>> INSTANCE;

   public FishmanPassiveBonusesAbility(AbilityCore<FishmanPassiveBonusesAbility> core) {
      super(core);
      this.pushStaticAttribute((Attribute)ForgeMod.SWIM_SPEED.get(), SWIM_SPEED_MODIFIER);
      this.addDuringPassiveEvent(this::duringPassiveEvent);
   }

   private void duringPassiveEvent(LivingEntity entity) {
      if (entity.m_204029_(FluidTags.f_13131_)) {
         entity.m_20301_(300);
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 250, 0, false, false));
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19598_, 100, 0, false, false));
      }

   }

   public Predicate<LivingEntity> getCheck() {
      return FISHMAN_CHECK;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isFishman()).orElse(false);
   }

   static {
      SWIM_SPEED_MODIFIER = new AttributeModifier(UUID.fromString("d9a209e7-15c0-490e-a9b6-4470b0bf6d28"), "Fishman Swim Speed Multiplier", (double)3.0F, Operation.MULTIPLY_BASE);
      FISHMAN_CHECK = (entity) -> {
         boolean hasAnyFruit = (Boolean)DevilFruitCapability.get(entity).map((props) -> props.hasAnyDevilFruit()).orElse(false);
         return !entity.m_20069_() || !hasAnyFruit || entity.m_21023_((MobEffect)ModEffects.BUBBLY_CORAL.get());
      };
      INSTANCE = ModRegistry.registerAbility("fishman_passive_bonuses", "Fishman Passive Bonuses", (id, name) -> (new AbilityCore.Builder(name, id, AbilityCategory.RACIAL, AbilityType.PASSIVE, FishmanPassiveBonusesAbility::new)).setIcon(ModResources.PERK_ICON).addDescriptionLine(ChangeStatsComponent.getTooltip()).setUnlockCheck(FishmanPassiveBonusesAbility::canUnlock).build("mineminenomi"));
   }
}
