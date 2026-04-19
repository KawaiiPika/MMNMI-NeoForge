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
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.PackOfSharksProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PackOfSharksAbility extends Ability {
   private static final float COOLDOWN = 300.0F;
   private static final float WATER_DAMAGE_MUL = 1.25F;
   private static final int DORIKI = 5000;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<PackOfSharksAbility>> INSTANCE = ModRegistry.registerAbility("pack_of_sharks", "Pack of Sharks", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user fires densely compressed shark-shaped waterbullets at the opponent", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, PackOfSharksAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), FishmanKarateHelper.getWaterBuffedProjectileDamageStat(1.25F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.WATER).setNodeFactories(PackOfSharksAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(100, this::onRepeaterTriggerEvent).addStopEvent(100, this::onRepeaterStopEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public PackOfSharksAbility(AbilityCore<PackOfSharksAbility> core) {
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
      this.repeaterComponent.start(entity, 5, 3);
   }

   private void onRepeaterTriggerEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity);
   }

   private void onRepeaterStopEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private PackOfSharksProjectile createProjectile(LivingEntity entity) {
      PackOfSharksProjectile proj = new PackOfSharksProjectile(entity.m_9236_(), entity, this);
      if (FishmanKarateHelper.isInWater(entity)) {
         proj.setDamage(proj.getDamage() * 1.25F);
      }

      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(5.0F, -8.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.FISHMAN.get()).and(DorikiUnlockCondition.atLeast((double)5000.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)UchimizuAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
