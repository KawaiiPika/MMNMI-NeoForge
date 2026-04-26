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
        
        int lastProcessedDoriki = stats.getBasic().trainingPoints().getOrDefault("LAST_PROCESSED_DORIKI", 0);
        int currentTier = (int) (doriki / 1000);
        int currentTier = Math.min((int) (doriki / 1000), 10);
        int lastTier = lastProcessedDoriki / 1000;

        if (currentTier > lastTier) {
            int tiersGained = currentTier - lastTier;
            for (TrainingPointType type : TrainingPointType.values()) {
                stats.alterTrainingPoints(type, tiersGained);
            }
                        java.util.Map<String, Integer> pointsMap = new java.util.HashMap<>(stats.getBasic().trainingPoints());
                // Assuming legacy cap is implicitly handled by Doriki max tier = 10, total points per type = 10.
                stats.alterTrainingPoints(type, tiersGained);
            }
            java.util.Map<String, Integer> pointsMap = new java.util.HashMap<>(stats.getBasic().trainingPoints());
            pointsMap.put("LAST_PROCESSED_DORIKI", currentTier * 1000);
            stats.setBasic(new PlayerStats.BasicStats(
                stats.getBasic().doriki(), stats.getBasic().cola(), stats.getBasic().ultraCola(), stats.getBasic().loyalty(),
                stats.getBasic().bounty(), stats.getBasic().belly(), stats.getBasic().extol(), stats.getBasic().identity(),
                stats.getBasic().hasShadow(), stats.getBasic().hasHeart(), stats.getBasic().hasStrawDoll(), stats.getBasic().isRogue(),
                stats.getBasic().stamina(), stats.getBasic().maxStamina(), pointsMap
            ));
            stats.sync(player);
        }
    }

    public static boolean spendTrainingPoints(Player player, TrainingPointType type, int amount) {
        PlayerStats stats = PlayerStats.get(player);
        int currentPoints = stats.getTrainingPoints(type);
        if (currentPoints >= amount) {
            stats.setTrainingPoints(type, currentPoints - amount);
            stats.sync(player);
            return true;
        }
        return false;
    }

    }

    public static boolean spendTrainingPoints(Player player, TrainingPointType type, int amount) {
        PlayerStats stats = PlayerStats.get(player);
        int currentPoints = stats.getTrainingPoints(type);
        if (currentPoints >= amount) {
            stats.setTrainingPoints(type, currentPoints - amount);
            stats.sync(player);
            return true;
        }
        return false;
    }


    public static void grantTrainingPoints(Player player, TrainingPointType type, int amount) {
        PlayerStats stats = PlayerStats.get(player);
        // Ensure points don't exceed the logical max tier based on doriki limit (10 points max per category usually)
        int currentPoints = stats.getTrainingPoints(type);
        if (currentPoints + amount > 10) {
            amount = 10 - currentPoints;
        }
        if (amount > 0) {
            stats.alterTrainingPoints(type, amount);
            stats.sync(player);

            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                xyz.pixelatedw.mineminenomi.networking.ModNetworking.sendTo(
                    new xyz.pixelatedw.mineminenomi.networking.packets.SPointsGainedPacket(amount, type.name()),
                    serverPlayer
                );
            } else {
                player.displayClientMessage(net.minecraft.network.chat.Component.translatable("info.mineminenomi.points_gained", amount, type.name()), true);
            }
        }
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
