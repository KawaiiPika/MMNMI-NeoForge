package xyz.pixelatedw.mineminenomi.init;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.world.ChallengesChunkGenerator;

import java.util.function.Supplier;

public class ModDimensions {
    public static final DeferredRegister<MapCodec<? extends net.minecraft.world.level.chunk.ChunkGenerator>> CHUNK_GENERATORS = 
            DeferredRegister.create(Registries.CHUNK_GENERATOR, ModMain.PROJECT_ID);

    public static final Supplier<MapCodec<ChallengesChunkGenerator>> CHALLENGES_CHUNK_GENERATOR = 
            CHUNK_GENERATORS.register("challenges_chunk_generator", () -> ChallengesChunkGenerator.CODEC);

    public static void register(IEventBus bus) {
        CHUNK_GENERATORS.register(bus);
    }
}
