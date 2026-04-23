package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class StructuresHelper {
    public static StructureStart getStructureAt(ServerLevel world, BlockPos pos) {
        for (Map.Entry<ResourceKey<Structure>, Structure> entry : world.registryAccess().registryOrThrow(Registries.STRUCTURE).entrySet()) {
            StructureStart struct = world.structureManager().getStructureAt(pos, entry.getValue());
            if (struct != null && struct.isValid()) {
                return struct;
            }
        }
        return null;
    }
}
