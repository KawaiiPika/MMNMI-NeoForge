package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class DamageTakenComponent extends AbilityComponent<IAbility> {
   private final PriorityEventPool<IGetHitEvent> onAttackEvents = new PriorityEventPool<IGetHitEvent>();
   private final PriorityEventPool<IGetHitEvent> onHurtEvents = new PriorityEventPool<IGetHitEvent>();
   private final PriorityEventPool<IGetHitEvent> onDamageEvents = new PriorityEventPool<IGetHitEvent>();

   public DamageTakenComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.DAMAGE_TAKEN.get(), ability);
   }

   public DamageTakenComponent addOnAttackEvent(IGetHitEvent event) {
      this.onAttackEvents.addEvent(100, event);
      return this;
   }

   public DamageTakenComponent addOnAttackEvent(int priority, IGetHitEvent event) {
      this.onAttackEvents.addEvent(priority, event);
      return this;
   }

   public DamageTakenComponent addOnHurtEvent(IGetHitEvent event) {
      this.onHurtEvents.addEvent(100, event);
      return this;
   }

   public DamageTakenComponent addOnHurtEvent(int priority, IGetHitEvent event) {
      this.onHurtEvents.addEvent(priority, event);
      return this;
   }

   public DamageTakenComponent addOnDamageEvent(IGetHitEvent event) {
      this.onDamageEvents.addEvent(100, event);
      return this;
   }

   public DamageTakenComponent addOnDamageEvent(int priority, IGetHitEvent event) {
      this.onDamageEvents.addEvent(priority, event);
      return this;
   }

   public float checkDamageTaken(LivingEntity entity, DamageSource damageSource, float damage, DamageState state) {
      this.ensureIsRegistered();
      if (!this.getAbility().isAbilityDisabled() && !this.getAbility().isAbilityPaused()) {
         List<IGetHitEvent> events = new ArrayList();
         switch (state) {
            case ATTACK -> events = this.onAttackEvents.getEventsStream().toList();
            case HURT -> events = this.onHurtEvents.getEventsStream().toList();
            case DAMAGE -> events = this.onDamageEvents.getEventsStream().toList();
         }

         for(IGetHitEvent event : events) {
            damage = event.damageCheck(entity, this.getAbility(), damageSource, damage);
         }

         return damage;
      } else {
         return damage;
      }
   }

   public static enum DamageState {
      ATTACK,
      HURT,
      DAMAGE;

      // $FF: synthetic method
      private static DamageState[] $values() {
         return new DamageState[]{ATTACK, HURT, DAMAGE};
      }
   }

   @FunctionalInterface
   public interface IGetHitEvent {
      float damageCheck(LivingEntity var1, IAbility var2, DamageSource var3, float var4);
   }
}
