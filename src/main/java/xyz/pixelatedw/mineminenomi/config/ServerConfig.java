package xyz.pixelatedw.mineminenomi.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.IntValue DORIKI_LIMIT;
    public static final ModConfigSpec.IntValue HEALTH_GAIN_FREQUENCY;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("General");
        DORIKI_LIMIT = builder
                .comment("Sets a new limit for maximum doriki a player may obtain\nDefault: 10000")
                .defineInRange("Doriki Limit", 10000, 0, 100000);

        HEALTH_GAIN_FREQUENCY = builder
                .comment("Defines at what doriki intervals an extra +1 HP is gained\nDefault: 40")
                .defineInRange("Health Gain Frequency", 40, 40, 100);
        builder.pop();

        SPEC = builder.build();
    }

    public static int getDorikiLimit() {
        return DORIKI_LIMIT.get();
    }

    public static int getHealthGainFrequency() {
        return HEALTH_GAIN_FREQUENCY.get();
    }
}
