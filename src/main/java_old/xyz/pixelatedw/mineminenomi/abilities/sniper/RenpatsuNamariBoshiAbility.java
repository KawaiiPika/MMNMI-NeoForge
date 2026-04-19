package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.RenpatsuNamariBoshiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class RenpatsuNamariBoshiAbility extends Ability {
   private static final float COOLDOWN = 120.0F;
   public static final RegistryObject<AbilityCore<RenpatsuNamariBoshiAbility>> INSTANCE = ModRegistry.registerAbility("renpatsu_namari_boshi", "Renpatsu Namari Boshi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires a barrage of exploding pellets", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, RenpatsuNamariBoshiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(120.0F), ContinuousComponent.getTooltip()).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.EXPLOSION).setSourceType(SourceType.BULLET).setNodeFactories(RenpatsuNamariBoshiAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = new ContinuousComponent(this);
   private final BowTriggerComponent bowTriggerComponent = (new BowTriggerComponent(this)).addShootEvent(this::shoot);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(100, this::triggerRepeaterEvent).addStopEvent(100, this::stopRepeaterEvent);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public RenpatsuNamariBoshiAbility(AbilityCore<RenpatsuNamariBoshiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.bowTriggerComponent, this.projectileComponent, this.repeaterComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   public void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   public boolean shoot(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.start(entity, 10, 3);
         return true;
      } else {
         return false;
      }
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      boolean isHitScan = HissatsuAbility.checkHitScan(entity);
      this.projectileComponent.setHitScan(isHitScan);
      this.projectileComponent.shoot(entity, 4.0F, 10.0F);
   }

   private void stopRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      this.cooldownComponent.startCooldown(entity, 120.0F);
   }

   public RenpatsuNamariBoshiProjectile createProjectile(LivingEntity entity) {
      RenpatsuNamariBoshiProjectile proj = new RenpatsuNamariBoshiProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-7.0F, -7.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.SNIPER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)HiNoToriBoshiAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
