package xyz.pixelatedw.mineminenomi.api.config.options;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class ListOption<T> extends ConfigOption<List<? extends T>> {
   private Predicate<Object> validator;

   public ListOption(List<T> defaultValue, Predicate<Object> validator, String optionName, @Nullable String optionDescription) {
      super(defaultValue, optionName, optionDescription);
      this.validator = validator;
   }

   public List<T> getDefault() {
      return this.defaultValue;
   }

   public Predicate<Object> getValidator() {
      return this.validator;
   }
}
