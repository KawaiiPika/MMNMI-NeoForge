package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HandleCannonGoalTest extends AbstractMinecraftTest {

    private HandleCannonGoal goal;
    private Mob mockedEntity;
    private Level mockedLevel;

    @BeforeEach
    void setupTests() {
        mockedEntity = mock(Mob.class);
        mockedLevel = mock(Level.class);

        when(mockedEntity.level()).thenReturn(mockedLevel);
        goal = new HandleCannonGoal(mockedEntity);
    }

    @Test
    void testCanUse_TickCountInvalid() {
        mockedEntity.tickCount = 101;
        assertFalse(goal.canUse());
    }

    @Test
    void testCanUse_IsPassenger() {
        mockedEntity.tickCount = 100;
        when(mockedEntity.isPassenger()).thenReturn(true);
        assertFalse(goal.canUse());
    }

    @Test
    void testCanUse_NoTarget() {
        mockedEntity.tickCount = 100;
        when(mockedEntity.isPassenger()).thenReturn(false);
        when(mockedEntity.getTarget()).thenReturn(null);
        assertFalse(goal.canUse());
    }
}
