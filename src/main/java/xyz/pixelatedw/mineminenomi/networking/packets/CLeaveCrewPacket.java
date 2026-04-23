package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.common.NeoForge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

import java.util.UUID;

public record CLeaveCrewPacket() implements CustomPacketPayload {

    public static final Type<CLeaveCrewPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "leave_crew"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CLeaveCrewPacket> STREAM_CODEC = StreamCodec.unit(new CLeaveCrewPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CLeaveCrewPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                UUID uuid = player.getUUID();
                FactionsWorldData worldData = FactionsWorldData.get();
                if (worldData == null) return;

                Crew crew = worldData.getCrewWithMember(uuid);
                if (crew != null) {
                    CrewEvent.Leave event = new CrewEvent.Leave(player, crew);
                    if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
                        boolean captainChange = false;
                        boolean isLastMemeber = crew.getMembers().size() == 1;
                        if (!isLastMemeber) {
                            FactionHelper.sendMessageToCrew(player.level(), crew, Component.translatable("crew.message.left", player.getDisplayName().getString()));
                            if (crew.getCaptain().getUUID().equals(uuid)) {
                                captainChange = true;
                            }
                        }

                        worldData.removeCrewMember(player.level(), crew, uuid);
                        if (captainChange && crew.getCaptain() != null) {
                            FactionHelper.sendMessageToCrew(player.level(), crew, Component.translatable("crew.message.new_captain", crew.getCaptain().getUsername()));
                        }
                    }
                }
            }
        });
    }
}
