package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DiableJambeAbility extends Ability {
   private static final float HOLD_TIME = 600.0F;
   private static final float MIN_COOLDOWN = 80.0F;
   private static final float MAX_COOLDOWN = 680.0F;
   public static final RegistryObject<AbilityCore<DiableJambeAbility>> INSTANCE = ModRegistry.registerAbility("diable_jambe", "Diable Jambe", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user heats up their leg, dealing additional damage and shortly setting the target on fire (Enhances all Blackleg attacks)", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, DiableJambeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F, 680.0F), ContinuousComponent.getTooltip(600.0F), ChangeStatsComponent.getTooltip()).setNodeFactories(DiableJambeAbility::createNode).setSourceElement(SourceElement.FIRE).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private static final AbilityAttributeModifier DIABLE_JAMBE_STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier DIABLE_JAMBE_ATTACK_SPEED_MODIFIER;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(100, this::onContinuityStartEvent).addTickEvent(100, this::onContinuityTickEvent).addEndEvent(100, this::onContinuityEndEvent);
   private final SkinOverlayComponent skinOverlayComponent;
   private final ChangeStatsComponent statsComponent;
   private boolean frozen;

   public DiableJambeAbility(AbilityCore<DiableJambeAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.statsComponent = new ChangeStatsComponent(this);
      this.frozen = false;
      Predicate<LivingEntity> isContinuityActive = (entity) -> this.continuousComponent.isContinuous();
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, DIABLE_JAMBE_ATTACK_SPEED_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, DIABLE_JAMBE_STRENGTH_MODIFIER, isContinuityActive);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.skinOverlayComponent, this.statsComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 600.0F);
   }

   private void onContinuityStartEvent(LivingEntity entity, IAbility ability) {
      this.frozen = false;
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityProps != null) {
         ExtraHachisAbility extraHachis = (ExtraHachisAbility)abilityProps.getEquippedAbility((AbilityCore)ExtraHachisAbility.INSTANCE.get());
         if (extraHachis != null) {
            extraHachis.getComponent((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get()).ifPresent((component) -> component.setMode(entity, ExtraHachisAbility.Mode.POELE_A_FRIRE));
         }

      }
   }

   private void onContinuityTickEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_21023_((MobEffect)ModEffects.FROZEN.get())) {
         this.frozen = true;
         AbilityHelper.reduceEffect(entity.m_21124_((MobEffect)ModEffects.FROZEN.get()), 1.1);
         this.continuousComponent.stopContinuity(entity);
      } else if (entity.m_21023_((MobEffect)ModEffects.FROSTBITE.get())) {
         this.frozen = true;
         AbilityHelper.reduceEffect(entity.m_21124_((MobEffect)ModEffects.FROSTBITE.get()), (double)1.5F);
         this.continuousComponent.stopContinuity(entity);
      } else {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DIABLE_JAMBE.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }
   }

   private void onContinuityEndEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, (this.frozen ? 160.0F : 80.0F) + this.continuousComponent.getContinueTime());
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityProps != null) {
         ExtraHachisAbility extraHachis = (ExtraHachisAbility)abilityProps.getEquippedAbility((AbilityCore)ExtraHachisAbility.INSTANCE.get());
         if (extraHachis != null) {
            extraHachis.getComponent((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get()).ifPresent((component) -> component.revertToDefault(entity));
         }

      }
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-7.0F, 12.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BLACK_LEG.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)PartyTableKickCourseAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)ExtraHachisAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.HOT_BOILING_SPECIAL_ARM).setColor("#FFBB44AA").build();
      DIABLE_JAMBE_STRENGTH_MODIFIER = new AbilityAttributeModifier(UUID.fromString("e3ae074c-40a9-49ff-aa3b-7cc9b98ddc2d"), INSTANCE, "Diable Jambe Attack Multiplier", (double)4.0F, Operation.ADDITION);
      DIABLE_JAMBE_ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("a984413a-7459-4989-8362-7c46a2663f0e"), INSTANCE, "Diable Jambe Speed Multiplier", (double)0.4F, Operation.ADDITION);
   }
}
