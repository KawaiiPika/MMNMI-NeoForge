package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.quest.SFinishObjectivePacket;

public abstract class Objective {
   private Quest parent;
   private Component title;
   private boolean isHidden;
   private boolean isOptional;
   private float maxProgress = 1.0F;
   private float progress;
   private Set<Objective> requirements = new HashSet();
   private final PriorityEventPool<IStartObjective> startEvents = new PriorityEventPool<IStartObjective>();
   private final PriorityEventPool<IRestartObjective> restartEvents = new PriorityEventPool<IRestartObjective>();
   private final PriorityEventPool<ICompleteObjective> completeEvents = new PriorityEventPool<ICompleteObjective>();

   public Objective(Quest parent, Component title) {
      this.parent = parent;
      this.title = title;
   }

   public void addStartEvent(int priority, IStartObjective event) {
      this.startEvents.addEvent(priority, event);
   }

   public void addRestartEvent(int priority, IRestartObjective event) {
      this.restartEvents.addEvent(priority, event);
   }

   public void addCompleteEvent(int priority, ICompleteObjective event) {
      this.completeEvents.addEvent(priority, event);
   }

   public void setProgress(Player player, float progress, boolean force) {
      if (!this.isLocked()) {
         if (!this.isComplete() || force) {
            double previousProgress = (double)this.getProgress();
            this.progress = Mth.m_14036_(progress, 0.0F, this.getMaxProgress());
            if (this.isComplete()) {
               if (this.parent.tryComplete(player)) {
                  this.parent.triggerCompleteEvents(player);
               } else {
                  this.completeEvents.dispatch((ev) -> ev.complete(player));
                  boolean shouldPing = previousProgress != (double)this.getProgress();
                  if (shouldPing) {
                     ModNetwork.sendTo(new SFinishObjectivePacket(), player);
                  }
               }
            }

         }
      }
   }

   public void alterProgress(Player player, float progress, boolean force) {
      this.setProgress(player, this.progress + progress, force);
   }

   public void alterProgress(Player player, float progress) {
      this.setProgress(player, this.progress + progress, false);
   }

   public float getProgress() {
      return this.progress;
   }

   public void setMaxProgress(float progress) {
      this.maxProgress = progress;
   }

   public float getMaxProgress() {
      return this.maxProgress;
   }

   public boolean isComplete() {
      return this.progress >= this.maxProgress;
   }

   public Objective addRequirements(Objective... objectives) {
      for(Objective obj : objectives) {
         this.addRequirement(obj);
      }

      return this;
   }

   public Objective addRequirement(Objective objective) {
      if (!this.requirements.contains(objective)) {
         this.requirements.add(objective);
      }

      return this;
   }

   public Objective setOptional() {
      this.isOptional = true;
      return this;
   }

   public boolean isOptional() {
      return this.isOptional;
   }

   public Objective markHidden() {
      this.isHidden = true;
      return this;
   }

   public boolean isHidden() {
      return this.isHidden;
   }

   public boolean isLocked() {
      if (this.requirements.size() <= 0) {
         return false;
      } else {
         return !this.requirements.stream().allMatch((o) -> !o.isOptional() && o.isComplete());
      }
   }

   public boolean hasEvents() {
      return this.startEvents.countEventsInPool() > 0L;
   }

   public Component getLocalizedTitle() {
      return this.title;
   }

   public CompoundTag save() {
      CompoundTag nbt = new CompoundTag();
      nbt.m_128379_("isHidden", this.isHidden);
      nbt.m_128350_("progress", this.progress);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      this.isHidden = nbt.m_128471_("isHidden");
      this.progress = nbt.m_128457_("progress");
   }

   @FunctionalInterface
   public interface ICompleteObjective {
      void complete(Player var1);
   }

   @FunctionalInterface
   public interface IRestartObjective {
      boolean restart(Player var1);
   }

   @FunctionalInterface
   public interface IStartObjective {
      void start(Player var1);
   }
}
