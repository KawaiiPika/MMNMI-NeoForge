package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraftforge.common.world.BiomeModifier;
import xyz.pixelatedw.mineminenomi.world.features.FeatureBiomeModifiers;
import xyz.pixelatedw.mineminenomi.world.spawners.SpawnsBiomeModifiers;

public class ModBiomeModifiers {
   public static void bootstrap(BootstapContext<BiomeModifier> context) {
      FeatureBiomeModifiers.bootstrap(context);
      SpawnsBiomeModifiers.bootstrap(context);
   }
}
