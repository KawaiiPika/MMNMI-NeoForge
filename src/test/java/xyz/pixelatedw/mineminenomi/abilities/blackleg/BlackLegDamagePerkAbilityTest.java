package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

import static org.junit.jupiter.api.Assertions.*;

class BlackLegDamagePerkAbilityTest {

    @BeforeAll
    static void setup() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Ignore bootstrap issues if already bootstrapped
        }
    }

    @Test
    void testIsPassive() {
        BlackLegDamagePerkAbility ability = new BlackLegDamagePerkAbility();
        assertTrue(ability.isPassive(), "BlackLegDamagePerkAbility should be passive");
    }

    @Test
    void testGetDisplayName() {
        BlackLegDamagePerkAbility ability = new BlackLegDamagePerkAbility();
        assertEquals("Black Leg Damage Perk", ability.getDisplayName().getString(), "Display name should be 'Black Leg Damage Perk'");
    }
}
