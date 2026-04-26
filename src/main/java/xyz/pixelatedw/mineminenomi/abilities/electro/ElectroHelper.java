package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ElectroHelper {

    public static boolean hasSulongActive(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive("mineminenomi:sulong");
    }

    public static boolean canTransform(Level level) {
        // Full moon and night
        return !level.isDay() && level.getMoonPhase() == 0;
    }
}
