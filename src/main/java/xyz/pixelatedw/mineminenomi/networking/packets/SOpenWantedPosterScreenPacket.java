package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;

public record SOpenWantedPosterScreenPacket(CompoundTag wantedData) implements CustomPacketPayload {

    public static final Type<SOpenWantedPosterScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_wanted_poster_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SOpenWantedPosterScreenPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            SOpenWantedPosterScreenPacket::wantedData,
            SOpenWantedPosterScreenPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SOpenWantedPosterScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> ClientHandler.handle(payload));
    }

    public static class ClientHandler {
        @OnlyIn(Dist.CLIENT)
        public static void handle(final SOpenWantedPosterScreenPacket payload) {
            WantedPosterData wantedPosterData = WantedPosterData.from(payload.wantedData());
            // TODO: Phase 3 - Open WantedPosterScreen when ported
            // net.minecraft.client.Minecraft.getInstance().setScreen(new xyz.pixelatedw.mineminenomi.ui.screens.WantedPosterScreen(wantedPosterData));
        }
    }
}
