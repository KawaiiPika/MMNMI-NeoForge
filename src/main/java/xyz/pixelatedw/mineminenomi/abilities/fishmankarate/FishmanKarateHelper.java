package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.world.entity.LivingEntity;

public class FishmanKarateHelper {
    public static boolean isInWater(LivingEntity entity) {
        boolean isInWater = entity.isInWaterOrBubble();

        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        if (stats != null) {
            if (stats.isAbilityActive("mineminenomi:free_swimming")) {
                return true;
            }
        }
        return isInWater;
    }
}
