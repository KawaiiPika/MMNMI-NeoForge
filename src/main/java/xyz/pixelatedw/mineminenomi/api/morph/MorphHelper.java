package xyz.pixelatedw.mineminenomi.api.morph;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.MorphData;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MorphHelper {

    public static void startMorph(LivingEntity entity, ResourceLocation morphModelId) {
        if (entity.hasData(ModDataAttachments.MORPH_DATA)) {
            MorphData morphData = entity.getData(ModDataAttachments.MORPH_DATA);
            List<ResourceLocation> activeMorphs = new ArrayList<>(morphData.activeMorphs());
            if (!activeMorphs.contains(morphModelId)) {
                activeMorphs.add(morphModelId);
            }
            entity.setData(ModDataAttachments.MORPH_DATA, new MorphData(Optional.of(morphModelId), activeMorphs));
        } else {
            List<ResourceLocation> activeMorphs = new ArrayList<>();
            activeMorphs.add(morphModelId);
            entity.setData(ModDataAttachments.MORPH_DATA, new MorphData(Optional.of(morphModelId), activeMorphs));
        }
    }

    public static void stopMorph(LivingEntity entity, ResourceLocation morphModelId) {
        if (entity.hasData(ModDataAttachments.MORPH_DATA)) {
            MorphData morphData = entity.getData(ModDataAttachments.MORPH_DATA);
            List<ResourceLocation> activeMorphs = new ArrayList<>(morphData.activeMorphs());
            activeMorphs.remove(morphModelId);
            Optional<ResourceLocation> current = activeMorphs.isEmpty() ? Optional.empty() : Optional.of(activeMorphs.get(0));
            entity.setData(ModDataAttachments.MORPH_DATA, new MorphData(current, activeMorphs));
        }
    }
}
