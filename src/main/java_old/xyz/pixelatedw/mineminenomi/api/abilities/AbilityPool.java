package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class AbilityPool {
   private final Set<AbilityCore<?>> abilities = new HashSet();
   private final HashMap<String, FlagValue<?>> flags = new HashMap();

   public AbilityPool addFlag(String flag, boolean value) {
      this.flags.put(flag, new BooleanFlagValue(value));
      return this;
   }

   public <T> T getFlagValue(String flag, Supplier<T> defaultValue) {
      FlagValue<T> value = (FlagValue)this.flags.get(flag);
      return (T)(value != null ? value.get() : defaultValue.get());
   }

   public void addAbilityCore(AbilityCore<?> core) {
      this.abilities.add(core);
   }

   public boolean hasAbilityCore(AbilityCore<?> core) {
      return this.abilities.contains(core);
   }

   public Stream<AbilityCore<?>> getAllPoolCores() {
      return this.abilities.stream();
   }

   public abstract static class FlagValue<T> {
      protected T value;

      public FlagValue(T value) {
         this.value = value;
      }

      public T get() {
         return this.value;
      }
   }

   public static class BooleanFlagValue extends FlagValue<Boolean> {
      public BooleanFlagValue(Boolean value) {
         super(value);
      }
   }
}
