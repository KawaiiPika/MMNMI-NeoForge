package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DokuPoisonEffectTest {

    @Mock
    private LivingEntity mockEntity;

    @Mock
    private DamageSource mockPoisonDamage;

    @Mock
    private ModDamageSources mockModDamageSources;

    private MockedStatic<ModDamageSources> mockedModDamageSourcesStatic;

    @BeforeAll
    static void setup() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Ignore bootstrap issues if already bootstrapped or missing some parts
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockedModDamageSourcesStatic = mockStatic(ModDamageSources.class);
        mockedModDamageSourcesStatic.when(ModDamageSources::getInstance).thenReturn(mockModDamageSources);
        when(mockModDamageSources.poison(any())).thenReturn(mockPoisonDamage);
    }

    @AfterEach
    void tearDown() {
        if (mockedModDamageSourcesStatic != null) {
            mockedModDamageSourcesStatic.close();
        }
    }

    @Test
    void testApplyEffectTick() throws Exception {
        // Bypass constructor using Unsafe to avoid registry/bootstrap issues
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        DokuPoisonEffect effect = (DokuPoisonEffect) unsafe.allocateInstance(DokuPoisonEffect.class);

        // Test case 1: Health > 1.0F
        when(mockEntity.getHealth()).thenReturn(2.0F);
        boolean result = effect.applyEffectTick(mockEntity, 2);
        assertTrue(result, "applyEffectTick should return true");
        verify(mockEntity, times(1)).hurt(mockPoisonDamage, 1.0F);

        // Test case 2: Health <= 1.0F
        when(mockEntity.getHealth()).thenReturn(1.0F);
        result = effect.applyEffectTick(mockEntity, 0);
        assertTrue(result, "applyEffectTick should return true");

        // Should not hurt entity again (still 1 time from previous test)
        verify(mockEntity, times(1)).hurt(mockPoisonDamage, 1.0F);
    }
}
