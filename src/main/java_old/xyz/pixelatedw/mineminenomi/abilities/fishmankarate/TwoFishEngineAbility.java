package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TwoFishEngineAbility extends Ability {
   private static final float HOLD_TIME = 200.0F;
   private static final float MIN_COOLDOWN = 100.0F;
   private static final float MAX_COOLDOWN = 300.0F;
   private static final int DORIKI = 2000;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<TwoFishEngineAbility>> INSTANCE = ModRegistry.registerAbility("two_fish_engine", "Two Fish Engine", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Increases the user's swimming speed", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, TwoFishEngineAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 300.0F), ContinuousComponent.getTooltip(200.0F), ChangeStatsComponent.getTooltip()).setNodeFactories(TwoFishEngineAbility::createNode).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SWIN_SPEED;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addEndEvent(100, this::onEndContinuousEvent);
   private final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);

   public TwoFishEngineAbility(AbilityCore<TwoFishEngineAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.statsComponent});
      this.statsComponent.addAttributeModifier((Attribute)ForgeMod.SWIM_SPEED.get(), SWIN_SPEED, (entity) -> this.continuousComponent.isContinuous());
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 200.0F);
   }

   private void onEndContinuousEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 100.0F + this.continuousComponent.getContinueTime());
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(7.0F, -8.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.FISHMAN.get()).and(DorikiUnlockCondition.atLeast((double)2000.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)PackOfSharksAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)SamehadaShoteiAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }

   static {
      SWIN_SPEED = new AbilityAttributeModifier(UUID.fromString("c6ad4347-b287-4bd5-b6c9-1533543fd15c"), INSTANCE, "Fishman Speed Modifier", (double)1.75F, Operation.MULTIPLY_TOTAL);
   }
}
