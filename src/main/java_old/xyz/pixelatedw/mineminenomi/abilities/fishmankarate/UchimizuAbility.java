package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.UchimizuProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class UchimizuAbility extends Ability {
   private static final float COOLDOWN = 80.0F;
   private static final float WATER_DAMAGE_MUL = 2.0F;
   private static final int DORIKI = 800;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<UchimizuAbility>> INSTANCE = ModRegistry.registerAbility("uchimizu", "Uchimizu", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user hurls big and fast water droplets at the opponent", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, UchimizuAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F), FishmanKarateHelper.getWaterBuffedProjectileDamageStat(2.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.WATER).setNodeFactories(UchimizuAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(100, this::onRepeaterTriggerEvent).addStopEvent(100, this::onRepeaterStopEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public UchimizuAbility(AbilityCore<UchimizuAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.projectileComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.startContinuity(entity);
      }
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, 8, 2);
   }

   private void onRepeaterTriggerEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 1.2F);
   }

   private void onRepeaterStopEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      this.cooldownComponent.startCooldown(entity, 80.0F);
   }

   private UchimizuProjectile createProjectile(LivingEntity entity) {
      UchimizuProjectile proj = new UchimizuProjectile(entity.m_9236_(), entity, this);
      if (FishmanKarateHelper.isInWater(entity)) {
         proj.setDamage(proj.getDamage() * 2.0F);
      }

      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(6.0F, -6.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.FISHMAN.get()).and(DorikiUnlockCondition.atLeast((double)800.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
