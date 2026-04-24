package xyz.pixelatedw.mineminenomi.datagen.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.level.structure.placement.ExtendedRandomSpreadStructurePlacement;

import java.util.List;

public class ModStructureSetsData {
    public static final ResourceKey<StructureSet> LARGE_BASES = createKey("large_bases");
    public static final ResourceKey<StructureSet> LARGE_SHIPS = createKey("large_ships");
    public static final ResourceKey<StructureSet> MEDIUM_SHIPS = createKey("medium_ships");
    public static final ResourceKey<StructureSet> SMALL_BASES = createKey("small_bases");
    public static final ResourceKey<StructureSet> SMALL_SHIPS = createKey("small_ships");
    public static final ResourceKey<StructureSet> TOWERS = createKey("towers");

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

        context.register(LARGE_BASES, new StructureSet(
                List.of(StructureSet.entry(structures.getOrThrow(ModStructuresData.MARINE_LARGE_BASE))),
                new ExtendedRandomSpreadStructurePlacement(
                        net.minecraft.core.Vec3i.ZERO,
                        net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod.DEFAULT,
                        0.8F,
                        984327123,
                        32,
                        16,
                        RandomSpreadType.LINEAR,
                        List.of(),
                        0
                )
        ));

        context.register(LARGE_SHIPS, new StructureSet(
                List.of(
                        StructureSet.entry(structures.getOrThrow(ModStructuresData.PIRATE_LARGE_SHIP)),
                        StructureSet.entry(structures.getOrThrow(ModStructuresData.MARINE_BATTLESHIP))
                ),
                new RandomSpreadStructurePlacement(54, 32, RandomSpreadType.LINEAR, 51237312)
        ));

        context.register(MEDIUM_SHIPS, new StructureSet(
                List.of(
                        StructureSet.entry(structures.getOrThrow(ModStructuresData.PIRATE_MEDIUM_SHIP)),
                        StructureSet.entry(structures.getOrThrow(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "unaligned/ghost_ship"))))
                ),
                new RandomSpreadStructurePlacement(48, 24, RandomSpreadType.LINEAR, 219582103)
        ));

        context.register(SMALL_BASES, new StructureSet(
                List.of(
                        StructureSet.entry(structures.getOrThrow(ModStructuresData.MARINE_SMALL_BASE)),
                        StructureSet.entry(structures.getOrThrow(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "bandit/house")))),
                        StructureSet.entry(structures.getOrThrow(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "bandit/arena")))),
                        StructureSet.entry(structures.getOrThrow(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "bandit/fort"))))
                ),
                new ExtendedRandomSpreadStructurePlacement(
                        net.minecraft.core.Vec3i.ZERO,
                        net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod.DEFAULT,
                        0.9F,
                        34810239,
                        24,
                        8,
                        RandomSpreadType.LINEAR,
                        List.of(
                            new ExtendedRandomSpreadStructurePlacement.SuperExclusionZone(
                                context.lookup(Registries.STRUCTURE_SET).getOrThrow(LARGE_BASES), 12
                            )
                        ),
                        0
                )
        ));

        context.register(SMALL_SHIPS, new StructureSet(
                List.of(
                        StructureSet.entry(structures.getOrThrow(ModStructuresData.PIRATE_SMALL_SHIP))
                ),
                new RandomSpreadStructurePlacement(36, 16, RandomSpreadType.LINEAR, 510293123)
        ));

        context.register(TOWERS, new StructureSet(
                List.of(
                        StructureSet.entry(structures.getOrThrow(ModStructuresData.MARINE_WATCH_TOWER))
                ),
                new ExtendedRandomSpreadStructurePlacement(
                        net.minecraft.core.Vec3i.ZERO,
                        net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod.DEFAULT,
                        0.9F,
                        510293123,
                        16,
                        8,
                        RandomSpreadType.LINEAR,
                        List.of(
                            new ExtendedRandomSpreadStructurePlacement.SuperExclusionZone(
                                context.lookup(Registries.STRUCTURE_SET).getOrThrow(LARGE_BASES), 12
                            ),
                            new ExtendedRandomSpreadStructurePlacement.SuperExclusionZone(
                                context.lookup(Registries.STRUCTURE_SET).getOrThrow(SMALL_BASES), 8
                            )
                        ),
                        0
                )
        ));
    }

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, name));
    }
}
