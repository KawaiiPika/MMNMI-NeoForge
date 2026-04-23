package xyz.pixelatedw.mineminenomi.services;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.player.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import static org.mockito.Mockito.*;

class ProgressionServiceTest {

    private Player player;
    private PlayerStats stats;

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
        player = mock(Player.class);
        stats = mock(PlayerStats.class);
    }

    @Test
    void testGrantTrainingPoints() {
        TrainingPointType type = TrainingPointType.MARTIAL_ARTS;
        int amount = 5;

        try (MockedStatic<PlayerStats> playerStatsMockedStatic = Mockito.mockStatic(PlayerStats.class)) {
            playerStatsMockedStatic.when(() -> PlayerStats.get(player)).thenReturn(stats);

            ProgressionService.grantTrainingPoints(player, type, amount);

            verify(stats).alterTrainingPoints(type, amount);
            verify(stats).sync(player);
        }
    }
}
