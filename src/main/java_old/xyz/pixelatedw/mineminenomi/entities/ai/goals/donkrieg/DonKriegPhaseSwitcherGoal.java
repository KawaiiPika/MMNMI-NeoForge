package xyz.pixelatedw.mineminenomi.entities.ai.goals.donkrieg;

import xyz.pixelatedw.mineminenomi.abilities.MH5Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.DonKriegEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class DonKriegPhaseSwitcherGoal extends TickedGoal<DonKriegEntity> {
   private static final float DAISENSO_HP_THRESHOLD = 70.0F;
   private final IAbilityData abilityData;
   private final float mh5HPThreshold;

   public DonKriegPhaseSwitcherGoal(DonKriegEntity entity) {
      super(entity);
      this.abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      this.mh5HPThreshold = entity.isDifficultyHardOrAbove() ? 70.0F : 50.0F;
   }

   public boolean m_8036_() {
      if (this.abilityData == null) {
         return false;
      } else if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!GoalHelper.canSee(this.entity, ((DonKriegEntity)this.entity).m_5448_())) {
         return false;
      } else if ((((DonKriegEntity)this.entity).hasFistPhaseActive() || ((DonKriegEntity)this.entity).hasMH5PhaseActive()) && this.trySwitchToDaisensoPhase()) {
         ((DonKriegEntity)this.entity).startDaisensoPhase();
         return true;
      } else if ((((DonKriegEntity)this.entity).hasFistPhaseActive() || ((DonKriegEntity)this.entity).hasDaisensoPhaseActive()) && this.trySwitchToMH5Phase()) {
         ((DonKriegEntity)this.entity).startMH5Phase();
         return true;
      } else {
         return false;
      }
   }

   private boolean trySwitchToDaisensoPhase() {
      if (((DonKriegEntity)this.entity).hasMH5PhaseActive()) {
         MH5Ability mh5Ability = (MH5Ability)this.abilityData.getEquippedAbility((AbilityCore)MH5Ability.INSTANCE.get());
         if (mh5Ability != null) {
            boolean isCharging = (Boolean)mh5Ability.getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).map((comp) -> comp.isCharging()).orElse(false);
            if (isCharging) {
               return false;
            }

            boolean isOnCooldown = (Boolean)mh5Ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map((comp) -> comp.isOnCooldown()).orElse(false);
            if (isOnCooldown) {
               return true;
            }

            return false;
         }
      } else if (GoalHelper.hasHealthAbovePercentage(this.entity, (double)70.0F)) {
         return false;
      }

      return true;
   }

   private boolean trySwitchToMH5Phase() {
      if (GoalHelper.hasHealthAbovePercentage(this.entity, (double)this.mh5HPThreshold)) {
         return false;
      } else {
         MH5Ability mh5Ability = (MH5Ability)this.abilityData.getEquippedAbility((AbilityCore)MH5Ability.INSTANCE.get());
         if (mh5Ability != null) {
            boolean isOnCooldown = (Boolean)mh5Ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map((comp) -> comp.isOnCooldown()).orElse(false);
            if (isOnCooldown) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
   }
}
