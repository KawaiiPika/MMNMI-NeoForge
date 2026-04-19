package xyz.pixelatedw.mineminenomi.init;

import com.mojang.serialization.Codec;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.world.ChallengesChunkGenerator;

public class ModDimensions {
   public static final RegistryObject<Codec<ChallengesChunkGenerator>> CHUNK_GENERATOR_CHALLENGES = ModRegistry.<Codec<ChallengesChunkGenerator>>registerChunkGenerator("challenges_chunk_generator", () -> ChallengesChunkGenerator.CODEC);

   public static void init() {
   }
}
