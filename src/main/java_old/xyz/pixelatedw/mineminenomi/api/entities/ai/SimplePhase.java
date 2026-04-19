package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.entity.Mob;

public class SimplePhase<E extends Mob> extends NPCPhase<E> {
   private IStartPhaseEvent<E> startPhaseEvent;
   private IStopPhaseEvent<E> stopPhaseEvent;

   public SimplePhase(String name, E entity) {
      super(name, entity);
   }

   public SimplePhase(String name, E entity, IStartPhaseEvent<E> startPhaseEvent) {
      super(name, entity);
      this.startPhaseEvent = startPhaseEvent;
   }

   public SimplePhase(String name, E entity, IStartPhaseEvent<E> startPhaseEvent, IStopPhaseEvent<E> stopPhaseEvent) {
      super(name, entity);
      this.startPhaseEvent = startPhaseEvent;
      this.stopPhaseEvent = stopPhaseEvent;
   }

   public void start(E entity) {
      if (this.startPhaseEvent != null) {
         this.startPhaseEvent.startPhase(entity);
      }

   }

   public void stop(E entity) {
      if (this.stopPhaseEvent != null) {
         this.stopPhaseEvent.stopPhase(entity);
      }

   }

   public void doTick() {
   }

   @FunctionalInterface
   public interface IStartPhaseEvent<E extends Mob> {
      void startPhase(E var1);
   }

   @FunctionalInterface
   public interface IStopPhaseEvent<E extends Mob> {
      void stopPhase(E var1);
   }
}
