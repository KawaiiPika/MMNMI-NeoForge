package xyz.pixelatedw.mineminenomi.abilities.sube;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SubeSubeDeflectAbilityTest extends AbstractMinecraftTest {

    private SubeSubeDeflectAbility ability;
    private LivingEntity mockedEntity;
    private ServerLevel mockedLevel;
    private PlayerStats mockedStats;
    private DamageSource mockedSource;

    @BeforeEach
    void setupTests() {
        ability = new SubeSubeDeflectAbility();
        mockedEntity = mock(LivingEntity.class);
        mockedLevel = mock(ServerLevel.class);
        mockedStats = mock(PlayerStats.class);
        mockedSource = mock(DamageSource.class);

        when(mockedEntity.level()).thenReturn(mockedLevel);
        when(mockedEntity.getX()).thenReturn(10.0);
        when(mockedEntity.getY()).thenReturn(20.0);
        when(mockedEntity.getZ()).thenReturn(30.0);
    }

    @Test
    void testOnHurtDeflectsPhysicalAttacks() {
        // Assume ability is active
        try (MockedStatic<PlayerStats> mockedStaticStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStaticStats.when(() -> PlayerStats.get(mockedEntity)).thenReturn(mockedStats);
            when(mockedStats.isAbilityActive(anyString())).thenReturn(true);

            // It uses getAbilityId(), let's mock the mod abilities to allow it or we override
            SubeSubeDeflectAbility spiedAbility = spy(ability);
            doReturn(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sube_sube_deflect")).when(spiedAbility).getAbilityId();

            when(mockedSource.is(DamageTypes.MAGIC)).thenReturn(false);
            when(mockedSource.is(DamageTypes.EXPLOSION)).thenReturn(false);
            when(mockedSource.is(DamageTypes.IN_FIRE)).thenReturn(false);
            when(mockedSource.is(DamageTypes.ON_FIRE)).thenReturn(false);

            boolean isInvulnerable = spiedAbility.checkInvulnerability(mockedEntity, mockedSource);

            org.junit.jupiter.api.Assertions.assertTrue(isInvulnerable, "Physical attack should be deflected");

            // Verify particles were spawned
            verify(mockedLevel).sendParticles(eq(ParticleTypes.END_ROD), eq(10.0), eq(21.0), eq(30.0), eq(5), eq(0.1), eq(0.1), eq(0.1), eq(0.02));
        }
    }

    @Test
    void testOnHurtDoesNotDeflectMagicOrFire() {
        try (MockedStatic<PlayerStats> mockedStaticStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStaticStats.when(() -> PlayerStats.get(mockedEntity)).thenReturn(mockedStats);
            when(mockedStats.isAbilityActive(anyString())).thenReturn(true);

            SubeSubeDeflectAbility spiedAbility = spy(ability);
            doReturn(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sube_sube_deflect")).when(spiedAbility).getAbilityId();

            // Test magic
            when(mockedSource.is(DamageTypes.MAGIC)).thenReturn(true);
            boolean isInvulnerableMagic = spiedAbility.checkInvulnerability(mockedEntity, mockedSource);
            org.junit.jupiter.api.Assertions.assertFalse(isInvulnerableMagic, "Magic attack should not be deflected");

            // Test Explosion
            when(mockedSource.is(DamageTypes.MAGIC)).thenReturn(false);
            when(mockedSource.is(DamageTypes.EXPLOSION)).thenReturn(true);
            boolean isInvulnerableExplosion = spiedAbility.checkInvulnerability(mockedEntity, mockedSource);
            org.junit.jupiter.api.Assertions.assertFalse(isInvulnerableExplosion, "Explosion attack should not be deflected");

            // Verify particles were NOT spawned
            verify(mockedLevel, never()).sendParticles(any(), anyDouble(), anyDouble(), anyDouble(), anyInt(), anyDouble(), anyDouble(), anyDouble(), anyDouble());
        }
    }

    @Test
    void testOnHurtWhenNotActive() {
        try (MockedStatic<PlayerStats> mockedStaticStats = Mockito.mockStatic(PlayerStats.class)) {
            mockedStaticStats.when(() -> PlayerStats.get(mockedEntity)).thenReturn(mockedStats);
            when(mockedStats.isAbilityActive(anyString())).thenReturn(false);

            SubeSubeDeflectAbility spiedAbility = spy(ability);
            doReturn(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sube_sube_deflect")).when(spiedAbility).getAbilityId();

            when(mockedSource.is(any(net.minecraft.resources.ResourceKey.class))).thenReturn(false);

            boolean isInvulnerable = spiedAbility.checkInvulnerability(mockedEntity, mockedSource);
            org.junit.jupiter.api.Assertions.assertFalse(isInvulnerable, "Attack should not be deflected if ability is not active");
            verify(mockedLevel, never()).sendParticles(any(), anyDouble(), anyDouble(), anyDouble(), anyInt(), anyDouble(), anyDouble(), anyDouble(), anyDouble());
        }
    }

    @Test
    void testGetDisplayName() {
        assertEquals("ability.mineminenomi.sube_sube_deflect", ability.getDisplayName().getString());
    }
}
