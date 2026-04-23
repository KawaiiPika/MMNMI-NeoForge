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

    @BeforeAll
    static void setupAll() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Ignore bootstrap issues in headless environment
        }
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
    }
}
