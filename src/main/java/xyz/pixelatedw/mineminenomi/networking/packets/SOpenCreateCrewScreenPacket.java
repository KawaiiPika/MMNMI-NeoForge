package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SOpenCreateCrewScreenPacket() implements CustomPacketPayload {

    public static final Type<SOpenCreateCrewScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_create_crew_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SOpenCreateCrewScreenPacket> STREAM_CODEC = StreamCodec.unit(new SOpenCreateCrewScreenPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SOpenCreateCrewScreenPacket payload, final net.neoforged.neoforge.network.handling.IPayloadContext context) {
        xyz.pixelatedw.mineminenomi.client.networking.ClientPacketHandlers.handleOpenCreateCrewScreen(payload, context);
    }
}