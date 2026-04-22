package xyz.pixelatedw.mineminenomi.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class FactionsConfig {
    public static ModConfigSpec.IntValue TIME_BETWEEN_PACKAGE_DROPS;
    public static ModConfigSpec.IntValue CREW_BOUNTY_REQUIREMENT;
    public static ModConfigSpec.BooleanValue WORLD_MESSAGE_ON_CREW_CREATE;
    public static ModConfigSpec.BooleanValue DISABLE_FRIENDLY_FIRE;

    public static void register(ModConfigSpec.Builder builder) {
        builder.push("Factions");
        TIME_BETWEEN_PACKAGE_DROPS = builder
            .comment("Time (in minutes) it takes for another package to drop. 0 means no package will spawn. Default: 15")
            .defineInRange("timeBetweenPackageDrops", 15, 0, 60);
        CREW_BOUNTY_REQUIREMENT = builder
            .comment("Bounty Requirement for creating a crew; 0 means no requirement. Default: 0")
            .defineInRange("crewBountyRequirement", 0, 0, 100000);
        WORLD_MESSAGE_ON_CREW_CREATE = builder
            .comment("Sends a message to all players when a new crew gets formed. Default: false")
            .define("worldMessageOnCrewCreate", false);
        DISABLE_FRIENDLY_FIRE = builder
            .comment("Disabled the friendly damage between crewmates. Default: true")
            .define("disableFriendlyFire", true);
        builder.pop();
    }
}
