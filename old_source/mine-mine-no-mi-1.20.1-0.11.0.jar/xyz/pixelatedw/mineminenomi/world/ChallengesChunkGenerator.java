package xyz.pixelatedw.mineminenomi.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

public class ChallengesChunkGenerator extends ChunkGenerator {
   public static final Codec<ChallengesChunkGenerator> CODEC = RecordCodecBuilder.create((inst) -> inst.group(RegistryOps.m_254866_(Biomes.f_48173_)).apply(inst, inst.stable(ChallengesChunkGenerator::new)));

   public ChallengesChunkGenerator(Holder.Reference<Biome> ref) {
      super(new FixedBiomeSource(ref));
   }

   protected Codec<? extends ChunkGenerator> m_6909_() {
      return CODEC;
   }

   public CompletableFuture<ChunkAccess> m_213974_(Executor p_223209_, Blender p_223210_, RandomState p_223211_, StructureManager p_223212_, ChunkAccess p_223213_) {
      return CompletableFuture.completedFuture(p_223213_);
   }

   public int m_214096_(int p_223032_, int p_223033_, Heightmap.Types p_223034_, LevelHeightAccessor p_223035_, RandomState p_223036_) {
      return 0;
   }

   public NoiseColumn m_214184_(int p_223028_, int p_223029_, LevelHeightAccessor p_223030_, RandomState p_223031_) {
      return new NoiseColumn(0, new BlockState[0]);
   }

   public void m_213679_(WorldGenRegion p_223043_, long p_223044_, RandomState p_223045_, BiomeManager p_223046_, StructureManager p_223047_, ChunkAccess p_223048_, GenerationStep.Carving p_223049_) {
   }

   public void m_214194_(WorldGenRegion p_223050_, StructureManager p_223051_, RandomState p_223052_, ChunkAccess p_223053_) {
   }

   public void m_6929_(WorldGenRegion p_62167_) {
   }

   public int m_6331_() {
      return 384;
   }

   public int m_6337_() {
      return 0;
   }

   public int m_142062_() {
      return 0;
   }

   public void m_213600_(List<String> p_223175_, RandomState p_223176_, BlockPos p_223177_) {
   }
}
