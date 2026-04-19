package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.level.structure.placement.ExtendedRandomSpreadStructurePlacement;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.BanditWoodTextureStructureProcessor;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.IgnoreWaterloggingStructureProcessor;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.RuleWithCopyStructureProcessor;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.StoneTextureStructureProcessor;

import java.util.function.Supplier;

public class ModStructures {
    public static final DeferredRegister<StructurePlacementType<?>> PLACEMENT_TYPES = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, ModMain.PROJECT_ID);
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, ModMain.PROJECT_ID);

    public static final Supplier<StructurePlacementType<ExtendedRandomSpreadStructurePlacement>> EXTENDED_RANDOM_SPREAD_PLACEMENT = PLACEMENT_TYPES.register("extended_random_spread", () -> () -> ExtendedRandomSpreadStructurePlacement.MAP_CODEC);

    public static final Supplier<StructureProcessorType<IgnoreWaterloggingStructureProcessor>> IGNORE_WATERLOGGING_PROCESSOR = PROCESSOR_TYPES.register("ignore_waterlogging", () -> () -> IgnoreWaterloggingStructureProcessor.MAP_CODEC);
    public static final Supplier<StructureProcessorType<StoneTextureStructureProcessor>> STONE_TEXTURE_PROCESSOR = PROCESSOR_TYPES.register("stone_texture", () -> () -> StoneTextureStructureProcessor.MAP_CODEC);
    public static final Supplier<StructureProcessorType<RuleWithCopyStructureProcessor>> RULE_WITH_COPY_PROCESSOR = PROCESSOR_TYPES.register("rule_with_copy", () -> () -> RuleWithCopyStructureProcessor.MAP_CODEC);
    public static final Supplier<StructureProcessorType<BanditWoodTextureStructureProcessor>> BANDIT_WOOD_TEXTURE_PROCESSOR = PROCESSOR_TYPES.register("bandit_wood_texture", () -> () -> BanditWoodTextureStructureProcessor.MAP_CODEC);

    public static void register(IEventBus bus) {
        PLACEMENT_TYPES.register(bus);
        PROCESSOR_TYPES.register(bus);
    }
}
