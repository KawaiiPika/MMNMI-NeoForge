package xyz.pixelatedw.mineminenomi.networking;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import xyz.pixelatedw.mineminenomi.networking.packets.SUpdatePlayerStatsPacket;

@EventBusSubscriber(modid = "mineminenomi", bus = EventBusSubscriber.Bus.MOD)
public class ModNetworking {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("mineminenomi").versioned("1.0.0");

        registrar.playToClient(
                SUpdatePlayerStatsPacket.TYPE,
                SUpdatePlayerStatsPacket.STREAM_CODEC,
                SUpdatePlayerStatsPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CToggleCombatModePacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CToggleCombatModePacket.CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CToggleCombatModePacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CExecuteAbilityPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CExecuteAbilityPacket.CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CExecuteAbilityPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CEquipAbilityPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CEquipAbilityPacket.CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CEquipAbilityPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CSelectAbilitySlotPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CSelectAbilitySlotPacket.CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CSelectAbilitySlotPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CRemoveAbilityPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CRemoveAbilityPacket.CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CRemoveAbilityPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CChangeCombatBarPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CChangeCombatBarPacket.CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CChangeCombatBarPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CFinishCharacterCreatorPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CFinishCharacterCreatorPacket.CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CFinishCharacterCreatorPacket::handle
        );

        registrar.playToClient(
                xyz.pixelatedw.mineminenomi.networking.packets.SOpenCreateCrewScreenPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.SOpenCreateCrewScreenPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.SOpenCreateCrewScreenPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CCreateCrewPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CCreateCrewPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CCreateCrewPacket::handle
        );

        registrar.playToClient(
                xyz.pixelatedw.mineminenomi.networking.packets.SOpenCrewScreenPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.SOpenCrewScreenPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.SOpenCrewScreenPacket::handle
        );

        registrar.playToClient(
                xyz.pixelatedw.mineminenomi.networking.packets.SOpenJollyRogerEditorScreenPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.SOpenJollyRogerEditorScreenPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.SOpenJollyRogerEditorScreenPacket::handle
        );

        registrar.playToClient(
                xyz.pixelatedw.mineminenomi.networking.packets.SSyncStrikerCrewPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.SSyncStrikerCrewPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.SSyncStrikerCrewPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CKickFromCrewPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CKickFromCrewPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CKickFromCrewPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CLeaveCrewPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CLeaveCrewPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CLeaveCrewPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.CUpdateJollyRogerPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.CUpdateJollyRogerPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.CUpdateJollyRogerPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.COpenCrewScreenPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.COpenCrewScreenPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.COpenCrewScreenPacket::handle
        );

        registrar.playToServer(
                xyz.pixelatedw.mineminenomi.networking.packets.COpenJollyRogerEditorScreenPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.COpenJollyRogerEditorScreenPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.COpenJollyRogerEditorScreenPacket::handle
        );

        registrar.playToClient(
                xyz.pixelatedw.mineminenomi.networking.packets.SSimpleMessageScreenEventPacket.TYPE,
                xyz.pixelatedw.mineminenomi.networking.packets.SSimpleMessageScreenEventPacket.STREAM_CODEC,
                xyz.pixelatedw.mineminenomi.networking.packets.SSimpleMessageScreenEventPacket::handle
        );
    }

    public static void sendTo(CustomPacketPayload payload, net.minecraft.server.level.ServerPlayer player) {
        player.connection.send(payload);
    }
}
