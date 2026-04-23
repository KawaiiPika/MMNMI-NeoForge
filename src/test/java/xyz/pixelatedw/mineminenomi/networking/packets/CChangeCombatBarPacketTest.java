package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CChangeCombatBarPacketTest extends AbstractMinecraftTest {

    private IPayloadContext context;
    private Player player;
    private PlayerStats playerStats;

    @BeforeEach
    void setupMocking() {
        context = mock(IPayloadContext.class);
        player = mock(Player.class);
        playerStats = mock(PlayerStats.class);

        when(context.player()).thenReturn(player);
        when(player.getData(ModDataAttachments.PLAYER_STATS)).thenReturn(playerStats);

        // Mock enqueueWork to execute synchronously
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(context).enqueueWork(any(Runnable.class));
    }

    @Test
    void testHandlePositiveChange() {
        when(playerStats.getCombatBarSet()).thenReturn(0);

        CChangeCombatBarPacket payload = new CChangeCombatBarPacket(1);
        CChangeCombatBarPacket.handle(payload, context);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(playerStats).setCombatBarSet(captor.capture());

        assertEquals(1, captor.getValue());
        verify(playerStats).sync(player);
    }
}
