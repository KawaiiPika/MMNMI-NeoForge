package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.level.structure.placement.ExtendedRandomSpreadStructurePlacement;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.BannditWoodTextureStructureProcessor;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.IgnoreWaterloggingStructureProcessor;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.RuleWithCopyStructureProcessor;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.StoneTextureStructureProcessor;

public class ModStructures {
   public static final RegistryObject<StructurePlacementType<ExtendedRandomSpreadStructurePlacement>> EXTENDED_RANDOM_SPREAD_PLACEMENT = ModRegistry.<StructurePlacementType<ExtendedRandomSpreadStructurePlacement>>registerStructurePlacement("extended_random_spread", () -> () -> ExtendedRandomSpreadStructurePlacement.CODEC);
   public static final RegistryObject<StructureProcessorType<RuleWithCopyStructureProcessor>> RULE_WITH_COPY_PROCESSOR = ModRegistry.<StructureProcessorType<RuleWithCopyStructureProcessor>>registerStructureProcessor("rule_with_copy", () -> () -> RuleWithCopyStructureProcessor.CODEC);
   public static final RegistryObject<StructureProcessorType<IgnoreWaterloggingStructureProcessor>> IGNORE_WATERLOGGING_PROCESSOR = ModRegistry.<StructureProcessorType<IgnoreWaterloggingStructureProcessor>>registerStructureProcessor("ignore_waterlogging", () -> () -> IgnoreWaterloggingStructureProcessor.CODEC);
   public static final RegistryObject<StructureProcessorType<StoneTextureStructureProcessor>> STONE_TEXTURE_PROCESSOR = ModRegistry.<StructureProcessorType<StoneTextureStructureProcessor>>registerStructureProcessor("stone_texture", () -> () -> StoneTextureStructureProcessor.CODEC);
   public static final RegistryObject<StructureProcessorType<BannditWoodTextureStructureProcessor>> BANDIT_WOOD_TEXTURE_PROCESSOR = ModRegistry.<StructureProcessorType<BannditWoodTextureStructureProcessor>>registerStructureProcessor("bandit_wood_texture", () -> () -> BannditWoodTextureStructureProcessor.CODEC);

   public static void init() {
   }
}
