package xyz.pixelatedw.mineminenomi.abilities.sube;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SubeSubeSpurAbilityTest extends AbstractMinecraftTest {

    private SubeSubeSpurAbility ability;
    private LivingEntity mockedEntity;
    private Level mockedLevel;

    @BeforeEach
    void setupTests() {
        ability = new SubeSubeSpurAbility();
        mockedEntity = mock(LivingEntity.class);
        mockedLevel = mock(Level.class);

        when(mockedEntity.level()).thenReturn(mockedLevel);
        when(mockedEntity.getX()).thenReturn(10.0);
        when(mockedEntity.getY()).thenReturn(20.0);
        when(mockedEntity.getZ()).thenReturn(30.0);

        when(mockedEntity.getLookAngle()).thenReturn(new Vec3(1.0, 0.0, 0.0));
        when(mockedEntity.getDeltaMovement()).thenReturn(new Vec3(0.0, 0.0, 0.0));
    }

    @Test
    void testOnTickOnGround() {
        when(mockedEntity.onGround()).thenReturn(true);
        // We cannot modify isClientSide as it is a final field and we are not using Unsafe.
        // The mock will have isClientSide = false by default.

        ability.onTick(mockedEntity, 10);

        ArgumentCaptor<Vec3> movementCaptor = ArgumentCaptor.forClass(Vec3.class);
        verify(mockedEntity).setDeltaMovement(movementCaptor.capture());

        Vec3 capturedMovement = movementCaptor.getValue();
        // Look angle was (1.0, 0.0, 0.0). Look * 0.4 = (0.4, 0, 0).
        // Added to original movement (0,0,0) = (0.4, 0, 0)
        assertEquals(0.4, capturedMovement.x, 0.001);
        assertEquals(0.0, capturedMovement.y, 0.001);
        assertEquals(0.0, capturedMovement.z, 0.001);

        // Since isClientSide is false, addParticle is not called.
        verify(mockedLevel, never()).addParticle(any(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void testOnTickOffGround() {
        when(mockedEntity.onGround()).thenReturn(false);

        ability.onTick(mockedEntity, 10);

        verify(mockedEntity, never()).setDeltaMovement(any());
        verify(mockedLevel, never()).addParticle(any(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void testStartUsing() {
        try (MockedStatic<AbilityHelper> mockedHelper = Mockito.mockStatic(AbilityHelper.class)) {
            ability.startUsing(mockedEntity);

            mockedHelper.verify(() -> AbilityHelper.sendAbilityMessage(
                eq(mockedEntity),
                any(Component.class)
            ));
        }
    }

    @Test
    void testGetDisplayName() {
        assertEquals("ability.mineminenomi.sube_sube_spur", ability.getDisplayName().getString());
    }
}
