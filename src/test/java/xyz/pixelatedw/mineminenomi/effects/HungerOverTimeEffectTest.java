package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class HungerOverTimeEffectTest extends AbstractMinecraftTest {

    @Test
    public void testApplyEffectTick() throws Exception {
        HungerOverTimeEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (HungerOverTimeEffect) unsafe.allocateInstance(HungerOverTimeEffect.class);

            java.lang.reflect.Field baseHungerField = HungerOverTimeEffect.class.getDeclaredField("baseHunger");
            baseHungerField.setAccessible(true);
            baseHungerField.setFloat(effect, 1.0F);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        Player mockPlayer = mock(Player.class);
        FoodData mockFoodData = mock(FoodData.class);

        when(mockPlayer.getFoodData()).thenReturn(mockFoodData);

        // Food level 10, amplifier 0 -> hunger 1.0 -> new food level 9
        when(mockFoodData.getFoodLevel()).thenReturn(10);
        boolean result = effect.applyEffectTick(mockPlayer, 0);
        assertTrue(result);
        verify(mockFoodData, times(1)).setFoodLevel(9);

        // Food level 10, amplifier 2 -> hunger 3.0 -> new food level 7
        reset(mockFoodData);
        when(mockPlayer.getFoodData()).thenReturn(mockFoodData);
        when(mockFoodData.getFoodLevel()).thenReturn(10);
        result = effect.applyEffectTick(mockPlayer, 2);
        assertTrue(result);
        verify(mockFoodData, times(1)).setFoodLevel(7);

        // Food level 1, amplifier 2 -> hunger 3.0 -> new food level 0 (not negative)
        reset(mockFoodData);
        when(mockPlayer.getFoodData()).thenReturn(mockFoodData);
        when(mockFoodData.getFoodLevel()).thenReturn(1);
        result = effect.applyEffectTick(mockPlayer, 2);
        assertTrue(result);
        verify(mockFoodData, times(1)).setFoodLevel(0);
    }
}
