package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DandelionItemTest {

    @BeforeAll
    static void setup() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Ignore bootstrap issues if already bootstrapped or missing some parts
        }
    }

    @Test
    void testFinishUsingItem_ServerSide_Player() {
        Level mockWorld = mock(Level.class);
        Player mockPlayer = mock(Player.class);
        ItemStack mockItemStack = mock(ItemStack.class);
        ItemCooldowns mockCooldowns = mock(ItemCooldowns.class);

        when(mockWorld.isClientSide()).thenReturn(false);
        when(mockPlayer.getCooldowns()).thenReturn(mockCooldowns);

        try (MockedStatic<DandelionItem> mocked = mockStatic(DandelionItem.class)) {
            DandelionItem item = mock(DandelionItem.class, CALLS_REAL_METHODS);

            mocked.when(() -> DandelionItem.getHealAmount(mockItemStack)).thenReturn(10.0f);

            ItemStack result = item.finishUsingItem(mockItemStack, mockWorld, mockPlayer);

            verify(mockCooldowns).addCooldown(item, 200);
            verify(mockPlayer).removeAllEffects();
            verify(mockWorld).broadcastEntityEvent(mockPlayer, (byte)30);
            verify(mockPlayer).heal(10.0f);
            verify(mockItemStack).shrink(1);
            assertEquals(mockItemStack, result);
        }
    }

    @Test
    void testFinishUsingItem_ClientSide_Player() {
        Level mockWorld = mock(Level.class);
        Player mockPlayer = mock(Player.class);
        ItemStack mockItemStack = mock(ItemStack.class);

        when(mockWorld.isClientSide()).thenReturn(true);

        DandelionItem item = mock(DandelionItem.class, CALLS_REAL_METHODS);

        ItemStack result = item.finishUsingItem(mockItemStack, mockWorld, mockPlayer);

        verify(mockPlayer, never()).getCooldowns();
        verify(mockPlayer, never()).removeAllEffects();
        verify(mockWorld, never()).broadcastEntityEvent(any(), anyByte());
        verify(mockPlayer, never()).heal(anyFloat());
        verify(mockItemStack, never()).shrink(anyInt());
        assertEquals(mockItemStack, result);
    }

    @Test
    void testFinishUsingItem_ServerSide_NotPlayer() {
        Level mockWorld = mock(Level.class);
        LivingEntity mockEntity = mock(LivingEntity.class);
        ItemStack mockItemStack = mock(ItemStack.class);

        when(mockWorld.isClientSide()).thenReturn(false);

        DandelionItem item = mock(DandelionItem.class, CALLS_REAL_METHODS);

        ItemStack result = item.finishUsingItem(mockItemStack, mockWorld, mockEntity);

        verify(mockEntity, never()).removeAllEffects();
        verify(mockWorld, never()).broadcastEntityEvent(any(), anyByte());
        verify(mockEntity, never()).heal(anyFloat());
        verify(mockItemStack, never()).shrink(anyInt());
        assertEquals(mockItemStack, result);
    }
}
