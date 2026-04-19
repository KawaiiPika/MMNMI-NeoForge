package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class ColaOverdriveAbility extends Ability {
   private static final int DRAIN_PER_TICK = 2;
   private static final float HOLD_TIME = 300.0F;
   private static final float BASE_COOLDOWN = 100.0F;
   private static final float COOLDOWN_PER_TICK = 1.0F;
   private static final AbilityDescriptionLine.IDescriptionLine COLA_TOOLTIP = (e, a) -> {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(e).orElse((Object)null);
      if (props == null) {
         return null;
      } else {
         int half = (int)Math.floor((double)((float)props.getMaxCola() / 2.0F));
         AbilityStat.Builder statBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_COLA, half);
         return statBuilder.build().getStatDescription();
      }
   };
   public static final RegistryObject<AbilityCore<ColaOverdriveAbility>> INSTANCE = ModRegistry.registerAbility("cola_overdrive", "Cola Overdrive", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user absorbs cola from their reserves to boost their physical abilities.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, ColaOverdriveAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), COLA_TOOLTIP).setUnlockCheck(ColaOverdriveAbility::canUnlock).build("mineminenomi");
   });
   private static final AbilityAttributeModifier MOVEMENT_SPEED_MODIFIER;
   private static final AbilityAttributeModifier SWIM_SPEED_MODIFIER;
   private static final AbilityAttributeModifier JUMP_HEIGHT_MODIFIER;
   private static final AbilityAttributeModifier FALL_RESISTANCE_MODIFIER;
   private static final AbilityAttributeModifier STEP_HEIGHT_MODIFIER;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);

   public ColaOverdriveAbility(AbilityCore<ColaOverdriveAbility> core) {
      super(core);
      Predicate<LivingEntity> isContinuityActive = (entity) -> this.continuousComponent.isContinuous();
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22279_, MOVEMENT_SPEED_MODIFIER, isContinuityActive);
      this.changeStatsComponent.addAttributeModifier((Supplier)ForgeMod.SWIM_SPEED, SWIM_SPEED_MODIFIER, isContinuityActive);
      this.changeStatsComponent.addAttributeModifier((Supplier)ModAttributes.JUMP_HEIGHT, JUMP_HEIGHT_MODIFIER, isContinuityActive);
      this.changeStatsComponent.addAttributeModifier((Supplier)ModAttributes.FALL_RESISTANCE, FALL_RESISTANCE_MODIFIER, isContinuityActive);
      this.changeStatsComponent.addAttributeModifier((Supplier)ForgeMod.STEP_HEIGHT_ADDITION, STEP_HEIGHT_MODIFIER, isContinuityActive);
      super.addComponents(this.continuousComponent, this.changeStatsComponent);
      super.addCanUseCheck(this::canUseAbility);
      super.addUseEvent(this::onUse);
   }

   private void onUse(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 300.0F);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props != null) {
         props.alterCola(-2);
         if (entity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)entity;
            ModNetwork.sendTo(new SUpdateColaAmountPacket(entity), player);
         }

         entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 5, 0));
         Result result = this.canUseAbility(entity, this);
         if (result.isFail()) {
            WyHelper.sendMessage(entity, result.getMessage());
            this.continuousComponent.stopContinuity(entity);
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 100.0F + 1.0F * this.continuousComponent.getContinueTime());
   }

   private Result canUseAbility(LivingEntity entity, IAbility ability) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Result.fail((Component)null);
      } else {
         return props.getCola() <= 0 ? Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_COLA) : Result.success();
      }
   }

   private static boolean canUnlock(LivingEntity user) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      return props == null ? false : props.isCyborg();
   }

   static {
      MOVEMENT_SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("876d97f9-d8a1-487c-af05-ca97d90deb90"), INSTANCE, "Cola Overdrive Movement Speed Multiplier", (double)0.25F, Operation.ADDITION);
      SWIM_SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("876d97f9-d8a1-487c-af05-ca97d90deb90"), INSTANCE, "Cola Overdrive Swim Speed Multiplier", (double)1.0F, Operation.ADDITION);
      JUMP_HEIGHT_MODIFIER = new AbilityAttributeModifier(UUID.fromString("74ddb8cc-1d53-47be-9ed8-ff1d26e8b665"), INSTANCE, "Cola Overdrive Jump Height Multiplier", 2.55, Operation.ADDITION);
      FALL_RESISTANCE_MODIFIER = new AbilityAttributeModifier(UUID.fromString("9120f731-0f9e-4c12-908c-1d4142daacce"), INSTANCE, "Cola Overdrive Fall Resistance Multiplier", (double)5.75F, Operation.ADDITION);
      STEP_HEIGHT_MODIFIER = new AbilityAttributeModifier(UUID.fromString("43f604bd-1ab3-4a27-9b10-bac40ade0e29"), INSTANCE, "Cola Overdrive Step Height Modifier", (double)1.0F, Operation.ADDITION);
   }
}
