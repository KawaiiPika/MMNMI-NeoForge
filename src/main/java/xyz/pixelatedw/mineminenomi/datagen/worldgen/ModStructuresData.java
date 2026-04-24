package xyz.pixelatedw.mineminenomi.datagen.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import xyz.pixelatedw.mineminenomi.ModMain;

import java.util.Map;

public class ModStructuresData {
    public static final ResourceKey<Structure> MARINE_BATTLESHIP = createKey("marine/battleship");
    public static final ResourceKey<Structure> MARINE_LARGE_BASE = createKey("marine/large_base");
    public static final ResourceKey<Structure> MARINE_SMALL_BASE = createKey("marine/small_base");
    public static final ResourceKey<Structure> MARINE_WATCH_TOWER = createKey("marine/watch_tower");

    public static final ResourceKey<Structure> PIRATE_LARGE_SHIP = createKey("pirate/large_ship");
    public static final ResourceKey<Structure> PIRATE_MEDIUM_SHIP = createKey("pirate/medium_ship");
    public static final ResourceKey<Structure> PIRATE_SMALL_SHIP = createKey("pirate/small_ship");

    public static final TagKey<Biome> HAS_STRUCTURE_LARGE_BASE = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "has_structure/large_base"));
    public static final TagKey<Biome> HAS_STRUCTURE_SMALL_BASE = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "has_structure/small_base"));
    public static final TagKey<Biome> HAS_STRUCTURE_WATCH_TOWER = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "has_structure/watch_tower"));

    public static void bootstrap(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);

        context.register(MARINE_BATTLESHIP, new JigsawStructure(
                new Structure.StructureSettings(biomes.getOrThrow(BiomeTags.IS_DEEP_OCEAN), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "marine/battleship/front_hull"))),
                3,
                ConstantHeight.of(net.minecraft.world.level.levelgen.VerticalAnchor.absolute(-9)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));

        context.register(MARINE_LARGE_BASE, new JigsawStructure(
                new Structure.StructureSettings(biomes.getOrThrow(HAS_STRUCTURE_LARGE_BASE), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "marine/large_base/ground_floors"))),
                5,
                ConstantHeight.of(net.minecraft.world.level.levelgen.VerticalAnchor.absolute(-1)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));

        context.register(MARINE_SMALL_BASE, new JigsawStructure(
                new Structure.StructureSettings(biomes.getOrThrow(HAS_STRUCTURE_SMALL_BASE), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "marine/small_base/ground_floors"))),
                5,
                ConstantHeight.of(net.minecraft.world.level.levelgen.VerticalAnchor.absolute(-1)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));

        context.register(MARINE_WATCH_TOWER, new JigsawStructure(
                new Structure.StructureSettings(biomes.getOrThrow(HAS_STRUCTURE_WATCH_TOWER), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "marine/watch_tower/ground_floors"))),
                5,
                ConstantHeight.of(net.minecraft.world.level.levelgen.VerticalAnchor.absolute(-1)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));

        context.register(PIRATE_LARGE_SHIP, new JigsawStructure(
                new Structure.StructureSettings(biomes.getOrThrow(BiomeTags.IS_DEEP_OCEAN), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "pirate/large_ship/front_hull"))),
                3,
                ConstantHeight.of(net.minecraft.world.level.levelgen.VerticalAnchor.absolute(-9)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));

        context.register(PIRATE_MEDIUM_SHIP, new JigsawStructure(
                new Structure.StructureSettings(biomes.getOrThrow(BiomeTags.IS_DEEP_OCEAN), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "pirate/medium_ship/front_hull"))),
                3,
                ConstantHeight.of(net.minecraft.world.level.levelgen.VerticalAnchor.absolute(-9)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));

        context.register(PIRATE_SMALL_SHIP, new JigsawStructure(
                new Structure.StructureSettings(biomes.getOrThrow(BiomeTags.IS_DEEP_OCEAN), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "pirate/small_ship/front_hull"))),
                2,
                ConstantHeight.of(net.minecraft.world.level.levelgen.VerticalAnchor.absolute(-9)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        ));
    }

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, name));
    }
}
