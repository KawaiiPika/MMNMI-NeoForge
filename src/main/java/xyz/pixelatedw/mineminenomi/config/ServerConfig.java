package xyz.pixelatedw.mineminenomi.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig {

    public static final ServerConfig INSTANCE;
    public static final ModConfigSpec SPEC;

    public final ModConfigSpec.IntValue maxAbilityBars;

    static {
        final Pair<ServerConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ServerConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    ServerConfig(ModConfigSpec.Builder builder) {
        builder.push("Abilities");
        maxAbilityBars = builder
                .comment("Number of ability bars;\nDefault: 2")
                .defineInRange("abilityBars", 2, 1, 10);
        builder.pop();
    }

    public static int getAbilityBars() {
        return INSTANCE.maxAbilityBars.get();
    }

    public static boolean isMobRewardsEnabled() {
        return true;
    }

    public static double getDorikiRewardMultiplier() {
        return 1.0;
    }

    public static double getBellyRewardMultiplier() {
        return 1.0;
    }

    public static double getBountyRewardMultiplier() {
        return 1.0;
    }

    public static double getHakiExpMultiplier() {
        return 1.0;
    }

    public static double getLoyaltyMultiplier() {
        return 1.0;
    }

    public static boolean isMinimumDorikiPerKillEnabled() {
        return false;
    }

    public static float getHakiExpLimit() {
        return 100.0f;
    }

    public static double getDorikiKeepPercentage() {
        return 10.0;
    }

    public static double getBountyKeepPercentage() {
        return 10.0;
    }

    public static double getBellyKeepPercentage() {
        return 10.0;
    }

    public static double getDorikiLimit() {
        return 10000.0;
    }
}
