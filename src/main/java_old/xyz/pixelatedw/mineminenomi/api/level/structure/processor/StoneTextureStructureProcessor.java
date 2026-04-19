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

public class StoneTextureStructureProcessor extends StructureProcessor {
   public static final Codec<StoneTextureStructureProcessor> CODEC = Codec.unit(StoneTextureStructureProcessor::new);
   private static final Block[] FULL_BLOCK;
   private static final Block[] WALL_BLOCK;
   private static final Block[] SLAB_BLOCK;

   @Nullable
   public StructureTemplate.StructureBlockInfo process(LevelReader world, BlockPos pos1, BlockPos pos2, StructureTemplate.StructureBlockInfo info1, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
      BlockPos pos = info2.f_74675_();
      RandomSource rng = RandomSource.m_216335_(Mth.m_14057_(pos));
      if (info1.f_74676_().m_60734_() == Blocks.f_50069_) {
         BlockState newState = FULL_BLOCK[rng.m_188503_(FULL_BLOCK.length)].m_49966_();
         return new StructureTemplate.StructureBlockInfo(pos, newState, info2.f_74677_());
      } else if (info1.f_74676_().m_60734_() == Blocks.f_50274_) {
         boolean isTop = !world.m_8055_(pos.m_7495_()).m_60795_();
         if (isTop && rng.m_188499_()) {
            BlockState newState = SLAB_BLOCK[rng.m_188503_(SLAB_BLOCK.length)].m_49966_();
            return new StructureTemplate.StructureBlockInfo(pos.m_7495_(), newState, info2.f_74677_());
         } else {
            BlockState newState = WALL_BLOCK[rng.m_188503_(WALL_BLOCK.length)].m_49966_();
            return new StructureTemplate.StructureBlockInfo(pos, newState, info2.f_74677_());
         }
      } else if (info1.f_74676_().m_60734_() == Blocks.f_50404_) {
         BlockState newState = SLAB_BLOCK[rng.m_188503_(SLAB_BLOCK.length)].m_49966_();
         return new StructureTemplate.StructureBlockInfo(pos, newState, info2.f_74677_());
      } else {
         return info2;
      }
   }

   protected StructureProcessorType<?> m_6953_() {
      return (StructureProcessorType)ModStructures.STONE_TEXTURE_PROCESSOR.get();
   }

   static {
      FULL_BLOCK = new Block[]{Blocks.f_50069_, Blocks.f_50652_, Blocks.f_50334_};
      WALL_BLOCK = new Block[]{Blocks.f_50274_, Blocks.f_50611_, Blocks.f_50016_};
      SLAB_BLOCK = new Block[]{Blocks.f_50404_, Blocks.f_50409_, Blocks.f_50600_, Blocks.f_50165_, Blocks.f_50016_};
   }
}
