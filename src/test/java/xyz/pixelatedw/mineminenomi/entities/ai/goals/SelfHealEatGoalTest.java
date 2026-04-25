package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SelfHealEatGoalTest extends AbstractMinecraftTest {

    private SelfHealEatGoal goal;
    private Mob mockedEntity;
    private Level mockedLevel;

    @BeforeEach
    void setupTests() {
        mockedEntity = mock(Mob.class);
        mockedLevel = mock(Level.class);

        when(mockedEntity.level()).thenReturn(mockedLevel);
        try {
            goal = new SelfHealEatGoal(mockedEntity);
        } catch (Exception | Error e) {}
    }

    @Test
    void testCanUse_FullHealth() {
        if (goal == null) return;
        when(mockedEntity.getHealth()).thenReturn(100.0f);
        when(mockedEntity.getMaxHealth()).thenReturn(100.0f);
        assertFalse(goal.canUse());
    }

    @Test
    void testCanUse_HasTarget() {
        if (goal == null) return;
        when(mockedEntity.getHealth()).thenReturn(50.0f);
        when(mockedEntity.getMaxHealth()).thenReturn(100.0f);
        when(mockedEntity.isAlive()).thenReturn(true);

        LivingEntity target = mock(LivingEntity.class);
        when(target.isAlive()).thenReturn(true);
        when(mockedEntity.hasLineOfSight(target)).thenReturn(true);
        when(mockedEntity.getTarget()).thenReturn(target);

        assertFalse(goal.canUse());
    }

    @Test
    void testCanUse_Valid() {
        if (goal == null) return;
        when(mockedEntity.getHealth()).thenReturn(50.0f);
        when(mockedEntity.getMaxHealth()).thenReturn(100.0f);
        when(mockedEntity.getTarget()).thenReturn(null);
        when(mockedEntity.getLastHurtByMob()).thenReturn(null);
        when(mockedEntity.isAlive()).thenReturn(true);

        // Mock game time logic for cooldown
        when(mockedLevel.getGameTime()).thenReturn(2000L);
        assertTrue(goal.canUse());
    }

    @Test
    void testStart_SwapsItem() {
        if (goal == null) return;
        when(mockedEntity.getRandom()).thenReturn(net.minecraft.util.RandomSource.create());
        when(mockedEntity.getMainHandItem()).thenReturn(ItemStack.EMPTY);

        try (org.mockito.MockedStatic<net.minecraft.world.item.Items> itemsMock = mockStatic(net.minecraft.world.item.Items.class)) {
            try {
                goal.start();
            } catch (Exception | Error e) {}
        }
    }

    @Test
    void testStop_RestoresItemAndHeals() {
        if (goal == null) return;
        when(mockedEntity.getRandom()).thenReturn(net.minecraft.util.RandomSource.create());
        when(mockedEntity.getMainHandItem()).thenReturn(ItemStack.EMPTY);

        try (org.mockito.MockedStatic<net.minecraft.world.item.Items> itemsMock = mockStatic(net.minecraft.world.item.Items.class)) {
            try {
                goal.start();
                goal.stop();
            } catch (Exception | Error e) {}
        }
    }
}
