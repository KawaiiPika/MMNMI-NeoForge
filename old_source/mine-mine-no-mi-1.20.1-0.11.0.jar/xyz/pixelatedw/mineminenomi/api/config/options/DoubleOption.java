package xyz.pixelatedw.mineminenomi.api.config.options;

import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Nullable;

public class DoubleOption extends MinMaxOption<Double> {
   public DoubleOption(Double defaultValue, Double min, Double max, String optionName, @Nullable String optionDescription) {
      super(defaultValue, min, max, optionName, optionDescription);
   }

   protected ForgeConfigSpec.ConfigValue<Double> buildValue(ForgeConfigSpec.Builder builder) {
      return builder.defineInRange(this.getTitle(), (Double)this.getDefault(), (Double)this.getMin(), (Double)this.getMax());
   }
}
