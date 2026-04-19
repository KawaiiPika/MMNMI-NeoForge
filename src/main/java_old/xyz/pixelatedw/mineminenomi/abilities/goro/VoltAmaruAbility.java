package xyz.pixelatedw.mineminenomi.abilities.goro;

import java.awt.Color;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRecalculateEyeHeightPacket;

public class VoltAmaruAbility extends Ability {
   private static final ResourceLocation DEFAULT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/volt_amaru.png");
   private static final ResourceLocation ALT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/alts/volt_amaru.png");
   private static final int COOLDOWN = 1000;
   private static final int CHARGE_TIME = 20;
   private static final int HOLD_TIME = 400;
   private static final Color COLOR = WyHelper.hexToRGB("#F0EC7155");
   public static final RegistryObject<AbilityCore<VoltAmaruAbility>> INSTANCE = ModRegistry.registerAbility("volt_amaru", "Volt Amaru", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into a powerful, lightning giant massively boosting physical attributes and lightning attacks", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, VoltAmaruAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(1000.0F), ChargeComponent.getTooltip(20.0F), ContinuousComponent.getTooltip(400.0F), ChangeStatsComponent.getTooltip()).setIcon(DEFAULT_ICON).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addEndEvent(this::onChargeEnd);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart).addEndEvent(this::onContinuityEnd);
   private final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);
   private final MorphComponent morphComponent = new MorphComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addOnHitEvent(this::onHitEvent).addTryHitEvent(this::tryHitEvent);
   private final SkinOverlayComponent skinOverlayComponent;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier KNOCKBACK_RESISTANCE;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;
   private static final AbilityAttributeModifier HEALTH_BOOST;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier JUMP_BOOST;
   private static final AbilityAttributeModifier FALL_RESISTENCE_MODIFIER;
   private static final AbilityOverlay OVERLAY;
   private static final AbilityOverlay OVERLAY_ALT;
   public float speed;

   public VoltAmaruAbility(AbilityCore<VoltAmaruAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.speed = 0.0F;
      Predicate<LivingEntity> isMorphActive = (entity) -> this.morphComponent.isMorphed();
      this.changeStatsComponent.addAttributeModifier((Supplier)ForgeMod.ENTITY_REACH, REACH_MODIFIER, isMorphActive);
      this.changeStatsComponent.addAttributeModifier((Supplier)ForgeMod.BLOCK_REACH, REACH_MODIFIER, isMorphActive);
      this.changeStatsComponent.addAttributeModifier((Supplier)ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER, isMorphActive);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22276_, HEALTH_BOOST, isMorphActive);
      this.changeStatsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER, isMorphActive);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER, isMorphActive);
      this.changeStatsComponent.addAttributeModifier((Supplier)ModAttributes.JUMP_HEIGHT, JUMP_BOOST, isMorphActive);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22278_, KNOCKBACK_RESISTANCE, isMorphActive);
      this.changeStatsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE, FALL_RESISTENCE_MODIFIER);
      super.addComponents(this.chargeComponent, this.continuousComponent, this.changeStatsComponent, this.morphComponent, this.dealDamageComponent, this.hitTriggerComponent, this.skinOverlayComponent);
      super.addUseEvent(this::onUseEvent);
      this.addEquipEvent(this::equipEvent);
   }

   public void equipEvent(LivingEntity entity, IAbility ability) {
      this.setDisplayIcon(DEFAULT_ICON);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.continuousComponent.isContinuous()) {
         this.chargeComponent.startCharging(entity, 20.0F);
      } else {
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
      LightningDischargeEntity ball = new LightningDischargeEntity(entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
      ball.setSize(4.0F);
      ball.setLightningLength(10.0F);
      ball.setAliveTicks(20);
      ball.setColor(ClientConfig.isGoroBlue() ? ElThorAbility.BLUE_THUNDER : ElThorAbility.YELLOW_THUNDER);
      entity.m_9236_().m_7967_(ball);
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 20, 1, false, false));
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 400.0F);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.VOLT_AMARU.get());
      this.changeStatsComponent.applyModifiers(entity);
      if (!entity.m_9236_().f_46443_) {
         ModNetwork.sendToAllTrackingAndSelf(new SRecalculateEyeHeightPacket(entity.m_19879_()), entity);
      }

      IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityDataProps != null) {
         PropelledFlightAbility voltAmaruFlightAbility = (PropelledFlightAbility)abilityDataProps.getPassiveAbility((AbilityCore)VoltAmaruFlightAbility.INSTANCE.get());
         if (voltAmaruFlightAbility != null && !voltAmaruFlightAbility.isPaused()) {
            ((Player)entity).m_150110_().f_35935_ = true;
            voltAmaruFlightAbility.enableFlight((Player)entity);
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
      this.morphComponent.stopMorph(entity);
      this.changeStatsComponent.removeModifiers(entity);
      super.cooldownComponent.startCooldown(entity, 1000.0F);
      IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityDataProps != null) {
         PropelledFlightAbility voltAmaruFlightAbility = (PropelledFlightAbility)abilityDataProps.getPassiveAbility((AbilityCore)VoltAmaruFlightAbility.INSTANCE.get());
         if (voltAmaruFlightAbility != null) {
            voltAmaruFlightAbility.disableFlight((Player)entity);
         }

      }
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return !this.morphComponent.isMorphed() ? HitTriggerComponent.HitResult.PASS : HitTriggerComponent.HitResult.HIT;
   }

   private boolean onHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      float conductivity = ModTags.Items.CONDUCTIVE.getValue(entity.m_21205_().m_41720_());
      if (conductivity > 0.5F) {
         AbilityHelper.setSecondsOnFireBy(target, 5, entity);
         this.dealDamageComponent.hurtTarget(entity, target, conductivity * 3.0F);
      }

      return true;
   }

   static {
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Volt Amaru Reach Modifier", 4.8, Operation.ADDITION);
      KNOCKBACK_RESISTANCE = new AbilityAttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID, INSTANCE, "Volt Amaru Knockback Resistance Modifier", (double)2.0F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Volt Amaru Toughness Modifier", (double)2.0F, Operation.ADDITION);
      HEALTH_BOOST = new AbilityAttributeModifier(AttributeHelper.MORPH_HEALTH_UUID, INSTANCE, "Volt Amaru Health Modifier", (double)20.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Volt Amaru Strength Modifier", (double)12.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Volt Amaru Attack Speed Modifier", (double)1.0F, Operation.MULTIPLY_BASE);
      JUMP_BOOST = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Volt Amaru Jump Modifier", (double)5.0F, Operation.MULTIPLY_BASE);
      FALL_RESISTENCE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_FALL_RESISTANCE_UUID, INSTANCE, "Volt Amaru Jump Resitance Modifier", (double)10.0F, Operation.ADDITION);
      OVERLAY = (new AbilityOverlay.Builder()).setColor(COLOR).setRenderType(AbilityOverlay.RenderType.ENERGY).build();
      OVERLAY_ALT = (new AbilityOverlay.Builder()).setColor(ElThorAbility.BLUE_THUNDER).setRenderType(AbilityOverlay.RenderType.ENERGY).build();
   }
}
