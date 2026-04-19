package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;

public abstract class DropHitAbility extends Ability {
   protected final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(90, this::startContinuityEvent).addTickEvent(90, this::tickContinuityEvent).addEndEvent(90, this::endContinuityEvent);
   protected final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
   protected final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private boolean hasLanded = true;
   private boolean hasFallDamage = true;
   private boolean startsInLiquid = false;
   private boolean isCancelable = true;

   public DropHitAbility(AbilityCore<? extends DropHitAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.damageTakenComponent, this.hitTrackerComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   protected void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.continuousComponent.isContinuous() || this.isCancelable) {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hasLanded = false;
      this.hasFallDamage = false;
      this.startsInLiquid = entity.m_20069_();
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && !this.hasFallDamage && entity.m_20096_() && entity.m_9236_().m_46467_() > this.getLastUseGametime() + 10L) {
         this.hasFallDamage = true;
      }

      if (!this.startsInLiquid && entity.m_20069_() && !this.hasLanded) {
         this.hasLanded = true;
      }

      if (this.startsInLiquid && entity.m_20069_() && entity.m_20184_().f_82480_ <= (double)0.0F) {
         this.hasLanded = true;
      }

      if (entity.m_20096_() && this.continuousComponent.getContinueTime() > 10.0F && !this.hasLanded) {
         this.hasLanded = true;
      }

      if (this.hasLanded) {
         this.onLanding(entity);
         this.continuousComponent.stopContinuity(entity);
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
   }

   public abstract void onLanding(LivingEntity var1);

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.hasFallDamage && damageSource == entity.m_269291_().m_268989_()) {
         this.hasFallDamage = true;
         return 0.0F;
      } else {
         return damage;
      }
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128379_("hasFallDamage", this.hasFallDamage);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.hasFallDamage = nbt.m_128471_("hasFallDamage");
   }

   public void setLanded() {
      this.hasLanded = true;
   }

   public boolean hasLanded() {
      return this.hasLanded;
   }

   public void setCancelable(boolean isCancelable) {
      this.isCancelable = isCancelable;
   }
}
