package xyz.pixelatedw.mineminenomi.api.entities.ai;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;

public abstract class NPCPhase<E extends Mob> {
   private String name;
   private final E entity;
   private final Set<Pair<Integer, Goal>> availableGoals = Sets.newLinkedHashSet();
   private GoalSelector goals;

   public NPCPhase(String name, E entity) {
      this.name = name;
      this.entity = entity;
      this.goals = new GoalSelector(entity.m_9236_().m_46658_());
   }

   public void addGoal(int priority, Goal goal) {
      if (!this.availableGoals.stream().anyMatch((pair) -> ((Goal)pair.getValue()).equals(goal))) {
         this.availableGoals.add(ImmutablePair.of(priority, goal));
         this.goals.m_25352_(priority, goal);
      }
   }

   public void addGoals(NPCPhase<E> phase) {
      for(Pair<Integer, Goal> pair : phase.getGoalsSet()) {
         this.addGoal((Integer)pair.getKey(), (Goal)pair.getValue());
      }

   }

   public final void startPhase() {
      this.start(this.entity);
   }

   public abstract void start(E var1);

   public final void stopPhase() {
      this.goals.m_25386_().forEach(WrappedGoal::m_8041_);
      this.stop(this.entity);
   }

   public abstract void stop(E var1);

   public final void tick() {
      int i = this.entity.m_9236_().m_7654_().m_129921_() + this.entity.m_19879_();
      if (i % 2 != 0 && this.entity.f_19797_ > 1) {
         this.goals.m_186081_(false);
      } else {
         this.goals.m_25373_();
      }

      this.doTick();
   }

   public abstract void doTick();

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.getName();
   }

   public <T extends IPhasesEntity> boolean isActive(T entity) {
      NPCPhase<?> currentPhase = entity.getPhaseManager().getCurrentPhase();
      return currentPhase == null ? false : currentPhase.equals(this);
   }

   public Set<Pair<Integer, Goal>> getGoalsSet() {
      return this.availableGoals;
   }
}
