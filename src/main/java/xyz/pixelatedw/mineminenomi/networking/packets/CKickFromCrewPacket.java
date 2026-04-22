package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.common.NeoForge;
import net.minecraft.core.UUIDUtil;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

import java.util.UUID;

public record CKickFromCrewPacket(UUID uuid) implements CustomPacketPayload {

    public static final Type<CKickFromCrewPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kick_from_crew"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CKickFromCrewPacket> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            CKickFromCrewPacket::uuid,
            CKickFromCrewPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CKickFromCrewPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer sender) {
                UUID uuid = payload.uuid();
                FactionsWorldData worldData = FactionsWorldData.get();
                if (worldData == null) return;

                Crew crew = worldData.getCrewWithCaptain(sender.getUUID());
                Player memberPlayer = sender.level().getPlayerByUUID(uuid);

                if (memberPlayer != null && crew != null && crew.hasMember(uuid) && !crew.getCaptain().getUUID().equals(uuid)) {
                    CrewEvent.Kick event = new CrewEvent.Kick(memberPlayer, crew);
                    if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
                        FactionHelper.sendMessageToCrew(sender.level(), crew, Component.translatable("crew.message.kicked", crew.getMember(uuid).getUsername()));
                        worldData.removeCrewMember(sender.level(), crew, uuid);
                    }
                }
            }
        });
    }
}
