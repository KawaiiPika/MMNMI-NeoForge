package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class HitTriggerComponent extends AbilityComponent<IAbility> {
   private static final BiFunction<EventReduce, HitResult, EventReduce> ACCUMULATOR = (total, next) -> {
      if (next == HitTriggerComponent.HitResult.FAIL) {
         ++total.fails;
      } else if (next == HitTriggerComponent.HitResult.HIT) {
         ++total.hits;
      }

      return total;
   };
   private static final BinaryOperator<EventReduce> COMBINER = (a, b) -> {
      a.fails += b.fails;
      a.hits += b.hits;
      return a;
   };
   private final PriorityEventPool<ITryHitEvent> tryHitEvents = new PriorityEventPool<ITryHitEvent>();
   private final PriorityEventPool<IOnHitEvent> onHitEvents = new PriorityEventPool<IOnHitEvent>();
   private HitResult result;

   public HitTriggerComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.HIT_TRIGGER.get(), ability);
   }

   public HitTriggerComponent addTryHitEvent(ITryHitEvent event) {
      this.tryHitEvents.addEvent(100, event);
      return this;
   }

   public HitTriggerComponent addTryHitEvent(int priority, ITryHitEvent event) {
      this.tryHitEvents.addEvent(priority, event);
      return this;
   }

   public HitTriggerComponent addOnHitEvent(IOnHitEvent event) {
      this.onHitEvents.addEvent(100, event);
      return this;
   }

   public HitTriggerComponent addOnHitEvent(int priority, IOnHitEvent event) {
      this.onHitEvents.addEvent(priority, event);
      return this;
   }

   public HitResult getResult() {
      return this.result;
   }

   public void resetHitResult() {
      this.result = HitTriggerComponent.HitResult.PASS;
   }

   public HitResult tryHit(LivingEntity entity, LivingEntity target, DamageSource source) {
      this.ensureIsRegistered();
      if (this.tryHitEvents.getEventsStream().count() <= 0L) {
         this.result = HitTriggerComponent.HitResult.PASS;
      } else {
         Stream<ITryHitEvent> eventStream = this.tryHitEvents.getEventsStream();
         Stream<HitResult> hitResults = eventStream.map((event) -> event.tryHit(entity, target, source, this.getAbility()));
         EventReduce result = (EventReduce)hitResults.reduce(new EventReduce(), ACCUMULATOR, COMBINER);
         if (result.fails > 0) {
            this.result = HitTriggerComponent.HitResult.FAIL;
         } else if (result.hits > 0) {
            this.result = HitTriggerComponent.HitResult.HIT;
         } else {
            this.result = HitTriggerComponent.HitResult.PASS;
         }
      }

      return this.result;
   }

   public boolean onHit(LivingEntity entity, LivingEntity target, DamageSource source) {
      this.ensureIsRegistered();
      boolean isCancelled = false;
      if (this.result == HitTriggerComponent.HitResult.HIT) {
         isCancelled = this.onHitEvents.dispatchCancelable((event) -> !event.onHit(entity, target, source, this.getAbility()));
      }

      this.resetHitResult();
      return !isCancelled;
   }

   public static enum HitResult {
      HIT,
      PASS,
      FAIL;

      // $FF: synthetic method
      private static HitResult[] $values() {
         return new HitResult[]{HIT, PASS, FAIL};
      }
   }

   static class EventReduce {
      public int fails;
      public int hits;

      public String toString() {
         return "fails: " + this.fails + ", hits: " + this.hits;
      }
   }

   @FunctionalInterface
   public interface IOnHitEvent {
      boolean onHit(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4);
   }

   @FunctionalInterface
   public interface ITryHitEvent {
      HitResult tryHit(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4);
   }
}
