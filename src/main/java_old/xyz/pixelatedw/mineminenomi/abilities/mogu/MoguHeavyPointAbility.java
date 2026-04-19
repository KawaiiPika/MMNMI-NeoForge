package xyz.pixelatedw.mineminenomi.abilities.mogu;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MoguHeavyPointAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<MoguHeavyPointAbility>> INSTANCE = ModRegistry.registerAbility("mogu_heavy_point", "Mogu Heavy Point", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into a mole, which focuses on strength and digging speed", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, MoguHeavyPointAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;
   private static final AbilityAttributeModifier MINING_SPEED_MODIFIER;

   public MoguHeavyPointAbility(AbilityCore<MoguHeavyPointAbility> core) {
      super(core);
      Predicate<LivingEntity> isContinuityActive = (entity) -> this.continuousComponent.isContinuous();
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.BLOCK_REACH, REACH_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.ENTITY_REACH, REACH_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.MINING_SPEED, MINING_SPEED_MODIFIER, isContinuityActive);
      this.continuousComponent.addEndEvent(100, this::onEndContinuityEvent);
   }

   public void duringContinuityEvent(Player player, int time) {
      player.m_7292_(new MobEffectInstance(MobEffects.f_19598_, 5, 2, false, false));
      if (!player.m_21023_(MobEffects.f_19611_) || player.m_21124_(MobEffects.f_19611_).m_19557_() < 500) {
         player.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 500, 0, false, false));
      }

   }

   protected void onEndContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_21195_(MobEffects.f_19596_);
      entity.m_21195_(MobEffects.f_19611_);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.MOGU_HEAVY.get();
   }

   static {
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Mogu Heavy Point Modifier", (double)5.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Mogu Heavy Point Modifier", (double)3.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Mogu Heavy Point Modifier", (double)0.15F, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Mogu Heavy Reach Modifier", (double)-0.5F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Mogu Heavy Point Toughness Modifier", (double)1.0F, Operation.ADDITION);
      MINING_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MINING_SPEED_UUID, INSTANCE, "Mogu Heavy Point Mining Speed Modifier", (double)5.0F, Operation.ADDITION);
   }
}
