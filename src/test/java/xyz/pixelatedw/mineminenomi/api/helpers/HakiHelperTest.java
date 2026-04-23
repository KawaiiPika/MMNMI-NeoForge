package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

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

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(null);

            float originalAmount = 10.0f;
            float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

            assertEquals(originalAmount, result, "Should return original amount if PlayerStats is null");
        }
    }

    @Test
    void testCalculateHakiDamage_NotActive() {
        LivingEntity attacker = mock(LivingEntity.class);
        LivingEntity target = mock(LivingEntity.class);
        PlayerStats stats = mock(PlayerStats.class);

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(stats);
            when(stats.isBusoshokuActive()).thenReturn(false);

            float originalAmount = 10.0f;
            float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

            assertEquals(originalAmount, result, "Should return original amount if Busoshoku is not active");
        }
    }

    @Test
    void testCalculateHakiDamage_ActiveBase() {
        LivingEntity attacker = mock(LivingEntity.class);
        LivingEntity target = mock(LivingEntity.class);
        PlayerStats stats = mock(PlayerStats.class);

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(stats);
            when(stats.isBusoshokuActive()).thenReturn(true);
            when(stats.getBusoshokuHakiExp()).thenReturn(2500.0f); // 2500 / 5000 = 0.5 boost
            when(stats.isAbilityActive("mineminenomi:busoshoku_haki_imbuing")).thenReturn(false);
            when(stats.isAbilityActive("mineminenomi:haoshoku_haki_infusion")).thenReturn(false);

            float originalAmount = 10.0f;
            float expectedBoost = 1.0f + (2500.0f / 5000.0f);
            float expectedAmount = originalAmount * expectedBoost;

            float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

            assertEquals(expectedAmount, result, 0.01f, "Should calculate boost correctly based on Haki exp");
            verify(stats).setBusoshokuHakiExp(2500.0f + 0.1f);
            verify(stats).sync(attacker);
        }
    }

    @Test
    void testCalculateHakiDamage_WithImbuing() {
        LivingEntity attacker = mock(LivingEntity.class);
        LivingEntity target = mock(LivingEntity.class);
        PlayerStats stats = mock(PlayerStats.class);

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(stats);
            when(stats.isBusoshokuActive()).thenReturn(true);
            when(stats.getBusoshokuHakiExp()).thenReturn(0.0f);
            when(stats.isAbilityActive("mineminenomi:busoshoku_haki_imbuing")).thenReturn(true);
            when(stats.isAbilityActive("mineminenomi:haoshoku_haki_infusion")).thenReturn(false);

            float originalAmount = 10.0f;
            float expectedBoost = 1.0f + 0.5f; // base + imbuing
            float expectedAmount = originalAmount * expectedBoost;

            float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

            assertEquals(expectedAmount, result, 0.01f, "Should add 0.5 to boost when imbuing is active");
        }
    }

    @Test
    void testCalculateHakiDamage_WithHaoshokuInfusion() {
        LivingEntity attacker = mock(LivingEntity.class);
        LivingEntity target = mock(LivingEntity.class);
        PlayerStats stats = mock(PlayerStats.class);

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(stats);
            when(stats.isBusoshokuActive()).thenReturn(true);
            when(stats.getBusoshokuHakiExp()).thenReturn(0.0f);
            when(stats.isAbilityActive("mineminenomi:busoshoku_haki_imbuing")).thenReturn(false);
            when(stats.isAbilityActive("mineminenomi:haoshoku_haki_infusion")).thenReturn(true);

            // We mock the level call on target since it attempts to access the ServerLevel inside HakiHelper
            when(target.level()).thenReturn(mock(net.minecraft.world.level.Level.class));

            float originalAmount = 10.0f;
            float expectedBoost = 1.0f + 1.5f; // base + haoshoku
            float expectedAmount = originalAmount * expectedBoost;

            float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

            assertEquals(expectedAmount, result, 0.01f, "Should add 1.5 to boost when haoshoku infusion is active");
        }
    }

    @Test
    void testIsWeakenedByHaoshoku_UserStronger() {
        LivingEntity user = mock(LivingEntity.class);
        LivingEntity target = mock(LivingEntity.class);
        PlayerStats userStats = mock(PlayerStats.class);
        PlayerStats targetStats = mock(PlayerStats.class);

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(user)).thenReturn(userStats);
            mockedStats.when(() -> PlayerStats.get(target)).thenReturn(targetStats);

            when(userStats.getDoriki()).thenReturn(10000.0);
            when(targetStats.getDoriki()).thenReturn(1000.0);

            boolean result = HakiHelper.isWeakenedByHaoshoku(user, target);

            assertEquals(true, result, "Target should be weakened if user power > target power * 1.5");
        }
    }

    @Test
    void testIsWeakenedByHaoshoku_UserWeaker() {
        LivingEntity user = mock(LivingEntity.class);
        LivingEntity target = mock(LivingEntity.class);
        PlayerStats userStats = mock(PlayerStats.class);
        PlayerStats targetStats = mock(PlayerStats.class);

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(user)).thenReturn(userStats);
            mockedStats.when(() -> PlayerStats.get(target)).thenReturn(targetStats);

            when(userStats.getDoriki()).thenReturn(1000.0);
            when(targetStats.getDoriki()).thenReturn(10000.0);

            boolean result = HakiHelper.isWeakenedByHaoshoku(user, target);

            assertEquals(false, result, "Target should not be weakened if user power < target power * 1.5");
        }
    }

    @Test
    void testTickHaki_ActiveHakiDrainStamina() {
        LivingEntity entity = mock(LivingEntity.class);
        PlayerStats stats = mock(PlayerStats.class);
        net.minecraft.world.level.Level level = mock(net.minecraft.world.level.Level.class);

        when(entity.level()).thenReturn(level);
        // Level is not client side
        // Default isClientSide = false for mock

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(entity)).thenReturn(stats);

            when(stats.isBusoshokuActive()).thenReturn(true);
            when(stats.getStamina()).thenReturn(10.0);

            HakiHelper.tickHaki(entity);

            verify(stats).setStamina(9.5); // 10.0 - 0.5
            verify(stats).sync(entity);
        }
    }

    @Test
    void testTickHaki_InactiveHakiRecoverStamina() {
        LivingEntity entity = mock(LivingEntity.class);
        PlayerStats stats = mock(PlayerStats.class);
        net.minecraft.world.level.Level level = mock(net.minecraft.world.level.Level.class);

        when(entity.level()).thenReturn(level);

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(entity)).thenReturn(stats);

            when(stats.isBusoshokuActive()).thenReturn(false);
            when(stats.isKenbunshokuActive()).thenReturn(false);
            when(stats.getStamina()).thenReturn(5.0);
            when(stats.getMaxStamina()).thenReturn(10.0);

            HakiHelper.tickHaki(entity);

            verify(stats).setStamina(5.2); // 5.0 + 0.2
            verify(stats).sync(entity);
        }
    }

    @Test
    void testCalculateHakiDamage_WithBothAbilities() {
        LivingEntity attacker = mock(LivingEntity.class);
        LivingEntity target = mock(LivingEntity.class);
        PlayerStats stats = mock(PlayerStats.class);

        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(stats);
            when(stats.isBusoshokuActive()).thenReturn(true);
            when(stats.getBusoshokuHakiExp()).thenReturn(5000.0f); // Max exp
            when(stats.isAbilityActive("mineminenomi:busoshoku_haki_imbuing")).thenReturn(true);
            when(stats.isAbilityActive("mineminenomi:haoshoku_haki_infusion")).thenReturn(true);

            // Mock the level call on target
            when(target.level()).thenReturn(mock(net.minecraft.world.level.Level.class));

            float originalAmount = 10.0f;
            float expectedBoost = 1.0f + 1.0f + 0.5f + 1.5f; // base + exp + imbuing + haoshoku
            float expectedAmount = originalAmount * expectedBoost;

            float result = HakiHelper.calculateHakiDamage(attacker, target, originalAmount);

            assertEquals(expectedAmount, result, 0.01f, "Should add all boosts together correctly");
        }
    }
}
