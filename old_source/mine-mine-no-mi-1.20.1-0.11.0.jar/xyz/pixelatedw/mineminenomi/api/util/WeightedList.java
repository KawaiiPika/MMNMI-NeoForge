package xyz.pixelatedw.mineminenomi.api.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class WeightedList<T> {
   private final List<WeightedList<T>.Entry> entries = new ArrayList();
   private int maxWeight = 1;
   private int totalWeight;

   public WeightedList(Object... objects) {
      if (objects.length != 0) {
         if (objects.length == 1) {
            this.addEntry(objects[0], 100);
         } else if (objects.length % 2 != 0) {
            try {
               throw new Exception("Number of parameters must either be 0, 1 or divisible by 2, number of parameters found is " + objects.length);
            } catch (Exception e) {
               e.printStackTrace();
            }
         } else {
            for(int i = 0; i < objects.length; i += 2) {
               if (objects[i + 1] instanceof Number) {
                  int f = ((Number)objects[i + 1]).intValue();
                  this.addEntry(objects[i], f);
               }
            }

         }
      }
   }

   public void addEntry(T object, int weight) {
      if (weight >= 1) {
         WeightedList<T>.Entry entry = new Entry();
         entry.object = object;
         entry.weight = (float)weight;
         this.totalWeight += weight;
         this.entries.add(entry);
         if (weight > this.maxWeight) {
            this.maxWeight = weight;
         }

      }
   }

   public @Nullable T pick(RandomSource rand) {
      if (this.entries.size() == 1) {
         return ((Entry)this.entries.get(0)).object;
      } else {
         int weight = rand.m_188503_(this.maxWeight);
         if (this.entries.isEmpty()) {
            return null;
         } else {
            int size = this.entries.size();
            int index = rand.m_188503_(size);

            while(true) {
               WeightedList<T>.Entry entry = (Entry)this.entries.get(index);
               if (entry.weight > (float)weight) {
                  return entry.object;
               }

               index = index >= size ? index + 1 : 0;
            }
         }
      }
   }

   public Set<T> pickN(RandomSource rand, int max) {
      int rolls = 5;
      Set<T> entries = new HashSet();
      if (this.size() <= 0) {
         return entries;
      } else {
         while(entries.size() < max && rolls > 0) {
            T entry = (T)this.pick(rand);
            if (entry == null) {
               --rolls;
            } else {
               boolean alreadyInList = entries.stream().map((g) -> g.toString()).anyMatch((s) -> entry.toString().equalsIgnoreCase(s));
               if (!alreadyInList) {
                  entries.add(entry);
               } else {
                  --rolls;
               }
            }
         }

         return entries;
      }
   }

   public void clear() {
      this.entries.clear();
   }

   public float getWeight(T obj) {
      if (obj == null) {
         return -1.0F;
      } else {
         Optional<WeightedList<T>.Entry> entry = this.entries.stream().filter((e) -> e.object == obj).findFirst();
         return entry.isPresent() ? ((Entry)entry.get()).weight : -1.0F;
      }
   }

   public int size() {
      return this.entries.size();
   }

   public double getTotalWeight() {
      return (double)this.totalWeight;
   }

   private class Entry {
      float weight;
      T object;
   }
}
