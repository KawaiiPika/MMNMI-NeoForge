package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

public class BannditWoodTextureStructureProcessor extends StructureProcessor {
   public static final Codec<BannditWoodTextureStructureProcessor> CODEC = Codec.unit(BannditWoodTextureStructureProcessor::new);
   private static final Block[] LOG_BLOCK;
   private static final Block[] FENCE_BLOCK;
   private static final Block[] SLABS_BLOCK;

   @Nullable
   public StructureTemplate.StructureBlockInfo process(LevelReader world, BlockPos pos1, BlockPos pos2, StructureTemplate.StructureBlockInfo info1, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
      BlockPos pos = info2.f_74675_();
      RandomSource rng = RandomSource.m_216335_(Mth.m_14057_(pos));
      if (info1.f_74676_().m_60734_() == Blocks.f_50004_) {
         BlockState newState = LOG_BLOCK[rng.m_188503_(LOG_BLOCK.length)].m_49966_();
         return new StructureTemplate.StructureBlockInfo(pos, newState, info2.f_74677_());
      } else if (info1.f_74676_().m_60734_() == Blocks.f_50403_) {
         BlockState newState = SLABS_BLOCK[rng.m_188503_(SLABS_BLOCK.length)].m_49966_();
         return new StructureTemplate.StructureBlockInfo(pos, newState, info2.f_74677_());
      } else if (info1.f_74676_().m_60734_() == Blocks.f_50483_) {
         BlockState newState = FENCE_BLOCK[rng.m_188503_(FENCE_BLOCK.length)].m_49966_();
         return new StructureTemplate.StructureBlockInfo(pos, newState, info2.f_74677_());
      } else {
         return info2;
      }
   }

   protected StructureProcessorType<?> m_6953_() {
      return (StructureProcessorType)ModStructures.BANDIT_WOOD_TEXTURE_PROCESSOR.get();
   }

   static {
      LOG_BLOCK = new Block[]{Blocks.f_50004_, Blocks.f_50009_, Blocks.f_50000_, Blocks.f_50005_};
      FENCE_BLOCK = new Block[]{Blocks.f_50483_, Blocks.f_50479_};
      SLABS_BLOCK = new Block[]{Blocks.f_50403_, Blocks.f_50399_};
   }
}
