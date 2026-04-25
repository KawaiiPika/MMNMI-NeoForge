package xyz.pixelatedw.mineminenomi.abilities.sube;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;

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
        when(mockedLevel.isClientSide()).thenReturn(false);
    }

    @Test
    void testGetDisplayName() {
        assertEquals("ability.mineminenomi.sube_sube_spur", ability.getDisplayName().getString());
    }
}
