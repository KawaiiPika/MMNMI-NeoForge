package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

public class IgnoreWaterloggingStructureProcessor extends StructureProcessor {
   public static final Codec<IgnoreWaterloggingStructureProcessor> CODEC = Codec.unit(IgnoreWaterloggingStructureProcessor::new);

   @Nullable
   public StructureTemplate.StructureBlockInfo process(LevelReader world, BlockPos pos1, BlockPos pos2, StructureTemplate.StructureBlockInfo info1, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
      return info2;
   }

   protected StructureProcessorType<?> m_6953_() {
      return (StructureProcessorType)ModStructures.IGNORE_WATERLOGGING_PROCESSOR.get();
   }
}
