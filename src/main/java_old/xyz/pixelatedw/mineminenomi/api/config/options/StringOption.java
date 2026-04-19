package xyz.pixelatedw.mineminenomi.api.config.options;

import javax.annotation.Nullable;
import net.minecraftforge.common.ForgeConfigSpec;

public class StringOption extends ConfigOption<String> {
   public StringOption(String defaultValue, String optionName, @Nullable String optionDescription) {
      super(defaultValue, optionName, optionDescription);
   }

   protected ForgeConfigSpec.ConfigValue<String> buildValue(ForgeConfigSpec.Builder builder) {
      return builder.define(this.getTitle(), (String)this.getDefault());
   }
}
