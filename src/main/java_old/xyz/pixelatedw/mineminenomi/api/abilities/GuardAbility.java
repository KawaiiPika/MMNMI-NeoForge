package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public abstract class GuardAbility extends Ability {
   protected final ContinuousComponent continuousComponent = new ContinuousComponent(this, this.isParallel());
   protected final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);
   protected final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnHurtEvent(200, this::onHurtEvent);
   protected final PoolComponent poolComponent;
   protected final MobEffectComponent effectComponent;
   private float totalGuarded;

   public GuardAbility(AbilityCore<? extends GuardAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GUARD_ABILITY, new AbilityPool[0]);
      this.effectComponent = new MobEffectComponent(this);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.statsComponent, this.damageTakenComponent, this.poolComponent, this.effectComponent});
      this.addUseEvent(200, this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.clearTotalGuarded();
      this.continuousComponent.triggerContinuity(entity, this.getHoldTime());
   }

   private float onHurtEvent(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (this.continuousComponent.isContinuous()) {
         boolean canUse = !this.getContinueUseChecksStream().anyMatch((p) -> p.canUse(entity, ability).isFail());
         if (!canUse) {
            return damage;
         } else {
            GuardValue guard = this.getGuard(entity);
            boolean breaks = false;
            if (this.canGuardBreak(entity)) {
               if (guard.breakKind == GuardAbility.GuardBreakKind.PER_HIT) {
                  breaks = guard.breakLimit > 0.0F && damage > guard.breakLimit;
               } else if (guard.breakKind == GuardAbility.GuardBreakKind.TOTAL) {
                  breaks = guard.breakLimit > 0.0F && this.totalGuarded > guard.breakLimit;
                  this.totalGuarded += damage;
               }
            }

            float finalDamage = damage;
            if (!breaks) {
               float var10000;
               switch (guard.kind) {
                  case FIXED_AMOUNT -> var10000 = guard.value;
                  case PERCENTAGE -> var10000 = damage * guard.value;
                  default -> var10000 = 0.0F;
               }

               float reduce = var10000;
               finalDamage = damage - reduce;
               this.onGuard(entity, damageSource, damage, finalDamage);
            }

            finalDamage = Math.max(finalDamage, 0.0F);
            return finalDamage;
         }
      } else {
         return damage;
      }
   }

   public void clearTotalGuarded() {
      this.totalGuarded = 0.0F;
   }

   public void blockMovement(LivingEntity entity) {
      this.effectComponent.applyEffect(entity, (MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), -1, 0);
   }

   public void removeMovementBlock(LivingEntity entity) {
      this.effectComponent.removeEffect(entity, (MobEffect)ModEffects.MOVEMENT_BLOCKED.get());
   }

   public abstract void onGuard(LivingEntity var1, DamageSource var2, float var3, float var4);

   public abstract GuardValue getGuard(LivingEntity var1);

   public float getHoldTime() {
      return -1.0F;
   }

   public boolean isParallel() {
      return true;
   }

   public boolean canGuardBreak(LivingEntity entity) {
      return this.getGuard(entity).breakLimit > 0.0F;
   }

   public static class GuardValue {
      private GuardKind kind;
      private GuardBreakKind breakKind;
      private float value;
      private float breakLimit;

      private GuardValue(GuardKind kind, float value) {
         this.kind = GuardAbility.GuardKind.FIXED_AMOUNT;
         this.breakKind = GuardAbility.GuardBreakKind.PER_HIT;
         this.kind = kind;
         this.value = value;
      }

      private GuardValue(GuardKind kind, float value, GuardBreakKind breakKind, float breakLimit) {
         this.kind = GuardAbility.GuardKind.FIXED_AMOUNT;
         this.breakKind = GuardAbility.GuardBreakKind.PER_HIT;
         this.kind = kind;
         this.value = value;
         this.breakKind = breakKind;
         this.breakLimit = breakLimit;
      }

      public static GuardValue fixedAmount(float value) {
         return new GuardValue(GuardAbility.GuardKind.FIXED_AMOUNT, value);
      }

      public static GuardValue fixedAmount(float value, GuardBreakKind breakKind, float breakLimit) {
         return new GuardValue(GuardAbility.GuardKind.FIXED_AMOUNT, value, breakKind, breakLimit);
      }

      public static GuardValue percentage(float percentage) {
         float value = (float)Mth.m_14008_((double)percentage, (double)0.0F, (double)1.0F);
         return new GuardValue(GuardAbility.GuardKind.PERCENTAGE, value);
      }

      public static GuardValue percentage(float percentage, GuardBreakKind breakKind, float breakLimit) {
         float value = (float)Mth.m_14008_((double)percentage, (double)0.0F, (double)1.0F);
         return new GuardValue(GuardAbility.GuardKind.PERCENTAGE, value, breakKind, breakLimit);
      }
   }

   public static enum GuardKind {
      FIXED_AMOUNT,
      PERCENTAGE;

      // $FF: synthetic method
      private static GuardKind[] $values() {
         return new GuardKind[]{FIXED_AMOUNT, PERCENTAGE};
      }
   }

   public static enum GuardBreakKind {
      PER_HIT,
      TOTAL;

      // $FF: synthetic method
      private static GuardBreakKind[] $values() {
         return new GuardBreakKind[]{PER_HIT, TOTAL};
      }
   }
}
