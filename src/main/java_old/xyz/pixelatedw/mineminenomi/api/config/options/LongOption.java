package xyz.pixelatedw.mineminenomi.api.config.options;

import javax.annotation.Nullable;
import net.minecraftforge.common.ForgeConfigSpec;

public class LongOption extends MinMaxOption<Long> {
   public LongOption(Long defaultValue, Long min, Long max, String optionName, @Nullable String optionDescription) {
      super(defaultValue, min, max, optionName, optionDescription);
   }

   protected ForgeConfigSpec.ConfigValue<Long> buildValue(ForgeConfigSpec.Builder builder) {
      return builder.defineInRange(this.getTitle(), (Long)this.getDefault(), (Long)this.getMin(), (Long)this.getMax());
   }
}
