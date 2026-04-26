package xyz.pixelatedw.mineminenomi.services;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.builder.TestEntityBuilder;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class ProgressionServiceTest {

    private Player player;
    private PlayerStats stats;
    private MockedStatic<PlayerStats> playerStatsMockedStatic;

    @BeforeAll
    static void setupAll() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Ignore bootstrap issues in headless environment
        }
    }

    @BeforeEach
    void setup() {
        TestEntityBuilder builder = TestEntityBuilder.instance().withDoriki(0);
        player = builder.build();
        stats = builder.getStats();

        playerStatsMockedStatic = Mockito.mockStatic(PlayerStats.class);
        playerStatsMockedStatic.when(() -> PlayerStats.get(player)).thenReturn(stats);
    }

    @AfterEach
    void tearDown() {
        playerStatsMockedStatic.close();
    }

    @Test
    void testGrantTrainingPoints() {
        TrainingPointType type = TrainingPointType.MARTIAL_ARTS;
        int amount = 5;

        ProgressionService.grantTrainingPoints(player, type, amount);

        assertEquals(5, stats.getTrainingPoints(type));
    }

    @Test
    void testGrantTrainingPointsMax10() {
        TrainingPointType type = TrainingPointType.MARTIAL_ARTS;
        int amount = 15;

        ProgressionService.grantTrainingPoints(player, type, amount);

        assertEquals(10, stats.getTrainingPoints(type));
    }

    @Test
    void testCheckProgressionEarnsPoints() {
        stats.setDoriki(2500); // Should earn 2 points for each type

        ProgressionService.checkProgression(player);

        assertEquals(2, stats.getTrainingPoints(TrainingPointType.MARTIAL_ARTS));
        assertEquals(2, stats.getTrainingPoints(TrainingPointType.WEAPON_MASTERY));
        assertEquals(2000, stats.getBasic().trainingPoints().getOrDefault("LAST_PROCESSED_DORIKI", 0));
    }

    @Test
    void testCheckProgressionMaxTier() {
        stats.setDoriki(15000); // Exceeds 10000

        ProgressionService.checkProgression(player);

        assertEquals(10, stats.getTrainingPoints(TrainingPointType.MARTIAL_ARTS));
        assertEquals(10000, stats.getBasic().trainingPoints().getOrDefault("LAST_PROCESSED_DORIKI", 0));
    }

    @Test
    void testCheckProgressionDoesNotRegrantPoints() {
        stats.setDoriki(2500);
        ProgressionService.checkProgression(player);
        assertEquals(2, stats.getTrainingPoints(TrainingPointType.MARTIAL_ARTS));

        stats.setDoriki(2999);
        ProgressionService.checkProgression(player);
        assertEquals(2, stats.getTrainingPoints(TrainingPointType.MARTIAL_ARTS)); // Still 2
        assertEquals(2000, stats.getBasic().trainingPoints().getOrDefault("LAST_PROCESSED_DORIKI", 0));
    }

    @Test
    void testSpendTrainingPoints() {
        stats.setTrainingPoints(TrainingPointType.MARTIAL_ARTS, 5);

        boolean success = ProgressionService.spendTrainingPoints(player, TrainingPointType.MARTIAL_ARTS, 3);

        assertTrue(success);
        assertEquals(2, stats.getTrainingPoints(TrainingPointType.MARTIAL_ARTS));
    }

    @Test
    void testSpendTrainingPointsInsufficient() {
        stats.setTrainingPoints(TrainingPointType.MARTIAL_ARTS, 2);

        boolean success = ProgressionService.spendTrainingPoints(player, TrainingPointType.MARTIAL_ARTS, 3);

        assertFalse(success);
        assertEquals(2, stats.getTrainingPoints(TrainingPointType.MARTIAL_ARTS));
    }
}
