package xyz.pixelatedw.mineminenomi.builder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import static org.junit.jupiter.api.Assertions.*;

public class TestEntityBuilderTest extends xyz.pixelatedw.mineminenomi.AbstractMinecraftTest {

    @Test
    public void testBuilder() {
        ResourceLocation testFruit = ResourceLocation.fromNamespaceAndPath("mineminenomi", "test_fruit");
        ResourceLocation testFaction = ResourceLocation.fromNamespaceAndPath("mineminenomi", "pirate");

        Player player = TestEntityBuilder.instance()
                .withDoriki(1500)
                .withBusoshokuHakiExp(100)
                .withDevilFruit(testFruit)
                .withFaction(testFaction)
                .build();

        PlayerStats stats = PlayerStats.get(player);

        assertNotNull(stats);
        assertEquals(1500, stats.getBasic().doriki());
        assertEquals(100, stats.getCombat().busoshokuHakiExp());
        assertEquals(testFruit, stats.getBasic().identity().devilFruit().orElse(null));
        assertEquals(testFaction, stats.getBasic().identity().faction().orElse(null));
    }
}
