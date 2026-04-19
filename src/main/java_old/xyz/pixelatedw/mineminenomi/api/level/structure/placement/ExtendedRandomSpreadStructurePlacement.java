package xyz.pixelatedw.mineminenomi.api.level.structure.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
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
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

public class ExtendedRandomSpreadStructurePlacement extends RandomSpreadStructurePlacement {
   public static final Codec<ExtendedRandomSpreadStructurePlacement> CODEC = ExtraCodecs.m_285994_(RecordCodecBuilder.mapCodec((inst) -> inst.group(Vec3i.m_194650_(16).optionalFieldOf("locate_offset", Vec3i.f_123288_).forGetter(StructurePlacement::m_227072_), FrequencyReductionMethod.f_227108_.optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT).forGetter(StructurePlacement::m_227073_), Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(StructurePlacement::m_227074_), ExtraCodecs.f_144628_.fieldOf("salt").forGetter(StructurePlacement::m_227075_), Codec.intRange(0, 4096).fieldOf("spacing").forGetter(ExtendedRandomSpreadStructurePlacement::m_205003_), Codec.intRange(0, 4096).fieldOf("separation").forGetter(ExtendedRandomSpreadStructurePlacement::m_205004_), RandomSpreadType.f_205014_.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(ExtendedRandomSpreadStructurePlacement::m_205005_), ExtendedRandomSpreadStructurePlacement.SuperExclusionZone.CODEC.listOf().optionalFieldOf("exclusion_zones", List.of()).forGetter(ExtendedRandomSpreadStructurePlacement::exclusionZones), Codec.intRange(0, 1048576).optionalFieldOf("min_distance_from_world_center", 0).forGetter(ExtendedRandomSpreadStructurePlacement::minDistanceFromWorldCenter)).apply(inst, ExtendedRandomSpreadStructurePlacement::new)), ExtendedRandomSpreadStructurePlacement::validate).codec();
   private final int spacing;
   private final int separation;
   private final RandomSpreadType spreadType;
   private final List<SuperExclusionZone> exclusionZones;
   private final int minDistanceFromWorldCenter;

   private static DataResult<ExtendedRandomSpreadStructurePlacement> validate(ExtendedRandomSpreadStructurePlacement p_286361_) {
      return p_286361_.spacing <= p_286361_.separation ? DataResult.error(() -> "Spacing has to be larger than separation") : DataResult.success(p_286361_);
   }

   public ExtendedRandomSpreadStructurePlacement(Vec3i offset, StructurePlacement.FrequencyReductionMethod freqReduction, float frequency, int salt, int spacing, int separation, RandomSpreadType spread, List<SuperExclusionZone> exclusionZones, int minDistanceFromWorldCenter) {
      super(offset, freqReduction, frequency, salt, Optional.empty(), spacing, separation, spread);
      this.spacing = spacing;
      this.separation = separation;
      this.spreadType = spread;
      this.exclusionZones = exclusionZones;
      this.minDistanceFromWorldCenter = minDistanceFromWorldCenter;
   }

   public ExtendedRandomSpreadStructurePlacement(int spacing, int sparation, RandomSpreadType spread, int salt) {
      this(Vec3i.f_123288_, FrequencyReductionMethod.DEFAULT, 1.0F, salt, spacing, sparation, spread, List.of(), 0);
   }

   public int m_205003_() {
      return this.spacing;
   }

   public int m_205004_() {
      return this.separation;
   }

   public RandomSpreadType m_205005_() {
      return this.spreadType;
   }

   public int minDistanceFromWorldCenter() {
      return this.minDistanceFromWorldCenter;
   }

   public List<SuperExclusionZone> exclusionZones() {
      return this.exclusionZones;
   }

   public ChunkPos m_227008_(long seed, int chunkX, int chunkZ) {
      int i = Math.floorDiv(chunkX, this.spacing);
      int j = Math.floorDiv(chunkZ, this.spacing);
      WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
      worldgenrandom.m_190058_(seed, i, j, this.m_227075_());
      int k = this.spacing - this.separation;
      int l = this.spreadType.m_227018_(worldgenrandom, k);
      int i1 = this.spreadType.m_227018_(worldgenrandom, k);
      return new ChunkPos(i * this.spacing + l, j * this.spacing + i1);
   }

   protected boolean m_214090_(ChunkGeneratorStructureState chunkGen, int chunkX, int chunkZ) {
      if (this.minDistanceFromWorldCenter > 0) {
         int xBlockPos = chunkX * 16;
         int zBlockPos = chunkZ * 16;
         if (xBlockPos * xBlockPos + zBlockPos * zBlockPos < this.minDistanceFromWorldCenter * this.minDistanceFromWorldCenter) {
            return false;
         }
      }

      ChunkPos chunkpos = this.m_227008_(chunkGen.m_254887_(), chunkX, chunkZ);
      return chunkpos.f_45578_ == chunkX && chunkpos.f_45579_ == chunkZ;
   }

   public boolean m_255071_(ChunkGeneratorStructureState chunkGen, int chunkX, int chunkZ) {
      if (!super.m_255071_(chunkGen, chunkX, chunkZ)) {
         return false;
      } else {
         for(SuperExclusionZone zone : this.exclusionZones) {
            if (zone.isPlacementForbidden(chunkGen, chunkX, chunkZ)) {
               return false;
            }
         }

         return true;
      }
   }

   public StructurePlacementType<?> m_203443_() {
      return (StructurePlacementType)ModStructures.EXTENDED_RANDOM_SPREAD_PLACEMENT.get();
   }

   public static record SuperExclusionZone(Holder<StructureSet> otherSet, int chunkCount) {
      public static final Codec<SuperExclusionZone> CODEC = RecordCodecBuilder.create((inst) -> inst.group(RegistryFileCodec.m_135592_(Registries.f_256998_, StructureSet.f_210001_, false).fieldOf("other_set").forGetter(SuperExclusionZone::otherSet), Codec.intRange(1, 16).fieldOf("chunk_count").forGetter(SuperExclusionZone::chunkCount)).apply(inst, SuperExclusionZone::new));

      boolean isPlacementForbidden(ChunkGeneratorStructureState chunkGen, int chunkX, int chunkZ) {
         return chunkGen.m_254936_(this.otherSet, chunkX, chunkZ, this.chunkCount);
      }
   }
}
