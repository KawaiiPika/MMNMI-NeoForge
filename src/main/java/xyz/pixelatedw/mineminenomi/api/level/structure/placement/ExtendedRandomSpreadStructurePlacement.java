package xyz.pixelatedw.mineminenomi.api.level.structure.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

import java.util.List;
import java.util.Optional;

public class ExtendedRandomSpreadStructurePlacement extends RandomSpreadStructurePlacement {
    public static final MapCodec<ExtendedRandomSpreadStructurePlacement> MAP_CODEC = RecordCodecBuilder.<ExtendedRandomSpreadStructurePlacement>mapCodec(inst -> inst.group(
            Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(ExtendedRandomSpreadStructurePlacement::locateOffset),
            StructurePlacement.FrequencyReductionMethod.CODEC.optionalFieldOf("frequency_reduction_method", StructurePlacement.FrequencyReductionMethod.DEFAULT).forGetter(ExtendedRandomSpreadStructurePlacement::frequencyReductionMethod),
            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(ExtendedRandomSpreadStructurePlacement::frequency),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter(ExtendedRandomSpreadStructurePlacement::salt),
            Codec.intRange(0, 4096).fieldOf("spacing").forGetter(ExtendedRandomSpreadStructurePlacement::spacing),
            Codec.intRange(0, 4096).fieldOf("separation").forGetter(ExtendedRandomSpreadStructurePlacement::separation),
            RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(ExtendedRandomSpreadStructurePlacement::spreadType),
            SuperExclusionZone.CODEC.listOf().optionalFieldOf("exclusion_zones", List.of()).forGetter(ExtendedRandomSpreadStructurePlacement::exclusionZones),
            Codec.intRange(0, 1048576).optionalFieldOf("min_distance_from_world_center", 0).forGetter(ExtendedRandomSpreadStructurePlacement::minDistanceFromWorldCenter)
    ).apply(inst, ExtendedRandomSpreadStructurePlacement::new)).validate(ExtendedRandomSpreadStructurePlacement::validate);

    public static final Codec<ExtendedRandomSpreadStructurePlacement> CODEC = MAP_CODEC.codec();

    private final int spacing;
    private final int separation;
    private final RandomSpreadType spreadType;
    private final List<SuperExclusionZone> exclusionZones;
    private final int minDistanceFromWorldCenter;

    private static DataResult<ExtendedRandomSpreadStructurePlacement> validate(ExtendedRandomSpreadStructurePlacement placement) {
        return placement.spacing <= placement.separation ? DataResult.error(() -> "Spacing has to be larger than separation") : DataResult.success(placement);
    }

    public ExtendedRandomSpreadStructurePlacement(Vec3i offset, StructurePlacement.FrequencyReductionMethod freqReduction, float frequency, int salt, int spacing, int separation, RandomSpreadType spread, List<SuperExclusionZone> exclusionZones, int minDistanceFromWorldCenter) {
        super(offset, freqReduction, frequency, salt, Optional.empty(), spacing, separation, spread);
        this.spacing = spacing;
        this.separation = separation;
        this.spreadType = spread;
        this.exclusionZones = exclusionZones;
        this.minDistanceFromWorldCenter = minDistanceFromWorldCenter;
    }

    public int spacing() { return this.spacing; }
    public int separation() { return this.separation; }
    public RandomSpreadType spreadType() { return this.spreadType; }
    public int minDistanceFromWorldCenter() { return this.minDistanceFromWorldCenter; }
    public List<SuperExclusionZone> exclusionZones() { return this.exclusionZones; }

    @Override
    public ChunkPos getPotentialStructureChunk(long seed, int chunkX, int chunkZ) {
        int i = Math.floorDiv(chunkX, this.spacing);
        int j = Math.floorDiv(chunkZ, this.spacing);
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
        worldgenrandom.setLargeFeatureWithSalt(seed, i, j, this.salt());
        int k = this.spacing - this.separation;
        return new ChunkPos(i * this.spacing + this.spreadType.evaluate(worldgenrandom, k), j * this.spacing + this.spreadType.evaluate(worldgenrandom, k));
    }

    @Override
    protected boolean isPlacementChunk(ChunkGeneratorStructureState chunkGen, int chunkX, int chunkZ) {
        if (this.minDistanceFromWorldCenter > 0) {
            int xBlockPos = chunkX * 16;
            int zBlockPos = chunkZ * 16;
            if ((long)xBlockPos * xBlockPos + (long)zBlockPos * zBlockPos < (long)this.minDistanceFromWorldCenter * this.minDistanceFromWorldCenter) {
                return false;
            }
        }

        ChunkPos chunkpos = this.getPotentialStructureChunk(chunkGen.getLevelSeed(), chunkX, chunkZ);
        return chunkpos.x == chunkX && chunkpos.z == chunkZ;
    }

    @Override
    public boolean isStructureChunk(ChunkGeneratorStructureState chunkGen, int chunkX, int chunkZ) {
        if (!super.isStructureChunk(chunkGen, chunkX, chunkZ)) {
            return false;
        } else {
            // Temporarily commented out to fix compilation and test world loading
            /*
            for (SuperExclusionZone zone : this.exclusionZones) {
                if (chunkGen.hasAnyStructureOfSet(zone.otherSet(), chunkX, chunkZ, zone.chunkCount())) {
                    return false;
                }
            }
            */
            return true;
        }
    }

    @Override
    public StructurePlacementType<?> type() {
        return ModStructures.EXTENDED_RANDOM_SPREAD_PLACEMENT.get();
    }

    public static record SuperExclusionZone(Holder<StructureSet> otherSet, int chunkCount) {
        public static final Codec<SuperExclusionZone> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                RegistryFileCodec.create(Registries.STRUCTURE_SET, StructureSet.DIRECT_CODEC, false).fieldOf("other_set").forGetter(SuperExclusionZone::otherSet),
                Codec.intRange(1, 16).fieldOf("chunk_count").forGetter(SuperExclusionZone::chunkCount)
        ).apply(inst, SuperExclusionZone::new));
    }
}
