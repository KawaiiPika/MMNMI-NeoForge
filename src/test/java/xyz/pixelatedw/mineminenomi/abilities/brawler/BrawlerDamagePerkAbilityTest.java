package xyz.pixelatedw.mineminenomi.abilities.brawler;

import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;
import static org.junit.jupiter.api.Assertions.*;
import net.minecraft.network.chat.Component;

class BrawlerDamagePerkAbilityTest extends AbstractMinecraftTest {
    @Test
    void testIsPassive() {
        BrawlerDamagePerkAbility ability = new BrawlerDamagePerkAbility();
        assertTrue(ability.isPassive(), "BrawlerDamagePerkAbility should be passive");
    }

    @Test
    void testGetDisplayName() {
        BrawlerDamagePerkAbility ability = new BrawlerDamagePerkAbility();
        Component displayName = ability.getDisplayName();
        assertNotNull(displayName, "Display name should not be null");
        assertEquals("Brawler Damage Perk", displayName.getString(), "Display name should match expected");
    }
}
