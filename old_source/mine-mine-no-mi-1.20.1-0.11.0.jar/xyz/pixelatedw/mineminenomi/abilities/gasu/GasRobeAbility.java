package xyz.pixelatedw.mineminenomi.abilities.gasu;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gasu.GasRobeProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class GasRobeAbility extends Ability {
   private static final int COOLDOWN = 200;
   public static final RegistryObject<AbilityCore<GasRobeAbility>> INSTANCE = ModRegistry.registerAbility("gas_robe", "Gas Robe", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a cloud of poisonous gas at the opponent", new Object[]{ModWeapons.BLUE_SWORD}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GasRobeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.POISON).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::onRepeaterTrigger).addStopEvent(this::onRepeaterStop);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public GasRobeAbility(AbilityCore<GasRobeAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.animationComponent, this.projectileComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.animationComponent.start(entity, ModAnimations.POINT_RIGHT_ARM);
         this.repeaterComponent.start(entity, 6, 3);
      }
   }

   private void onRepeaterTrigger(LivingEntity entity, IAbility ability) {
      if (super.canUse(entity).isFail()) {
         this.repeaterComponent.stop(entity);
      }

      this.projectileComponent.shoot(entity, 2.5F, 1.5F);
   }

   private void onRepeaterStop(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      this.animationComponent.stop(entity);
      super.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private GasRobeProjectile createProjectile(LivingEntity entity) {
      GasRobeProjectile proj = new GasRobeProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
