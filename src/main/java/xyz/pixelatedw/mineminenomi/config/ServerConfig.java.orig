package xyz.pixelatedw.mineminenomi.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue DORIKI_LIMIT;
    public static final ModConfigSpec.IntValue HEALTH_GAIN_FREQUENCY;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("general");

        DORIKI_LIMIT = builder
                .comment("Sets a new limit for maximum doriki a player may obtain")
                .defineInRange("dorikiLimit", 10000, 0, 100000);

        HEALTH_GAIN_FREQUENCY = builder
                .comment("Defines at what doriki intervals an extra +1 HP is gained")
                .defineInRange("healthGainFrequency", 40, 40, 100);

        builder.pop();

        SPEC = builder.build();
    }
}
