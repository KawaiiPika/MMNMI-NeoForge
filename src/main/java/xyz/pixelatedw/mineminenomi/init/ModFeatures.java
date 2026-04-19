package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.world.features.*;
import xyz.pixelatedw.mineminenomi.world.features.configs.SizedBlockStateFeatureConfig;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, ModMain.PROJECT_ID);

    public static final Supplier<Feature<NoneFeatureConfiguration>> PONEGLYPH = FEATURES.register("poneglyph", () -> new PoneglyphFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> SNOW_MOUNTAIN = FEATURES.register("snow_mountain", () -> new SnowMountainFeature(ProbabilityFeatureConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> SNOW_FLOOR = FEATURES.register("snow_floor", () -> new SnowFloorFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> LARGE_ICE_SPIKE = FEATURES.register("large_ice_spike", () -> new LargeIceSpikeFeature(ProbabilityFeatureConfiguration.CODEC));
    public static final Supplier<Feature<SimpleBlockConfiguration>> LAKE_AIR = FEATURES.register("lake_air", () -> new LargeLakesFeature(SimpleBlockConfiguration.CODEC));
    public static final Supplier<Feature<SizedBlockStateFeatureConfig>> DIORITE_BOULDER = FEATURES.register("diorite_boulder", () -> new BoulderFeature(SizedBlockStateFeatureConfig.CODEC));

    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}
