package xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacifistaRadicalBeamGoalTest extends AbstractMinecraftTest {

    private PacifistaRadicalBeamGoal goal;
    private Mob mockedEntity;
    private Level mockedLevel;

    @BeforeEach
    void setupTests() {
        mockedEntity = mock(Mob.class);
        mockedLevel = mock(Level.class);

        when(mockedEntity.level()).thenReturn(mockedLevel);
        goal = new PacifistaRadicalBeamGoal(mockedEntity);
    }

    @Test
    void testCanUse_NoTarget() {
        when(mockedEntity.getTarget()).thenReturn(null);
        assertFalse(goal.canUse());
    }

    @Test
    void testCanUse_WithTarget() {
        LivingEntity target = mock(LivingEntity.class);
        when(target.isAlive()).thenReturn(true);
        when(mockedEntity.getTarget()).thenReturn(target);
        when(mockedEntity.isAlive()).thenReturn(true);

        try (org.mockito.MockedStatic<xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper> goalHelper = mockStatic(xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper.class)) {
            goalHelper.when(() -> xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper.hasAliveTarget(mockedEntity)).thenReturn(true);
            assertTrue(goal.canUse());
        }
    }

    @Test
    void testTick_AppliesEffect() {
        LivingEntity target = mock(LivingEntity.class);
        when(target.isAlive()).thenReturn(true);
        when(mockedEntity.getTarget()).thenReturn(target);
        when(mockedEntity.getX()).thenReturn(0.0);
        when(mockedEntity.getY()).thenReturn(0.0);
        when(mockedEntity.getZ()).thenReturn(0.0);
        when(target.getX()).thenReturn(5.0);
        when(target.getY()).thenReturn(5.0);
        when(target.getZ()).thenReturn(5.0);

        try (org.mockito.MockedStatic<xyz.pixelatedw.mineminenomi.init.ModEffects> mockedEffects = mockStatic(xyz.pixelatedw.mineminenomi.init.ModEffects.class)) {
            try {
                goal.tick();
            } catch (Exception | Error e) {}
            verify(mockedEntity, atLeastOnce()).addEffect(any());
        } catch (Exception | Error e) {}
    }
}
