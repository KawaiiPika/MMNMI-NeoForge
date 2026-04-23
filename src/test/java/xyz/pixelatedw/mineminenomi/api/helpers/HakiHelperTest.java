package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.pixelatedw.mineminenomi.builder.TestEntityBuilder;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HakiHelperTest extends xyz.pixelatedw.mineminenomi.effects.AbstractMinecraftTest {

    @Test
    void testCalculateHakiDamage_NoStats() {
        LivingEntity attacker = mock(LivingEntity.class);
        LivingEntity target = mock(LivingEntity.class);

        when(attacker.getData(ModDataAttachments.PLAYER_STATS)).thenReturn(null);

        float originalAmount = 10.0f;
        float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

        assertEquals(originalAmount, result, "Should return original amount if PlayerStats is null");
    }

    @Test
    void testCalculateHakiDamage_NotActive() {
        TestEntityBuilder builder = TestEntityBuilder.instance().withBusoshokuActive(false);
        Player attacker = builder.build();
        LivingEntity target = mock(LivingEntity.class);

        float originalAmount = 10.0f;
        float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

        assertEquals(originalAmount, result, "Should return original amount if Busoshoku is not active");
    }

    @Test
    void testCalculateHakiDamage_ActiveBase() {
        TestEntityBuilder builder = TestEntityBuilder.instance()
                .withBusoshokuActive(true)
                .withBusoshokuHakiExp(2500.0f); // 2500 / 5000 = 0.5 boost
        Player attacker = builder.build();
        LivingEntity target = mock(LivingEntity.class);

        float originalAmount = 10.0f;
        float expectedBoost = 1.0f + (2500.0f / 5000.0f);
        float expectedAmount = originalAmount * expectedBoost;

        float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

        assertEquals(expectedAmount, result, 0.01f, "Should calculate boost correctly based on Haki exp");
        assertEquals(2500.0f + 0.1f, builder.getStats().getBusoshokuHakiExp(), 0.001f);
    }

    @Test
    void testCalculateHakiDamage_WithImbuing() {
        TestEntityBuilder builder = TestEntityBuilder.instance()
                .withBusoshokuActive(true)
                .withBusoshokuHakiExp(0.0f)
                .withActiveAbility("mineminenomi:busoshoku_haki_imbuing");
        Player attacker = builder.build();
        LivingEntity target = mock(LivingEntity.class);

        float originalAmount = 10.0f;
        float expectedBoost = 1.0f + 0.5f; // base + imbuing
        float expectedAmount = originalAmount * expectedBoost;

        float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

        assertEquals(expectedAmount, result, 0.01f, "Should add 0.5 to boost when imbuing is active");
    }

    @Test
    void testCalculateHakiDamage_WithHaoshokuInfusion() {
        TestEntityBuilder builder = TestEntityBuilder.instance()
                .withBusoshokuActive(true)
                .withBusoshokuHakiExp(0.0f)
                .withActiveAbility("mineminenomi:haoshoku_haki_infusion");
        Player attacker = builder.build();
        LivingEntity target = mock(LivingEntity.class);

        when(target.level()).thenReturn(mock(net.minecraft.world.level.Level.class));

        float originalAmount = 10.0f;
        float expectedBoost = 1.0f + 1.5f; // base + haoshoku
        float expectedAmount = originalAmount * expectedBoost;

        float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

        assertEquals(expectedAmount, result, 0.01f, "Should add 1.5 to boost when haoshoku infusion is active");
    }

    @Test
    void testCalculateHakiDamage_WithBothAbilities() {
        TestEntityBuilder builder = TestEntityBuilder.instance()
                .withBusoshokuActive(true)
                .withBusoshokuHakiExp(5000.0f) // Max exp
                .withActiveAbility("mineminenomi:busoshoku_haki_imbuing")
                .withActiveAbility("mineminenomi:haoshoku_haki_infusion");
        Player attacker = builder.build();
        LivingEntity target = mock(LivingEntity.class);

        when(target.level()).thenReturn(mock(net.minecraft.world.level.Level.class));

        float originalAmount = 10.0f;
        float expectedBoost = 1.0f + 1.0f + 0.5f + 1.5f; // base + exp + imbuing + haoshoku
        float expectedAmount = originalAmount * expectedBoost;

        float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

        assertEquals(expectedAmount, result, 0.01f, "Should add all boosts together correctly");
    }
}
