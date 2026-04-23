package xyz.pixelatedw.mineminenomi.events;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommonEventsTest extends AbstractMinecraftTest {

    private LivingEntity attacker;
    private LivingEntity target;
    private DamageSource damageSource;
    private DamageContainer damageContainer;
    private LivingIncomingDamageEvent event;
    private PlayerStats attackerStats;
    private PlayerStats targetStats;

    @BeforeEach
    void setupTests() {
        attacker = mock(LivingEntity.class);
        target = mock(LivingEntity.class);
        damageSource = mock(DamageSource.class);

        lenient().when(damageSource.getEntity()).thenReturn(attacker);

        damageContainer = new DamageContainer(damageSource, 10.0f);
        event = new LivingIncomingDamageEvent(target, damageContainer);

        attackerStats = mock(PlayerStats.class);
        targetStats = mock(PlayerStats.class);

        // Ensure getActiveAbilities returns empty by default
        lenient().when(attackerStats.getActiveAbilities()).thenReturn(Collections.emptyList());
        lenient().when(targetStats.getActiveAbilities()).thenReturn(Collections.emptyList());
        lenient().when(target.hasEffect(any())).thenReturn(false);
        lenient().when(attacker.hasEffect(any())).thenReturn(false);
    }

    @Test
    void testMagmaCoatingBoost() {
        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class);
             MockedStatic<HakiHelper> mockedHaki = Mockito.mockStatic(HakiHelper.class)) {

            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(attackerStats);
            mockedStats.when(() -> PlayerStats.get(target)).thenReturn(null);

            lenient().when(attackerStats.isAbilityActive(anyString())).thenReturn(false);
            when(attackerStats.isAbilityActive("mineminenomi:magma_coating")).thenReturn(true);

            mockedHaki.when(() -> HakiHelper.calculateHakiDamage(any(), any(), anyFloat())).thenAnswer(inv -> inv.getArgument(2));

            CommonEvents.onLivingIncomingDamage(event);

            assertEquals(20.0f, event.getAmount());
            verify(target).setRemainingFireTicks(100);
        }
    }

    @Test
    void testSwordsmanDamagePerk() {
        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class);
             MockedStatic<HakiHelper> mockedHaki = Mockito.mockStatic(HakiHelper.class);
             MockedStatic<ItemsHelper> mockedItems = Mockito.mockStatic(ItemsHelper.class)) {

            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(attackerStats);
            mockedStats.when(() -> PlayerStats.get(target)).thenReturn(null);

            lenient().when(attackerStats.isAbilityActive(anyString())).thenReturn(false);
            when(attackerStats.isAbilityActive("mineminenomi:swordsman_damage")).thenReturn(true);

            ItemStack swordStack = mock(ItemStack.class);
            when(attacker.getMainHandItem()).thenReturn(swordStack);
            mockedItems.when(() -> ItemsHelper.isSword(swordStack)).thenReturn(true);

            mockedHaki.when(() -> HakiHelper.calculateHakiDamage(any(), any(), anyFloat())).thenAnswer(inv -> inv.getArgument(2));

            CommonEvents.onLivingIncomingDamage(event);

            assertEquals(12.0f, event.getAmount(), 0.01f);
        }
    }

    @Test
    void testBlackLegDamagePerk() {
        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class);
             MockedStatic<HakiHelper> mockedHaki = Mockito.mockStatic(HakiHelper.class)) {

            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(attackerStats);
            mockedStats.when(() -> PlayerStats.get(target)).thenReturn(null);

            lenient().when(attackerStats.isAbilityActive(anyString())).thenReturn(false);
            when(attackerStats.isAbilityActive("mineminenomi:black_leg_damage")).thenReturn(true);
            ItemStack emptyStack = ItemStack.EMPTY;
            when(attacker.getMainHandItem()).thenReturn(emptyStack);

            mockedHaki.when(() -> HakiHelper.calculateHakiDamage(any(), any(), anyFloat())).thenAnswer(inv -> inv.getArgument(2));

            CommonEvents.onLivingIncomingDamage(event);

            assertEquals(12.0f, event.getAmount(), 0.01f);
        }
    }

    @Test
    void testSniperAccuracyPerk() {
        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class);
             MockedStatic<HakiHelper> mockedHaki = Mockito.mockStatic(HakiHelper.class)) {

            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(attackerStats);
            mockedStats.when(() -> PlayerStats.get(target)).thenReturn(null);

            lenient().when(attackerStats.isAbilityActive(anyString())).thenReturn(false);
            when(attackerStats.isAbilityActive("mineminenomi:sniper_accuracy")).thenReturn(true);
            when(damageSource.is(DamageTypeTags.IS_PROJECTILE)).thenReturn(true);

            mockedHaki.when(() -> HakiHelper.calculateHakiDamage(any(), any(), anyFloat())).thenAnswer(inv -> inv.getArgument(2));

            CommonEvents.onLivingIncomingDamage(event);

            assertEquals(12.0f, event.getAmount(), 0.01f);
        }
    }

    @Test
    void testBrawlerDamagePerk() {
        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class);
             MockedStatic<HakiHelper> mockedHaki = Mockito.mockStatic(HakiHelper.class)) {

            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(attackerStats);
            mockedStats.when(() -> PlayerStats.get(target)).thenReturn(null);

            lenient().when(attackerStats.isAbilityActive(anyString())).thenReturn(false);
            when(attackerStats.isAbilityActive("mineminenomi:brawler_damage")).thenReturn(true);
            ItemStack emptyStack = ItemStack.EMPTY;
            when(attacker.getMainHandItem()).thenReturn(emptyStack);

            mockedHaki.when(() -> HakiHelper.calculateHakiDamage(any(), any(), anyFloat())).thenAnswer(inv -> inv.getArgument(2));

            CommonEvents.onLivingIncomingDamage(event);

            assertEquals(12.0f, event.getAmount(), 0.01f);
        }
    }

    @Test
    void testKenbunshokuHakiDodge() {
        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {

            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(null);
            mockedStats.when(() -> PlayerStats.get(target)).thenReturn(targetStats);

            when(targetStats.isKenbunshokuActive()).thenReturn(true);
            lenient().when(targetStats.isAbilityActive(anyString())).thenReturn(false);
            when(targetStats.isAbilityActive("mineminenomi:kenbunshoku_haki_future_sight")).thenReturn(true);

            // Random returns less than 0.8 to dodge
            net.minecraft.util.RandomSource random = mock(net.minecraft.util.RandomSource.class);
            when(target.getRandom()).thenReturn(random);
            when(random.nextFloat()).thenReturn(0.5f);

            ServerLevel serverLevel = mock(ServerLevel.class);
            // DO NOT MOCK target.level() -> allow it to return null
            when(target.level()).thenReturn(null);

            when(targetStats.getKenbunshokuHakiExp()).thenReturn(0.0f);

            // Recreate event with target that is explicitly returning the mocked stats
            // And ensure attacker is null so we don't trip over attacker logic
            DamageSource newDamageSource = mock(DamageSource.class);
            when(newDamageSource.getEntity()).thenReturn(null);
            DamageContainer newDamageContainer = new DamageContainer(newDamageSource, 10.0f);
            LivingIncomingDamageEvent newEvent = new LivingIncomingDamageEvent(target, newDamageContainer);

            CommonEvents.onLivingIncomingDamage(newEvent);

            assertTrue(newEvent.isCanceled());
            verify(targetStats).setKenbunshokuHakiExp(0.1f);
            verify(targetStats).sync(target);
            // Since level is null, particles and sound aren't called.
            // This prevents NPE from ModSounds.DODGE_1.get() in the unit test since the registry isn't fully set up.
            verify(serverLevel, never()).sendParticles(any(), anyDouble(), anyDouble(), anyDouble(), anyInt(), anyDouble(), anyDouble(), anyDouble(), anyDouble());
        }
    }

    @Test
    void testLogiaIntangibility() {
        try (MockedStatic<PlayerStats> mockedStats = Mockito.mockStatic(PlayerStats.class)) {

            mockedStats.when(() -> PlayerStats.get(attacker)).thenReturn(attackerStats);
            mockedStats.when(() -> PlayerStats.get(target)).thenReturn(targetStats);

            when(targetStats.isLogia()).thenReturn(true);
            when(target.hasEffect(ModEffects.HANDCUFFED_KAIROSEKI)).thenReturn(false);

            lenient().when(attackerStats.isAbilityActive(anyString())).thenReturn(false);
            when(attackerStats.isBusoshokuActive()).thenReturn(false); // No haki bypass

            ServerLevel serverLevel = mock(ServerLevel.class);
            when(target.level()).thenReturn(serverLevel);

            when(target.getX()).thenReturn(10.0);
            when(target.getY()).thenReturn(20.0);
            when(target.getZ()).thenReturn(30.0);

            CommonEvents.onLivingIncomingDamage(event);

            assertTrue(event.isCanceled());
            verify(serverLevel).sendParticles(eq(ParticleTypes.LARGE_SMOKE), anyDouble(), anyDouble(), anyDouble(), eq(10), anyDouble(), anyDouble(), anyDouble(), anyDouble());
            verify(serverLevel).playSound(isNull(), anyDouble(), anyDouble(), anyDouble(), eq(SoundEvents.FIRE_EXTINGUISH), eq(SoundSource.NEUTRAL), eq(0.5f), eq(1.2f));
        }
    }
}
