package xyz.pixelatedw.mineminenomi.init;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class ModPlacedFeatures {
   public static final ResourceKey<PlacedFeature> KAIROSEKI_ORE = registerKey("kairoseki_ore_placed");
   public static final ResourceKey<PlacedFeature> PONEGLYPH = registerKey("poneglyph_placed");
   public static final ResourceKey<PlacedFeature> SNOW_MOUNTAIN = registerKey("snow_mountain");
   public static final ResourceKey<PlacedFeature> SNOW_FLOOR = registerKey("snow_floor");
   public static final ResourceKey<PlacedFeature> LARGE_ICE_SPIKE = registerKey("large_ice_spike");

   public static void bootstrap(BootstapContext<PlacedFeature> context) {
      HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.m_255420_(Registries.f_256911_);
      register(context, KAIROSEKI_ORE, configuredFeatures.m_255043_(ModConfiguredFeatures.KAIROSEKI_ORE), ore(CountPlacement.m_191628_(6), PlacementUtils.f_195360_));
      register(context, PONEGLYPH, configuredFeatures.m_255043_(ModConfiguredFeatures.PONEGLYPH), List.of(CountPlacement.m_191628_(1), InSquarePlacement.m_191715_(), PlacementUtils.f_195360_, BiomeFilter.m_191561_()));
      register(context, SNOW_MOUNTAIN, configuredFeatures.m_255043_(ModConfiguredFeatures.SNOW_MOUNTAIN), List.of(CountPlacement.m_191628_(1), InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_()));
      register(context, SNOW_FLOOR, configuredFeatures.m_255043_(ModConfiguredFeatures.SNOW_FLOOR), List.of(BiomeFilter.m_191561_()));
      register(context, LARGE_ICE_SPIKE, configuredFeatures.m_255043_(ModConfiguredFeatures.LARGE_ICE_SPIKE), List.of(CountPlacement.m_191628_(1), InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_()));
   }

   private static List<PlacementModifier> ore(PlacementModifier p_195347_, PlacementModifier p_195348_) {
      return List.of(p_195347_, InSquarePlacement.m_191715_(), p_195348_, BiomeFilter.m_191561_());
   }

   private static ResourceKey<PlacedFeature> registerKey(String name) {
      return ResourceKey.m_135785_(Registries.f_256988_, ResourceLocation.fromNamespaceAndPath("mineminenomi", name));
   }

   private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
      context.m_255272_(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
   }
}
