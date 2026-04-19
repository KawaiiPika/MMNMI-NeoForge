package xyz.pixelatedw.mineminenomi.api.config.options;

import javax.annotation.Nullable;

public abstract class MinMaxOption<T> extends ConfigOption<T> {
   private T min;
   private T max;

   public MinMaxOption(T defaultValue, T min, T max, String optionName, @Nullable String optionDescription) {
      super(defaultValue, optionName, optionDescription);
      this.min = min;
      this.max = max;
   }

   public T getMin() {
      return this.min;
   }

   public T getMax() {
      return this.max;
   }
}
