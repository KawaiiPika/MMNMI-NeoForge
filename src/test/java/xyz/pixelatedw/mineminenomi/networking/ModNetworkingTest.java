package xyz.pixelatedw.mineminenomi.networking;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;
import xyz.pixelatedw.mineminenomi.networking.packets.CExecuteAbilityPacket;
import xyz.pixelatedw.mineminenomi.networking.packets.SUpdatePlayerStatsPacket;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ModNetworkingTest extends AbstractMinecraftTest {

    @Captor
    private ArgumentCaptor<CustomPacketPayload> payloadCaptor;

    private AutoCloseable mocks;
    private MockedStatic<PacketDistributor> mockedPacketDistributor;

    @BeforeEach
    public void setupMocks() {
        mocks = MockitoAnnotations.openMocks(this);
        mockedPacketDistributor = Mockito.mockStatic(PacketDistributor.class);
    }

    @AfterEach
    public void teardownMocks() throws Exception {
        if (mockedPacketDistributor != null) {
            mockedPacketDistributor.close();
        }
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void testSendToServerCapturesCorrectPayload() {
        // Arrange
        CExecuteAbilityPacket packet = new CExecuteAbilityPacket(1);

        // Act
        PacketDistributor.sendToServer(packet);

        // Assert
        mockedPacketDistributor.verify(() -> PacketDistributor.sendToServer(payloadCaptor.capture()));
        CustomPacketPayload capturedPayload = payloadCaptor.getValue();

        assertNotNull(capturedPayload);
        assertEquals(CExecuteAbilityPacket.TYPE, capturedPayload.type());
        assertEquals(1, ((CExecuteAbilityPacket) capturedPayload).slot());
    }

    @Test
    public void testSendToPlayerCapturesCorrectPayload() {
        // Arrange
        PlayerStats stats = new PlayerStats();
        SUpdatePlayerStatsPacket packet = new SUpdatePlayerStatsPacket(stats);
        ServerPlayer mockPlayer = mock(ServerPlayer.class);

        // Act
        PacketDistributor.sendToPlayer(mockPlayer, packet);

        // Assert
        mockedPacketDistributor.verify(() -> PacketDistributor.sendToPlayer(Mockito.eq(mockPlayer), payloadCaptor.capture()));
        CustomPacketPayload capturedPayload = payloadCaptor.getValue();

        assertNotNull(capturedPayload);
        assertEquals(SUpdatePlayerStatsPacket.TYPE, capturedPayload.type());
        assertEquals(stats, ((SUpdatePlayerStatsPacket) capturedPayload).stats());
    }
}
