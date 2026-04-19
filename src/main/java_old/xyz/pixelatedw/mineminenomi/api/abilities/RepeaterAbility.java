package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public abstract class RepeaterAbility extends Ability {
   protected final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(200, this::onContinuityStart);
   protected final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(200, this::onRepeaterTrigger).addStopEvent(200, this::onRepeaterStop);
   protected final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::getProjectileFactory);
   private IOnTriggerShootEvent customShootLogic = null;

   public RepeaterAbility(AbilityCore<? extends RepeaterAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.projectileComponent});
      this.addUseEvent(200, this::onUseEvent);
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
         this.repeaterComponent.start(entity, this.getMaxTriggers(), this.getTriggerInterval());
      }
   }

   private void onRepeaterTrigger(LivingEntity entity, IAbility ability) {
      if (super.canUse(entity).isFail()) {
         this.repeaterComponent.stop(entity);
      }

      if (this.customShootLogic != null) {
         this.customShootLogic.customShootLogic(entity);
      } else {
         this.projectileComponent.shoot(entity, this.getProjectileSpeed(), this.getProjectileSpread());
      }

   }

   private void onRepeaterStop(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      this.cooldownComponent.startCooldown(entity, this.getRepeaterCooldown());
   }

   public void setCustomShootLogic(IOnTriggerShootEvent event) {
      this.customShootLogic = event;
   }

   public float getProjectileSpeed() {
      return 2.0F;
   }

   public float getProjectileSpread() {
      return 1.0F;
   }

   public abstract int getMaxTriggers();

   public abstract int getTriggerInterval();

   public abstract float getRepeaterCooldown();

   public abstract <P extends NuProjectileEntity> P getProjectileFactory(LivingEntity var1);

   @FunctionalInterface
   public interface IOnTriggerShootEvent {
      void customShootLogic(LivingEntity var1);
   }
}
