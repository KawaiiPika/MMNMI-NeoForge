package xyz.pixelatedw.mineminenomi.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
   public static final ForgeConfigSpec SPEC;

   private static void setupConfig(ForgeConfigSpec.Builder builder) {
      builder.push("System");
      SystemConfig.MASTER_COMMAND.createValue(builder);
      SystemConfig.ENABLE_PERMISSIONS.createValue(builder);
      builder.pop();
   }

   static {
      ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
      setupConfig(configBuilder);
      SPEC = configBuilder.build();
   }
}
