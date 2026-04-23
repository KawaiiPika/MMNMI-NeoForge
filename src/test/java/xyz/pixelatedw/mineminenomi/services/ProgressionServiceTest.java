package xyz.pixelatedw.mineminenomi.services;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.player.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.builder.TestEntityBuilder;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgressionServiceTest extends xyz.pixelatedw.mineminenomi.AbstractMinecraftTest {
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
        TestEntityBuilder builder = TestEntityBuilder.instance();
        Player player = builder.build();
        PlayerStats stats = builder.getStats();

        TrainingPointType type = TrainingPointType.MARTIAL_ARTS;
        int amount = 5;

        int initialPoints = stats.getTrainingPoints(type);

        ProgressionService.grantTrainingPoints(player, type, amount);

        int newPoints = stats.getTrainingPoints(type);

        assertEquals(initialPoints + amount, newPoints);
        try (MockedStatic<PlayerStats> playerStatsMockedStatic = Mockito.mockStatic(PlayerStats.class)) {
            playerStatsMockedStatic.when(() -> PlayerStats.get(player)).thenReturn(stats);

            ProgressionService.grantTrainingPoints(player, type, amount);

            verify(stats).alterTrainingPoints(type, amount);
            verify(stats).sync(player);
        }
    }
}
