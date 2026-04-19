package xyz.pixelatedw.mineminenomi.api.config.options;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraftforge.common.ForgeConfigSpec;

public class IntegerListOption extends ListOption<Integer> {
   private static final Predicate<Object> VALIDATOR = new Predicate<Object>() {
      public boolean test(Object t) {
         if (!(t instanceof Integer i)) {
            return false;
         } else {
            return i >= 0;
         }
      }
   };

   public IntegerListOption(List<Integer> defaultValue, String optionName, @Nullable String optionDescription) {
      super(defaultValue, VALIDATOR, optionName, optionDescription);
   }

   protected ForgeConfigSpec.ConfigValue<List<? extends Integer>> buildValue(ForgeConfigSpec.Builder builder) {
      if (this.getDescription() != null) {
         builder.comment(this.getDescription());
      }

      return builder.defineList(this.getTitle(), this.getDefault(), this.getValidator());
   }
}
