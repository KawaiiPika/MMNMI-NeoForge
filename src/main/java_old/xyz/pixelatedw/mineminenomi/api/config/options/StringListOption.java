package xyz.pixelatedw.mineminenomi.api.config.options;

import com.google.common.base.Strings;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraftforge.common.ForgeConfigSpec;

public class StringListOption extends ListOption<String> {
   private static final Predicate<Object> STRING_VALIDATOR = new Predicate<Object>() {
      public boolean test(Object t) {
         if (!(t instanceof String str)) {
            return false;
         } else {
            return !Strings.isNullOrEmpty(str);
         }
      }
   };

   public StringListOption(List<String> defaultValue, String optionName, @Nullable String optionDescription) {
      super(defaultValue, STRING_VALIDATOR, optionName, optionDescription);
   }

   protected ForgeConfigSpec.ConfigValue<List<? extends String>> buildValue(ForgeConfigSpec.Builder builder) {
      return builder.defineList(this.getTitle(), this.getDefault(), this.getValidator());
   }
}
