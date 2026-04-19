package xyz.pixelatedw.mineminenomi.api.config.options;

import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public abstract class ConfigOption<T> {
   private static final Component WORLD_RESTART_REQUIRED = Component.m_237113_("Requires a world restart in order to take effect!");
   private ForgeConfigSpec.ConfigValue<T> value;
   protected T defaultValue;
   private String defaultTitle;
   private @Nullable String defaultDescription;
   private Component title;
   private Component description = Component.m_237119_();
   private boolean requiresWorldRestart;

   public ConfigOption(T defaultValue, String optionName, @Nullable String optionDescription) {
      this.defaultValue = defaultValue;
      this.defaultTitle = optionName;
      optionName = WyHelper.getResourceName(optionName);
      this.title = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "config.option." + optionName, this.defaultTitle));
      if (optionDescription != null) {
         this.defaultDescription = optionDescription;
         this.description = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "config.option." + optionName + ".tooltip", optionDescription));
      }

   }

   public <C extends ConfigOption<T>> C worldRestart() {
      this.requiresWorldRestart = true;
      return (C)this;
   }

   protected abstract ForgeConfigSpec.ConfigValue<T> buildValue(ForgeConfigSpec.Builder var1);

   public ForgeConfigSpec.ConfigValue<T> createValue(ForgeConfigSpec.Builder builder) {
      if (this.getDescription() != null) {
         builder.comment(this.getDescription());
      }

      if (this.requiresWorldRestart) {
         builder.worldRestart();
         builder.comment("Requires a world restart in order to take effect!");
      }

      this.value = this.buildValue(builder);
      return this.value;
   }

   public ForgeConfigSpec.ConfigValue<T> getValue() {
      return this.value;
   }

   public T get() {
      try {
         return (T)this.value.get();
      } catch (Exception var2) {
         return this.defaultValue;
      }
   }

   public T getDefault() {
      return this.defaultValue;
   }

   public String getTitle() {
      return this.defaultTitle;
   }

   public @Nullable String getDescription() {
      return this.defaultDescription;
   }

   public Component getTitleComponent() {
      return this.title;
   }

   public @Nullable Component[] getDescriptionComponent() {
      return this.requiresWorldRestart ? new Component[]{this.description, WORLD_RESTART_REQUIRED} : new Component[]{this.description};
   }
}
