package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;

public record SOpenWantedPosterScreenPacket(CompoundTag wantedData) implements CustomPacketPayload {

    public static final Type<SOpenWantedPosterScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_wanted_poster_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SOpenWantedPosterScreenPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG, SOpenWantedPosterScreenPacket::wantedData,
            SOpenWantedPosterScreenPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SOpenWantedPosterScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            WantedPosterData wantedPosterData = WantedPosterData.from(payload.wantedData());
            // Screen is not yet ported
            // Minecraft.getInstance().setScreen(new WantedPosterScreen(wantedPosterData));
        });
    }
}
