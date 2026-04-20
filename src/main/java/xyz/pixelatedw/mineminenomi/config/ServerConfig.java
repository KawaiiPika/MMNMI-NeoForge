package xyz.pixelatedw.mineminenomi.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue DORIKI_LIMIT;
    public static final ModConfigSpec.IntValue HEALTH_GAIN_FREQUENCY;
    public static final ModConfigSpec.BooleanValue RACE_RANDOMIZER;
    public static final ModConfigSpec.BooleanValue ALLOW_SUB_RACE_SELECT;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("general");

        DORIKI_LIMIT = builder
                .comment("Sets a new limit for maximum doriki a player may obtain")
                .defineInRange("dorikiLimit", 10000, 0, 100000);

        HEALTH_GAIN_FREQUENCY = builder
                .comment("Defines at what doriki intervals an extra +1 HP is gained")
                .defineInRange("healthGainFrequency", 40, 40, 100);

        RACE_RANDOMIZER = builder
                .comment("If true, players will have a randomized race and cannot select it during Character Creation")
                .define("raceRandomizer", false);

        ALLOW_SUB_RACE_SELECT = builder
                .comment("If true, players can select sub-races like Minks during Character Creation")
                .define("allowSubRaceSelect", true);

        builder.pop();

        SPEC = builder.build();
    }
}
