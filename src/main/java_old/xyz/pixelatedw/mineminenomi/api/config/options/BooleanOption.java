package xyz.pixelatedw.mineminenomi.api.config.options;

import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Nullable;

public class BooleanOption extends ConfigOption<Boolean> {
   public BooleanOption(Boolean defaultValue, String optionName, @Nullable String optionDescription) {
      super(defaultValue, optionName, optionDescription);
   }

   protected ForgeConfigSpec.ConfigValue<Boolean> buildValue(ForgeConfigSpec.Builder builder) {
      return builder.define(this.getTitle(), (Boolean)this.getDefault());
   }
}
