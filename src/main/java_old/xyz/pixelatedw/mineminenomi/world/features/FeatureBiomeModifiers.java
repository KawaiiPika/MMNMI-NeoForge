package xyz.pixelatedw.mineminenomi.world.features;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import xyz.pixelatedw.mineminenomi.init.ModPlacedFeatures;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class FeatureBiomeModifiers {
   public static final ResourceKey<BiomeModifier> ADD_KAIROSEKI_ORE = registerKey("add_kairoseki_ore");
   public static final ResourceKey<BiomeModifier> ADD_PONEGLYPH = registerKey("add_poneglyph");

   public static void bootstrap(BootstapContext<BiomeModifier> context) {
      HolderGetter<PlacedFeature> placedFeatures = context.m_255420_(Registries.f_256988_);
      HolderGetter<Biome> biomes = context.m_255420_(Registries.f_256952_);
      context.m_255272_(ADD_KAIROSEKI_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.m_254956_(ModTags.Biomes.HAS_KAIROSEKI_ORE), HolderSet.m_205809_(new Holder[]{placedFeatures.m_255043_(ModPlacedFeatures.KAIROSEKI_ORE)}), Decoration.UNDERGROUND_ORES));
      context.m_255272_(ADD_PONEGLYPH, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.m_254956_(BiomeTags.f_215817_), HolderSet.m_205809_(new Holder[]{placedFeatures.m_255043_(ModPlacedFeatures.PONEGLYPH)}), Decoration.UNDERGROUND_STRUCTURES));
   }

   private static ResourceKey<BiomeModifier> registerKey(String name) {
      return ResourceKey.m_135785_(Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath("mineminenomi", name));
   }
}
