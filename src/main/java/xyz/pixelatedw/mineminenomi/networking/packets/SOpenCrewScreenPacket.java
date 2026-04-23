package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import net.minecraft.client.Minecraft;

public record SOpenCrewScreenPacket(Crew crew) implements CustomPacketPayload {

    public static final Type<SOpenCrewScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_crew_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SOpenCrewScreenPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG.map(Crew::from, Crew::write),
            SOpenCrewScreenPacket::crew,
            SOpenCrewScreenPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SOpenCrewScreenPacket payload, final IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> ClientHandler.handle(payload));
        }
    }

    public static class ClientHandler {
        public static void handle(final SOpenCrewScreenPacket payload) {
            net.minecraft.client.Minecraft.getInstance().setScreen(new xyz.pixelatedw.mineminenomi.client.gui.screens.CrewDetailsScreen(payload.crew()));
        }
    }
}
