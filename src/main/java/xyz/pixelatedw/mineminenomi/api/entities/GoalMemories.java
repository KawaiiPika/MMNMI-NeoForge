package xyz.pixelatedw.mineminenomi.api.entities;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class GoalMemories {
   private final Map<MemoryModuleType<?>, ExpirableValue<?>> memories = Maps.newHashMap();

   public void tick() {
      Iterator<Map.Entry<MemoryModuleType<?>, ExpirableValue<?>>> it = this.memories.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry<MemoryModuleType<?>, ExpirableValue<?>> entry = it.next();
         ExpirableValue<?> memory = entry.getValue();
         memory.tick();
         if (memory.hasExpired()) {
            it.remove();
         }
      }

   }

   public <U> Optional<U> getMemory(MemoryModuleType<U> type) {
      try {
         return Optional.of((U) ((ExpirableValue)this.memories.get(type)).getValue());
      } catch (NullPointerException var3) {
         return Optional.empty();
      }
   }

   public boolean hasMemoryValue(MemoryModuleType<?> type) {
      return this.checkMemory(type);
   }

   public <U> void eraseMemory(MemoryModuleType<U> type) {
      this.memories.remove(type);
   }

   public <U> void setMemory(MemoryModuleType<U> memoryType, U memory) {
      this.setMemoryInternal(memoryType, ExpirableValue.of(memory));
   }

   public <U> void setMemoryWithExpiry(MemoryModuleType<U> memoryType, U memory, long timesToLive) {
      this.setMemoryInternal(memoryType, ExpirableValue.of(memory, timesToLive));
   }

   public boolean checkMemory(MemoryModuleType<?> memoryType) {
      ExpirableValue<?> memory = this.memories.get(memoryType);
      return memory != null;
   }

   public <U> boolean isMemoryValue(MemoryModuleType<U> memoryType, U memory) {
      return !this.hasMemoryValue(memoryType) ? false : this.getMemory(memoryType).filter((p_233704_1_) -> p_233704_1_.equals(memory)).isPresent();
   }

   private <U> void setMemoryInternal(MemoryModuleType<U> memoryType, ExpirableValue<?> memory) {
      if (this.memories.containsKey(memoryType)) {
         if (this.isEmptyCollection(memory.getValue())) {
            this.eraseMemory(memoryType);
         } else {
            this.memories.put(memoryType, memory);
         }
      } else {
         this.memories.put(memoryType, memory);
      }

   }

   private boolean isEmptyCollection(Object collection) {
      return collection instanceof Collection && ((Collection)collection).isEmpty();
   }
}
