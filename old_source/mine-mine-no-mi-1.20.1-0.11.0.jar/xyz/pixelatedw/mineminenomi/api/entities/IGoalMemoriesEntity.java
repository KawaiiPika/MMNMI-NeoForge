package xyz.pixelatedw.mineminenomi.api.entities;

import java.util.Optional;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public interface IGoalMemoriesEntity {
   GoalMemories getGoalMemories();

   default <U> Optional<U> getMemory(MemoryModuleType<U> type) {
      return this.getGoalMemories().<U>getMemory(type);
   }

   default boolean hasMemoryValue(MemoryModuleType<?> type) {
      return this.getGoalMemories().hasMemoryValue(type);
   }

   default <U> void eraseMemory(MemoryModuleType<U> type) {
      this.getGoalMemories().eraseMemory(type);
   }

   default <U> void setMemory(MemoryModuleType<U> memoryType, U memory) {
      this.getGoalMemories().setMemory(memoryType, memory);
   }

   default <U> void setMemoryWithExpiry(MemoryModuleType<U> memoryType, U memory, long timesToLive) {
      this.getGoalMemories().setMemoryWithExpiry(memoryType, memory, timesToLive);
   }
}
