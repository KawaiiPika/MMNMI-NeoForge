package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class PauseTickComponent extends AbilityComponent<IAbility> {
   private final PriorityEventPool<IPauseEvent> pauseEvents = new PriorityEventPool<IPauseEvent>();
   private final PriorityEventPool<IResumeEvent> resumeEvents = new PriorityEventPool<IResumeEvent>();
   private boolean isPaused;

   public PauseTickComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.PAUSE_TICK.get(), ability);
   }

   public void setPause(LivingEntity entity, boolean isPaused) {
      this.ensureIsRegistered();
      this.isPaused = isPaused;
      if (this.isPaused) {
         this.pauseEvents.dispatch((event) -> event.pause(entity, this.getAbility()));
      } else {
         this.resumeEvents.dispatch((event) -> event.resume(entity, this.getAbility()));
      }

   }

   public boolean isPaused() {
      return this.isPaused;
   }

   public PauseTickComponent addPauseEvent(int priority, IPauseEvent event) {
      this.pauseEvents.addEvent(priority, event);
      return this;
   }

   public PauseTickComponent addResumeEvent(int priority, IResumeEvent event) {
      this.resumeEvents.addEvent(priority, event);
      return this;
   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      nbt.m_128379_("isPaused", this.isPaused);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);
      this.isPaused = nbt.m_128471_("isPaused");
   }

   @FunctionalInterface
   public interface IPauseEvent {
      void pause(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IResumeEvent {
      void resume(LivingEntity var1, IAbility var2);
   }
}
