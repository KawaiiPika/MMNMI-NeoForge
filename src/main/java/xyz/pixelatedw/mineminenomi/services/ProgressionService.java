package xyz.pixelatedw.mineminenomi.services;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class ProgressionService {

    /**
     * Handles the progression logic for a player.
     * This should be called whenever Doriki changes.
     */
    public static void checkProgression(Player player) {
        PlayerStats stats = PlayerStats.get(player);
        double doriki = stats.getDoriki();
        
        // Example logic: Grant 1 point of each type every 1000 Doriki
        // In a real scenario, this would track 'last processed doriki' to avoid re-granting.
        // For now, we'll just provide the helper methods for other systems to use.
    }

    public static void grantTrainingPoints(Player player, TrainingPointType type, int amount) {
        PlayerStats stats = PlayerStats.get(player);
        stats.alterTrainingPoints(type, amount);
        stats.sync(player);
        
        // Optional: Send a message to the player
        // player.displayClientMessage(Component.translatable("info.mineminenomi.points_gained", amount, type.name()), true);
    }

    /**
     * Ported 'level up' logic. In legacy, this might have been part of specific events.
     * Here we centralize it.
     */
    public static void levelUp(Player player) {
        // Grant a bundle of points on significant milestones
        for (TrainingPointType type : TrainingPointType.values()) {
            grantTrainingPoints(player, type, 1);
        }
    }
}
