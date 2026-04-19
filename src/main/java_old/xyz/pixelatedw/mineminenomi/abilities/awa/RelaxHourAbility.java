package xyz.pixelatedw.mineminenomi.abilities.awa;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.awa.RelaxHourProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class RelaxHourAbility extends Ability {
   private static final float COOLDOWN = 80.0F;
   public static final RegistryObject<AbilityCore<RelaxHourAbility>> INSTANCE = ModRegistry.registerAbility("relax_hour", "Relax Hour", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires a barrage of cleaning bubbles, leaving their targets weakened and immobile", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, RelaxHourAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.WATER).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::onRepeaterTrigger).addStopEvent(this::onRepeaterStop);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public RelaxHourAbility(AbilityCore<RelaxHourAbility> core) {
      super(core);
      super.addComponents(this.continuousComponent, this.repeaterComponent, this.projectileComponent);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, 5, 5);
   }

   private void onRepeaterTrigger(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 1.0F, 1.0F);
   }

   private void onRepeaterStop(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      super.cooldownComponent.startCooldown(entity, 80.0F);
   }

   private RelaxHourProjectile createProjectile(LivingEntity entity) {
      RelaxHourProjectile proj = new RelaxHourProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
