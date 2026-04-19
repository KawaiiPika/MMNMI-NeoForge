package xyz.pixelatedw.mineminenomi.api.entities.ai;

import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public abstract class AbilityWrapperGoal<E extends Mob, A extends IAbility> extends TickedGoal<E> {
   private AbilityCore<A> abilityCore;
   private A ability;
   private Optional<IAbilityData> props;
   private final Optional<StackComponent> stackComponent;
   private boolean stopOnCooldown = true;
   private boolean trackContinuity = false;
   private boolean trackCharging = false;

   public AbilityWrapperGoal(E entity, AbilityCore<A> core) {
      super(entity);
      this.props = AbilityCapability.get(entity);
      this.abilityCore = core;
      this.ability = core.getType().equals(AbilityType.ACTION) ? MobsHelper.unlockAndEquipAbility(entity, core) : MobsHelper.unlockAbility(entity, core);
      if (this.ability == null && this.props.isPresent()) {
         this.ability = ((IAbilityData)this.props.get()).getEquippedOrPassiveAbility(core);
      }

      if (this.ability == null) {
         throw new RuntimeException("Ability assigned is null !?");
      } else {
         this.ability.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> comp.addStartEvent(150, (e, a) -> this.trackContinuity = true));
         this.ability.getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).ifPresent((comp) -> comp.addStartEvent(150, (e, a) -> this.trackCharging = true));
         this.stackComponent = this.getAbility().<StackComponent>getComponent((AbilityComponentKey)ModAbilityComponents.STACK.get());
         if (this.stackComponent.isPresent()) {
            this.dontStopOnCooldown();
         }

      }
   }

   public AbilityWrapperGoal<E, A> dontStopOnCooldown() {
      this.stopOnCooldown = false;
      return this;
   }

   public boolean m_8036_() {
      if (this.entity.m_6084_() && !this.entity.m_6162_()) {
         if (this.entity.m_21023_((MobEffect)ModEffects.IN_EVENT.get())) {
            return false;
         } else if (this.entity.m_5448_() != null && this.entity.m_5448_() instanceof Monster) {
            return false;
         } else {
            for(MobEffectInstance effectInst : this.entity.m_21220_()) {
               if (effectInst.m_19544_() instanceof IBindHandsEffect && ((IBindHandsEffect)effectInst.m_19544_()).isBlockingSwings()) {
                  return false;
               }
            }

            if (!this.canUseWrapper()) {
               return false;
            } else if (this.ability.canUse(this.entity).isFail()) {
               return false;
            } else {
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public abstract boolean canUseWrapper();

   public boolean m_8045_() {
      if (!this.entity.m_6084_()) {
         return false;
      } else if (this.entity.m_21023_((MobEffect)ModEffects.IN_EVENT.get())) {
         return false;
      } else {
         boolean isDisabled = (Boolean)this.ability.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).map(DisableComponent::isDisabled).orElse(false);
         if (isDisabled) {
            return false;
         } else {
            if (this.stopOnCooldown) {
               boolean isOnCooldown = (Boolean)this.ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map(CooldownComponent::isOnCooldown).orElse(false);
               if (isOnCooldown) {
                  return false;
               }
            }

            if (this.trackContinuity) {
               Optional<ContinuousComponent> comp = this.ability.<ContinuousComponent>getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get());
               if (comp.isPresent()) {
                  boolean hasStopped = !((ContinuousComponent)comp.get()).isContinuous();
                  if (hasStopped) {
                     this.trackContinuity = false;
                     return false;
                  }
               }
            }

            if (this.stackComponent.isPresent() && ((StackComponent)this.stackComponent.get()).getStacks() <= 0) {
               return false;
            } else {
               if (!this.entity.m_9236_().f_46443_) {
                  ServerLevel serverLevel = (ServerLevel)this.entity.m_9236_();
                  Optional<ProtectedArea> area = ProtectedAreasData.get(serverLevel).getProtectedArea(this.entity.m_20183_().m_123341_(), this.entity.m_20183_().m_123342_(), this.entity.m_20183_().m_123343_());
                  if (area.isPresent() && !((ProtectedArea)area.get()).canUseAbility(this.getCore())) {
                     return false;
                  }
               }

               return this.canContinueToUseWrapper();
            }
         }
      }
   }

   public abstract boolean canContinueToUseWrapper();

   public final void m_8056_() {
      super.m_8056_();
      this.startWrapper();
      this.ability.use(this.entity);
      if (FGCommand.SHOW_NPC_ABILITY_LOG) {
         WyDebug.debug(WyDebug.ABILITY_LOG, String.format("%s started %s (%s) at tick %s", this.entity.m_7755_().getString(), this.ability.getDisplayName().getString(), this.getClass().getSimpleName(), this.entity.f_19797_));
      }

   }

   public abstract void startWrapper();

   public final void m_8037_() {
      this.tickWrapper();
      if (this.stackComponent.isPresent() && ((StackComponent)this.stackComponent.get()).getStacks() > 0 && this.getAbility().canUse(this.entity).isSuccess()) {
         this.getAbility().use(this.entity);
         if (FGCommand.SHOW_NPC_ABILITY_LOG) {
            WyDebug.debug(WyDebug.ABILITY_LOG, String.format("%s reused %s (%s) at tick %s", this.entity.m_7755_().getString(), this.ability.getDisplayName().getString(), this.getClass().getSimpleName(), this.entity.f_19797_));
         }
      }

      super.m_8037_();
   }

   public abstract void tickWrapper();

   public final void m_8041_() {
      super.m_8041_();
      this.stopWrapper();
      this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> comp.stopContinuity(this.entity));
      if (FGCommand.SHOW_NPC_ABILITY_LOG) {
         WyDebug.debug(WyDebug.ABILITY_LOG, String.format("%s stopped %s at tick %s", this.entity.m_7755_().getString(), this.ability.getDisplayName().getString(), this.entity.f_19797_));
      }

   }

   public abstract void stopWrapper();

   public A getAbility() {
      return this.ability;
   }

   public AbilityCore<A> getCore() {
      return this.abilityCore;
   }

   public Optional<IAbilityData> getProps() {
      return this.props;
   }
}
