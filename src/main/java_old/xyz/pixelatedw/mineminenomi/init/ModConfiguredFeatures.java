package xyz.pixelatedw.mineminenomi.init;

import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import xyz.pixelatedw.mineminenomi.world.features.configs.SizedBlockStateFeatureConfig;

public class ModConfiguredFeatures {
   public static final ResourceKey<ConfiguredFeature<?, ?>> KAIROSEKI_ORE = registerKey("kairoseki_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> PONEGLYPH = registerKey("poneglyph");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SNOW_MOUNTAIN = registerKey("snow_mountain");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SNOW_FLOOR = registerKey("snow_floor");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ICE_SPIKE = registerKey("large_ice_spike");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SABAODY_TREE = registerKey("sabaody_tree");
   public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_SABAODY_TREE = registerKey("huge_sabaody_tree");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LAKE_AIR = registerKey("lake_air");
   public static final ResourceKey<ConfiguredFeature<?, ?>> DIORITE_BOULDER = registerKey("diorite_boulder");

   public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
      RuleTest stoneTest = new TagMatchTest(BlockTags.f_144266_);
      RuleTest deepslateTest = new TagMatchTest(BlockTags.f_144267_);
      List<OreConfiguration.TargetBlockState> kairosekiOre = List.of(OreConfiguration.m_161021_(stoneTest, ((Block)ModBlocks.KAIROSEKI_ORE.get()).m_49966_()), OreConfiguration.m_161021_(deepslateTest, ((Block)ModBlocks.DEEPSLATE_KAIROSEKI_ORE.get()).m_49966_()));
      register(context, KAIROSEKI_ORE, Feature.f_65731_, new OreConfiguration(kairosekiOre, 9));
      register(context, PONEGLYPH, (Feature)ModFeatures.PONEGLYPH.get(), NoneFeatureConfiguration.f_67737_);
      register(context, SNOW_MOUNTAIN, (Feature)ModFeatures.SNOW_MOUNTAIN.get(), new ProbabilityFeatureConfiguration(0.01F));
      register(context, LARGE_ICE_SPIKE, (Feature)ModFeatures.LARGE_ICE_SPIKE.get(), new ProbabilityFeatureConfiguration(0.01F));
      register(context, LAKE_AIR, (Feature)ModFeatures.LAKE_AIR.get(), new SimpleBlockConfiguration(BlockStateProvider.m_191382_(Blocks.f_50016_)));
      register(context, SNOW_FLOOR, (Feature)ModFeatures.SNOW_FLOOR.get(), NoneFeatureConfiguration.f_67737_);
      register(context, DIORITE_BOULDER, (Feature)ModFeatures.DIORITE_BOULDER.get(), new SizedBlockStateFeatureConfig(Blocks.f_50228_.m_49966_(), 5));
   }

   private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
      return ResourceKey.m_135785_(Registries.f_256911_, ResourceLocation.fromNamespaceAndPath("mineminenomi", name));
   }

   private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
      context.m_255272_(key, new ConfiguredFeature(feature, configuration));
   }
}
