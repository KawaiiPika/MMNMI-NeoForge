package xyz.pixelatedw.mineminenomi.client.networking;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.networking.packets.*;
import xyz.pixelatedw.mineminenomi.client.gui.screens.CrewDetailsScreen;
import xyz.pixelatedw.mineminenomi.client.gui.screens.JollyRogerEditorScreen;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.screens.Screen;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;

public class ClientPacketHandlers {

    public static void handleOpenCreateCrewScreen(final SOpenCreateCrewScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new xyz.pixelatedw.mineminenomi.client.gui.screens.CreateCrewScreen());
        });
    }

    public static void handleOpenCrewScreen(final SOpenCrewScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new xyz.pixelatedw.mineminenomi.client.gui.screens.CrewDetailsScreen(payload.crew()));
        });
    }

    public static void handleOpenJollyRogerEditorScreen(final SOpenJollyRogerEditorScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new xyz.pixelatedw.mineminenomi.client.gui.screens.JollyRogerEditorScreen(payload.isEditing(), payload.crew(), payload.elements()));
        });
    }

    public static void handleSyncStrikerCrew(final SSyncStrikerCrewPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.level != null) {
                Entity entity = mc.level.getEntity(payload.entityId());
                // TODO: Update StrikerEntity
                // if (entity instanceof xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity striker) {
                //    striker.setCrew(payload.crew());
                // }
            }
        });
    }

    public static void handleSimpleMessageScreenEvent(final SSimpleMessageScreenEventPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Screen screen = mc.screen;
            if (screen instanceof IEventReceiverScreen eventReceiver) {
                eventReceiver.handleEvent(payload.event());
            }
        });
    }
}
