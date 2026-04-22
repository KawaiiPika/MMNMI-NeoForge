package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class KingPunchAbilityTest {

    private KingPunchAbility ability;
    private LivingEntity entity;
    private Level level;
    private DamageSources damageSources;
    private DamageSource damageSource;

    @BeforeAll
    public static void setupAll() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Ignore bootstrap issues if already bootstrapped
        }
    }

    @BeforeEach
    public void setup() {
        ability = new KingPunchAbility();
        entity = mock(LivingEntity.class, withSettings().withoutAnnotations());
        level = mock(Level.class, withSettings().withoutAnnotations());
        damageSources = mock(DamageSources.class, withSettings().withoutAnnotations());
        damageSource = mock(DamageSource.class, withSettings().withoutAnnotations());

        when(entity.level()).thenReturn(level);
        when(entity.damageSources()).thenReturn(damageSources);
        when(damageSources.mobAttack(entity)).thenReturn(damageSource);
    }

    private void setField(Object obj, String fieldName, Object value) throws Exception {
        Field field = KingPunchAbility.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private Object getField(Object obj, String fieldName) throws Exception {
        Field field = KingPunchAbility.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    @Test
    public void testStartUsing() throws Exception {
        when(level.getGameTime()).thenReturn(1000L);
        when(level.isClientSide()).thenReturn(false);

        java.lang.reflect.Method method = ability.getClass().getDeclaredMethod("startUsing", LivingEntity.class);
        method.setAccessible(true);
        method.invoke(ability, entity);

        assertTrue((Boolean) getField(ability, "isCharging"));
        assertEquals(1000L, (Long) getField(ability, "chargeStartTime"));

        ArgumentCaptor<Component> captor = ArgumentCaptor.forClass(Component.class);
        verify(entity, atLeastOnce()).sendSystemMessage(captor.capture());
        assertEquals("Warming up the King Punch... This will take a while.", captor.getAllValues().get(0).getString());
    }

    @Test
    public void testOnTickPerformPunch() throws Exception {
        setField(ability, "isCharging", true);
        setField(ability, "chargeStartTime", 1000L);

        when(level.getGameTime()).thenReturn(1600L); // elapsed = 600
        when(level.isClientSide()).thenReturn(false);

        AABB aabb = mock(AABB.class, withSettings().withoutAnnotations());
        when(entity.getBoundingBox()).thenReturn(aabb);
        AABB inflatedAabb = mock(AABB.class, withSettings().withoutAnnotations());
        when(aabb.inflate(20.0)).thenReturn(inflatedAabb);

        LivingEntity target = mock(LivingEntity.class, withSettings().withoutAnnotations());
        when(level.getEntitiesOfClass(eq(LivingEntity.class), eq(inflatedAabb))).thenReturn(Collections.singletonList(target));

        when(entity.position()).thenReturn(new Vec3(0, 0, 0));
        when(target.position()).thenReturn(new Vec3(10, 0, 0));

        // Let's create a subclass to stub out PlayerStats dependent methods from Ability.java
        TestableKingPunchAbility testableAbility = new TestableKingPunchAbility();
        setField(testableAbility, "isCharging", true);
        setField(testableAbility, "chargeStartTime", 1000L);

        testableAbility.onTick(entity, 0);

        verify(target).hurt(damageSource, 100.0f);
        verify(target).setDeltaMovement(5.0, 1.5, 0.0);

        ArgumentCaptor<Component> captor = ArgumentCaptor.forClass(Component.class);
        verify(entity, times(2)).sendSystemMessage(captor.capture());
        assertEquals("KING PUNCH!!!", captor.getAllValues().get(1).getString());

        assertFalse((Boolean) getField(testableAbility, "isCharging"));
        assertTrue(testableAbility.cooldownStarted);
        assertTrue(testableAbility.stopped);
    }

    public static class TestableKingPunchAbility extends KingPunchAbility {
        public boolean cooldownStarted = false;
        public boolean stopped = false;

        @Override
        protected void startCooldown(LivingEntity entity, long ticks) {
            cooldownStarted = true;
        }

        @Override
        public void stop(LivingEntity entity) {
            stopped = true;
        }
    }
}
