package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.world.features.BoulderFeature;
import xyz.pixelatedw.mineminenomi.world.features.LargeIceSpikeFeature;
import xyz.pixelatedw.mineminenomi.world.features.LargeLakesFeature;
import xyz.pixelatedw.mineminenomi.world.features.PoneglyphFeature;
import xyz.pixelatedw.mineminenomi.world.features.SnowFloorFeature;
import xyz.pixelatedw.mineminenomi.world.features.SnowMountainFeature;
import xyz.pixelatedw.mineminenomi.world.features.configs.SizedBlockStateFeatureConfig;

public class ModFeatures {
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> PONEGLYPH = ModRegistry.<Feature<NoneFeatureConfiguration>>registerFeature("poneglyph", PoneglyphFeature::new);
   public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> SNOW_MOUNTAIN = ModRegistry.<Feature<ProbabilityFeatureConfiguration>>registerFeature("snow_mountain", SnowMountainFeature::new);
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SNOW_FLOOR = ModRegistry.<Feature<NoneFeatureConfiguration>>registerFeature("snow_floor", SnowFloorFeature::new);
   public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> LARGE_ICE_SPIKE = ModRegistry.<Feature<ProbabilityFeatureConfiguration>>registerFeature("large_ice_spike", LargeIceSpikeFeature::new);
   public static final RegistryObject<Feature<SimpleBlockConfiguration>> LAKE_AIR = ModRegistry.<Feature<SimpleBlockConfiguration>>registerFeature("lake_air", LargeLakesFeature::new);
   public static final RegistryObject<Feature<SizedBlockStateFeatureConfig>> DIORITE_BOULDER = ModRegistry.<Feature<SizedBlockStateFeatureConfig>>registerFeature("diorite_boulder", BoulderFeature::new);

   public static void init() {
   }
}
