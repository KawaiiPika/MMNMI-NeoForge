package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PacketDistributorTest extends AbstractMinecraftTest {

    @Captor
    private ArgumentCaptor<CustomPacketPayload> packetCaptor;

    @BeforeEach
    void setupMocking() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInterceptPacketAndValidatePayload() {
        try (MockedStatic<PacketDistributor> mockedDistributor = Mockito.mockStatic(PacketDistributor.class)) {
            // Dispatch a payload that we want to intercept
            CChangeCombatBarPacket payload = new CChangeCombatBarPacket(5);
            PacketDistributor.sendToServer(payload);

            // Intercept using the captor
            mockedDistributor.verify(() -> PacketDistributor.sendToServer(packetCaptor.capture()));

            // Validate it was intercepted
            CustomPacketPayload captured = packetCaptor.getValue();
            assertNotNull(captured);
            assertEquals(CChangeCombatBarPacket.TYPE, captured.type());

            // Assert that the internal payload data is perfectly structured
            assertTrue(captured instanceof CChangeCombatBarPacket);
            CChangeCombatBarPacket capturedPacket = (CChangeCombatBarPacket) captured;
            assertEquals(5, capturedPacket.change());
        }
    }
}
