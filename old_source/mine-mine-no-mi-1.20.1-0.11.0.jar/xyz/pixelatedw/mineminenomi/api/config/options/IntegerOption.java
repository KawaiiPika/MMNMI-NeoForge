package xyz.pixelatedw.mineminenomi.api.config.options;

import javax.annotation.Nullable;
import net.minecraftforge.common.ForgeConfigSpec;

public class IntegerOption extends MinMaxOption<Integer> {
   public IntegerOption(Integer defaultValue, Integer min, Integer max, String optionName, @Nullable String optionDescription) {
      super(defaultValue, min, max, optionName, optionDescription);
   }

   protected ForgeConfigSpec.ConfigValue<Integer> buildValue(ForgeConfigSpec.Builder builder) {
      return builder.defineInRange(this.getTitle(), (Integer)this.getDefault(), (Integer)this.getMin(), (Integer)this.getMax());
   }
}
