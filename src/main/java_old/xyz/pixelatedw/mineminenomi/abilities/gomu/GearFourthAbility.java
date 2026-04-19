package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GearFourthAbility extends MorphAbility {
   private static final int HOLD_TIME = 1000;
   private static final int MIN_COOLDOWN = 100;
   private static final float MAX_COOLDOWN = 666.6667F;
   public static final RegistryObject<AbilityCore<GearFourthAbility>> INSTANCE = ModRegistry.registerAbility("gear_fourth", "Gear Fourth", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user inflates their muscle structure to tremendously increase the power of their attacks and also allows flight.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GearFourthAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredActiveAbilityTooltip(BusoshokuHakiFullBodyHardeningAbility.INSTANCE)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 666.6667F), ContinuousComponent.getTooltip(1000.0F), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;
   private static final AbilityOverlay OVERLAY;
   private final SkinOverlayComponent skinOverlayComponent;
   private final StackComponent stackComponent;
   public float speed;

   public GearFourthAbility(AbilityCore<GearFourthAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.stackComponent = (new StackComponent(this)).addStackChangeEvent(this::changeStackEvent);
      this.speed = 0.0F;
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent, this.stackComponent});
      this.addCanUseCheck((entity, ability) -> AbilityUseConditions.requiresActiveAbility(entity, ability, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
      this.addContinueUseCheck((entity, ability) -> AbilityUseConditions.requiresActiveAbility(entity, ability, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22285_, ARMOR_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER);
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addTickEvent(this::duringContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.stackComponent.setDefaultStacks(0);
      this.skinOverlayComponent.showAll(entity);
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         GomuGomuNoPistolAbility pistol = (GomuGomuNoPistolAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoPistolAbility.INSTANCE.get());
         if (pistol != null) {
            pistol.switchFourthGear(entity);
         }

         GomuGomuNoGatlingAbility gatling = (GomuGomuNoGatlingAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoGatlingAbility.INSTANCE.get());
         if (gatling != null) {
            gatling.switchFourthGear(entity);
         }

         GomuGomuNoBazookaAbility bazooka = (GomuGomuNoBazookaAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoBazookaAbility.INSTANCE.get());
         if (bazooka != null) {
            bazooka.switchFourthGear(entity);
         }

         PropelledFlightAbility flightAbility = (PropelledFlightAbility)props.getPassiveAbility((AbilityCore)GearFourthFlightAbility.INSTANCE.get());
         if (flightAbility != null && !flightAbility.isPaused()) {
            flightAbility.enableFlight((Player)entity);
         }

      }
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      float continueTime = this.continuousComponent.getContinueTime();
      if (continueTime % 10.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GEAR_SECOND.get(), entity, entity.m_20185_(), entity.m_20186_() + (double)1.0F, entity.m_20189_());
      }

      int stacks = this.stackComponent.getStacks();
      if ((stacks != 0 || !((double)continueTime > WyHelper.percentage((double)50.0F, (double)this.continuousComponent.getThresholdTime()))) && (stacks != 1 || !((double)continueTime > WyHelper.percentage((double)70.0F, (double)this.continuousComponent.getThresholdTime())))) {
         if (stacks == 2 && (double)continueTime > WyHelper.percentage((double)90.0F, (double)this.continuousComponent.getThresholdTime())) {
            this.stackComponent.addStacks(entity, ability, 1);
         }
      } else {
         this.stackComponent.addStacks(entity, ability, 1);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
      this.stackComponent.revertStacksToDefault(entity, ability);
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         GomuGomuNoPistolAbility pistol = (GomuGomuNoPistolAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoPistolAbility.INSTANCE.get());
         if (pistol != null) {
            pistol.switchNoGear(entity);
         }

         GomuGomuNoGatlingAbility gatling = (GomuGomuNoGatlingAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoGatlingAbility.INSTANCE.get());
         if (gatling != null) {
            gatling.switchNoGear(entity);
         }

         GomuGomuNoBazookaAbility bazooka = (GomuGomuNoBazookaAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoBazookaAbility.INSTANCE.get());
         if (bazooka != null) {
            bazooka.switchNoGear(entity);
         }

         PropelledFlightAbility flightAbility = (PropelledFlightAbility)props.getPassiveAbility((AbilityCore)GearFourthFlightAbility.INSTANCE.get());
         if (flightAbility != null) {
            flightAbility.disableFlight((Player)entity);
         }

         float cooldown = Math.max(100.0F, this.continuousComponent.getContinueTime() / 1.5F);
         this.cooldownComponent.startCooldown(entity, cooldown);
      }
   }

   private void changeStackEvent(LivingEntity entity, IAbility ability, int stacks) {
      if (stacks == 1) {
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19612_, 200, 0, true, true));
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 200, 0, true, true));
      } else if (stacks >= 2) {
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19612_, 400, 0, true, true));
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 400, 0, true, true));
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 400, 0, true, true));
         if (stacks > 2) {
            this.stackComponent.revertStacksToDefault(entity, ability);
            this.continuousComponent.stopContinuity(entity);
            this.disableComponent.startDisable(entity, 400.0F);
         }
      }

   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.GEAR_FOURTH.get();
   }

   public float getContinuityHoldTime() {
      return 1000.0F;
   }

   static {
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Gear Fourth Armor Modifier", (double)10.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Gear Fourth Attack Damage Modifier", (double)15.0F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Gear Fourth Toughness Modifier", (double)2.0F, Operation.ADDITION);
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.G4_OVERLAY).build();
   }
}
