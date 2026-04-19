package xyz.pixelatedw.mineminenomi.api.config.options;

import net.minecraftforge.common.ForgeConfigSpec;

public class EnumOption<T extends Enum<T>> extends ConfigOption<T> {
   private Class<T> enumClass;
   private T[] enumValues;

   public EnumOption(T defaultValue, T[] values, String optionName, String optionDescription) {
      super(defaultValue, optionName, optionDescription);
      this.enumClass = defaultValue.getClass();
      this.enumValues = values;
   }

   public Class<T> getEnumClass() {
      return this.enumClass;
   }

   protected ForgeConfigSpec.ConfigValue<T> buildValue(ForgeConfigSpec.Builder builder) {
      if (this.getDescription() != null) {
         builder.comment(this.getDescription());
      }

      return builder.defineEnum(this.getTitle(), (Enum)this.getDefault(), this.enumValues);
   }
}
