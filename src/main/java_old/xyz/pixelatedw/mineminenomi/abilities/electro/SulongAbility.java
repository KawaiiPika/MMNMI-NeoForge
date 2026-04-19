package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.awt.Color;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SulongAbility extends Ability {
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 1200;
   private static final Color COLOR = WyHelper.hexToRGB("#B0E9F255");
   private static final int DORIKI = 1200;
   public static final RegistryObject<AbilityCore<SulongAbility>> INSTANCE = ModRegistry.registerAbility("sulong", "Sulong", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user reveals their true power during the full moon, enhancing their physical and electrical power. While active %s stacks are not consumed.", new Object[]{MentionHelper.mentionAbility((AbilityCore)EleclawAbility.INSTANCE.get())}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, SulongAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 1200.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setNodeFactories(SulongAbility::createNode).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier JUMP_MODIFIER;
   private static final AbilityAttributeModifier FALL_RESISTANCE;
   private static final AbilityAttributeModifier STEP_HEIGHT;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::tickContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);
   private final SkinOverlayComponent skinOverlayComponent;

   public SulongAbility(AbilityCore<SulongAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.statsComponent, this.skinOverlayComponent});
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier((Attribute)ModAttributes.PUNCH_DAMAGE.get(), STRENGTH_MODIFIER);
      this.statsComponent.addAttributeModifier((Attribute)ModAttributes.JUMP_HEIGHT.get(), JUMP_MODIFIER);
      this.statsComponent.addAttributeModifier((Attribute)ModAttributes.FALL_RESISTANCE.get(), FALL_RESISTANCE);
      this.statsComponent.addAttributeModifier((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get(), STEP_HEIGHT);
      this.addCanUseCheck(ElectroHelper::canTransformInSulong);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.statsComponent.applyModifiers(entity);
      this.skinOverlayComponent.showAll(entity);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && ElectroHelper.canTransformInSulong(entity, ability).isFail()) {
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.statsComponent.removeModifiers(entity);
      this.skinOverlayComponent.hideAll(entity);
      float cooldown = Math.max(100.0F, this.continuousComponent.getContinueTime() * 2.0F);
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(7.0F, 6.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.MINK.get()).and(DorikiUnlockCondition.atLeast((double)1200.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)EleclawAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.BODY).setColor(COLOR).build();
      SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("e158d542-5644-4921-9d5f-895f0b0a164c"), INSTANCE, "Sulong Speed Modifier", (double)1.5F, Operation.MULTIPLY_BASE);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(UUID.fromString("00bd47ca-63b1-4040-b942-d9857231c9da"), INSTANCE, "Sulong Damage Modifier", (double)10.0F, Operation.ADDITION);
      JUMP_MODIFIER = new AbilityAttributeModifier(UUID.fromString("00bd47ca-63b1-4040-b942-d9857231c9da"), INSTANCE, "Sulong Jump Modifier", 1.05, Operation.ADDITION);
      FALL_RESISTANCE = new AbilityAttributeModifier(UUID.fromString("00bd47ca-63b1-4040-b942-d9857231c9da"), INSTANCE, "Sulong Fall Resistance Modifier", (double)6.25F, Operation.ADDITION);
      STEP_HEIGHT = new AbilityAttributeModifier(UUID.fromString("eab680cd-a6dc-438a-99d8-46f9eb53a950"), INSTANCE, "Sulong Step Height Modifier", (double)1.0F, Operation.ADDITION);
   }
}
